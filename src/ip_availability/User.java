package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class User implements General, Runnable {
	private final Socket socket;
	private EchoServer echoServer;

	public User(EchoServer echoServer, Socket socket) {
		this.echoServer = echoServer;
		this.socket = socket;
	}

	public Socket getSocket() {
		return this.socket;
	}

	public List<Interval> date = new ArrayList<Interval>();

	Scanner scanner;

	public void run() {
		try {
			final PrintStream out = new PrintStream(socket.getOutputStream());
			scanner = new Scanner(socket.getInputStream());
			out.println("Enter command:");
			while (scanner.hasNextLine() && this.socket.isConnected()) {
				final String line = scanner.nextLine();
				if (!line.contains(":")) {
					out.println("error:unknowncommand");
					continue;
				}
				final String[] string = line.split(":");
				/*CommandHandler(string, line);
				if (!CommandHandler(string, line))
					out.println("error:unknowncommand");*/
				out.println(CommandHandler(string, line));
				out.println("Enter command:");
			}
			scanner.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			echoServer.onClientStopped(this);
		}
	}

	public String CommandHandler(String[] string, String line) throws IOException {
		String result;
		if ("login".equals(string[0])) {
			result = LoggedIn(line);
			// out.println("ok");
		} else if ("logout".equals(string[1]))
			result = LoggedOut(line);
		else if ("shutdown".equals(string[1]))
			result = Shutdown(line);
		else if ("info".equals(string[1]))
			result = Info(line);
		else if ("listavailable".equals(string[1]))
			result = ListAvailable(line);
		else if ("listabsent".equals(string[1]))
			result = ListAbsent(line);
		else
			result = "error:unknowncommand";
		
		return result;
	}

	public void StopUser() {
		echoServer.onClientStopped(this);
	}

	public String LoggedIn(String string) throws IOException {
		LoginCommandHandler login = new LoginCommandHandler(string, socket, this);
		return login.Login(string, this);
	}

	public String LoggedOut(String string) throws IOException {
		LogoutCommandHandler logout = new LogoutCommandHandler(string, socket, this);
		return logout.Logout(string);
	}

	public String Info(String string) throws IOException {
		InfoCommandHandler info = new InfoCommandHandler(string, socket, this);
		return info.Info(string);
	}

	public String Shutdown(String string) throws IOException {
		ShutdownCommandHandler shutdown = new ShutdownCommandHandler(string, socket);
		if (shutdown.Shutdown(string).equals("ok")) {
			echoServer.stopServer();
			return "not ok";
		}
		return "ok";
	}

	public String ListAvailable(String string) throws IOException {
		ListAvailableCommandHandler list = new ListAvailableCommandHandler(string, socket);
		return list.ListAvailable(string);
	}

	public String ListAbsent(String string) throws IOException {
		ListAbsentCommandHandler absent = new ListAbsentCommandHandler(string, socket);
		return absent.ListAbsent(string);
	}

	public void stopClient() throws IOException {
		socket.close();
	}
}
