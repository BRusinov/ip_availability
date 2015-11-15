package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ShutdownCommandHandler implements General {
	private final String command;
	private final Socket socket;
	public ShutdownCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public Boolean Shutdown(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		
		if(currentlyLoggedUsers.contains(split[0])){
			out.println("ok");
			return true;
		}
		else{
			out.println("error:notlogged");
			return false;
		}
	}
}
