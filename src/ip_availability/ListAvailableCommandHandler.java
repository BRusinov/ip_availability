package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class ListAvailableCommandHandler implements currentlyLoggedUsers {
	private final String command;
	private final Socket socket;
	public ListAvailableCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public Boolean ListAvailable(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());

		String users = "ok";
		if(currentlyLoggedUsers.contains(split[0])){
			for (String name : currentlyLoggedUsers) {
				users += ":" + name;
			}
			out.println(users);
			return true;
		}
		else{
			out.println("error:notlogged");
			return true;
		}
	}
}
