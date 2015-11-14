package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
//import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class User implements currentlyLoggedUsers, Runnable{
	private static final Object SHUTDOWN = "shutdown";
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
				if(SHUTDOWN.equals(line)){
					echoServer.stopServer();
					break;
				}
				if("login".equals(string[0])){
					System.out.println("tyka");
					out.println("vutre");
					login.Login(line);
//					break;
				} 
				if("logout".equals(string[1])){
					out.println("vunka");
					logout.Logout(line);
				}
				if("shutdown".equals(string[1])){
					if(shutdown.Shutdown(line)){
						echoServer.stopServer();
						break;
					}
				}
				if("info".equals(string[1])){
					info.Info(line);
				}
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
