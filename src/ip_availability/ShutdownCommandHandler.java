package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class ShutdownCommandHandler implements General {
	private final String command;
	private final Socket socket;
	private final User user;
	public ShutdownCommandHandler(String command, Socket socket, User user){
		this.command=command;
		this.socket=socket;
		this.user = user;
	}
	
	public String Shutdown(String string) throws IOException{
		final String[] split = string.split(":");
		if(currentlyLoggedUsers.containsKey(user.name)) return "ok";
			return "error:notlogged";
	}
}
