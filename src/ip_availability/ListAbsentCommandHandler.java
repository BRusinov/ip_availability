package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class ListAbsentCommandHandler implements General{
	private final String command;
	private final Socket socket;
	private final User user;

	public ListAbsentCommandHandler(String command, Socket socket, User user){
		this.command=command;
		this.socket=socket;
		this.user=user;
	}
	
	public String ListAbsent(String string) throws IOException {
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		String users = "ok";
		if(currentlyLoggedUsers.containsKey(user.name)){
			for (String name : notLoggedUsers) {
				if(usersToLoginCount.containsKey(name)) users += ":" + name;
			}
			return users;
		}
		return "error:notlogged";
	}
}
