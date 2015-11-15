package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class User implements General, Runnable{
	private final Socket socket;
	private EchoServer echoServer;
	
	public User(EchoServer echoServer, Socket socket) {
		this.echoServer=echoServer;
		this.socket=socket;
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	
	Scanner scanner;
	public void run(){
		try {
			final PrintStream out = new PrintStream(socket.getOutputStream());
			scanner = new Scanner(socket.getInputStream());
			out.println("Enter command:");
			while(scanner.hasNextLine() && this.socket.isConnected()){
				final String line= scanner.nextLine();
				if (!line.contains(":")){
					out.println("error:unknowncommand");
					continue;
				}
				final String[] string= line.split(":");
				if("login".equals(string[0])){
					LoggedIn(line);
					out.println("ok");
				} 
				else if("logout".equals(string[1])){
					LoggedOut(line);
				}
				else if("shutdown".equals(string[1])){
					Shutdown(line);
				}
				else if("info".equals(string[1])){
					Info(line);
				}
				else if("listavailable".equals(string[1])){
					ListAvailable(line);
				}
				else if("listabsent".equals(string[1])){
					ListAbsent(line);
				}else
				out.println("error:unknowncommand");
				out.println("Enter command:");

			}
			scanner.close();
			out.close();
			socket.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		} finally {
			echoServer.onClientStopped(this);
		}
	}
	
	public void StopUser(){
		echoServer.onClientStopped(this);
	}
	
	public void LoggedIn(String string) throws IOException{
		LoginCommandHandler login = new LoginCommandHandler(string, socket);
		login.Login(string,this);
	}
	
	public void LoggedOut(String string) throws IOException{
		LogoutCommandHandler logout= new LogoutCommandHandler(string, socket);
		logout.Logout(string);
	}
	
	public void Info(String string) throws IOException{
		InfoCommandHandler info= new InfoCommandHandler(string, socket);
		info.Info(string);
	}
	
	public Boolean Shutdown(String string) throws IOException{
		ShutdownCommandHandler shutdown= new ShutdownCommandHandler(string, socket);
		if(shutdown.Shutdown(string)){
			echoServer.stopServer();
			return false;
		}
		return true;
	}
	
	public void ListAvailable(String string) throws IOException{
		ListAvailableCommandHandler list= new ListAvailableCommandHandler(string, socket);
		list.ListAvailable(string);
	}
	
	public void ListAbsent(String string) throws IOException{
		ListAbsentCommandHandler absent= new ListAbsentCommandHandler(string, socket);
		absent.ListAbsent(string);
	}
	
	public void stopClient() throws IOException{
		socket.close();
	}
}
