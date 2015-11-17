package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class ListAvailableCommandHandler implements General {
	private final String command;
	private final Socket socket;
	private final User user;
	public ListAvailableCommandHandler(String command, Socket socket, User user){
		this.command=command;
		this.socket=socket;
		this.user = user;
	}
	
	public String ListAvailable(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		String users = "ok";
		if(currentlyLoggedUsers.containsKey(user.name)){
			for (String name : currentlyLoggedUsers.keySet()) users += ":" + name;
			return users;
		}
		else return "error:notlogged";
	}
}
