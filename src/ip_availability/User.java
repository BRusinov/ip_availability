package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class User implements currentlyLoggedUsers, Runnable{
	private final Socket socket;
	private EchoServer echoServer;
	
	public User(EchoServer echoServer, Socket socket) {
		this.echoServer=echoServer;
		this.socket=socket;
	}
	
	public void run(){
		Scanner scanner;
		try {
			final PrintStream out = new PrintStream(socket.getOutputStream());
			scanner = new Scanner(socket.getInputStream());
			out.println("EXO");
			while(scanner.hasNextLine()){
				final String line= scanner.nextLine();
				final String[] string= line.split(":");
				LoginCommandHandler login= new LoginCommandHandler(line, socket);
				LogoutCommandHandler logout= new LogoutCommandHandler(line, socket);
				ShutdownCommandHandler shutdown= new ShutdownCommandHandler(line, socket);
				InfoCommandHandler info= new InfoCommandHandler(line, socket);
				ListAvailableCommandHandler list= new ListAvailableCommandHandler(line, socket);
				ListAbsentCommandHandler absent= new ListAbsentCommandHandler(line, socket);
				if("login".equals(string[0])){
					login.Login(line);
				} 
				else if("logout".equals(string[1])){
					logout.Logout(line);
				}
				else if("shutdown".equals(string[1])){
					if(shutdown.Shutdown(line)){
						echoServer.stopServer();
						break;
					}
				}
				else if("info".equals(string[1])){
					info.Info(line);
				}
				else if("listavailable".equals(string[1])){
					list.ListAvailable(line);
				}
				else if("listabsent".equals(string[1])){
					absent.ListAbsent(line);
				}else
				out.println("error:unknowncommand");
			}
			scanner.close();
			out.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			echoServer.onClientStopped(this);
		}	
	}
	
	public void stopClient() throws IOException{
		socket.close();
	}
}
