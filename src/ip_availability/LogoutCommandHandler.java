package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class LogoutCommandHandler implements General {
	private final String command;
	private final Socket socket;
	
	
	public LogoutCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public void Logout(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.containsKey(split[0]) && usersToLoginCount.containsKey(split[0])){
			currentlyLoggedUsers.remove(split[0]);
			notLoggedUsers.add(split[0]);
		}
		else {
			out.println("error:notlogged");
		}
	}
}
