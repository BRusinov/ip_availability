package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
//import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class User implements currentlyLoggedUsers{
	private static final Object STOP_SERVER = "shutdown";
//	private static String command;
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
			while(scanner.hasNextLine()){
				final String line= scanner.nextLine();
				if(STOP_SERVER.equals(line)){
					echoServer.stopServer();
					break;
				} 
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
