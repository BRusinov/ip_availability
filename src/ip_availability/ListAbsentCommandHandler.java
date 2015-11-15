package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
public class ListAbsentCommandHandler implements currentlyLoggedUsers{
	private final String command;
	private final Socket socket;
 
	public ListAbsentCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public void ListAbsent(String string) throws IOException {
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		String users = "ok";
		if(currentlyLoggedUsers.contains(split[0])){
			for (String name : notLoggedUsers) {
				if(usersToLoginCount.containsKey(name)){
					users += ":" + name;
				}
			}
			out.println(users);
		}
		else out.println("error:notlogged");
	}
}