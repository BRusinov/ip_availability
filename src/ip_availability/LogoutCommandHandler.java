package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Date;

@SuppressWarnings("unused")

public class LogoutCommandHandler implements General {
	private final String command;
	private final Socket socket;
	private final User user;
	
	public LogoutCommandHandler(String command, Socket socket, User user){
		this.command=command;
		this.socket=socket;
		this.user=user;
	}
	
	public String Logout(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.containsKey(split[0]) && usersToLoginCount.containsKey(split[0])){
			Interval interval= user.date.get(user.date.size()-1);
			interval.end=new Date();
			user.date.remove(user.date.size()-1);
			user.date.add(interval);
			currentlyLoggedUsers.remove(split[0]);
			notLoggedUsers.add(split[0]);
			return "ok";
		}
			return "error:notlogged";
	}
}
