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
	public String name;

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
				out.println(CommandHandler(line));
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

	public String CommandHandler(String line) throws IOException {
		final String[] split = line.split(":");
		String result;
		if ("login".equals(split[0])) result = LoggedIn(line);
		else if ("logout".equals(split[0])) result = LoggedOut(line);
		else if ("shutdown".equals(split[0])) result = Shutdown(line);
		else if ("info".equals(split[0])) result = Info(line);
		else if ("listavailable".equals(split[0])) result = ListAvailable(line);
		else if ("listabsent".equals(split[0])) result = ListAbsent(line);
		else result = "error:unknowncommand";
		return result;
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
		ShutdownCommandHandler shutdown = new ShutdownCommandHandler(string, socket, this);
		if (shutdown.Shutdown(string).equals("ok")) {
			echoServer.stopServer();
			return "not ok";
		}
		return "ok";
	}

	public String ListAvailable(String string) throws IOException {
		ListAvailableCommandHandler list = new ListAvailableCommandHandler(string, socket, this);
		return list.ListAvailable(string);
	}

	public String ListAbsent(String string) throws IOException {
		ListAbsentCommandHandler absent = new ListAbsentCommandHandler(string, socket, this);
		return absent.ListAbsent(string);
	}

	public void stopClient() throws IOException {
		socket.close();
	}
}
