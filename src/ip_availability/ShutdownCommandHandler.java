package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class ShutdownCommandHandler implements General {
	private final String command;
	private final Socket socket;
	public ShutdownCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public String Shutdown(String string) throws IOException{
		final String[] split = string.split(":");
		if(currentlyLoggedUsers.containsKey(split[0])){
			return "ok";
		}
			return "error:notlogged";
	}
}
