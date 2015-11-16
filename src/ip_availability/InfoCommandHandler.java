package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class InfoCommandHandler implements General {
	private final String command;
	private final Socket socket;
	private final User user;
	public InfoCommandHandler(String command, Socket socket, User user){
		this.command=command;
		this.socket=socket;
		this.user=user;
	}
	

	
	public String Info(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.containsKey(split[0])){
			out.println("ok:" + split[2] + ":" + (currentlyLoggedUsers.containsKey(split[2]) ? "true" : "false") + ":" + usersToLoginCount.get(split[2]));
			out.println(user.date.size());
			for (Interval interval : user.date) {
				System.out.println("vuv fora ");
				String result;
				if(interval.end!=null) result = ":"+ interval.dateFormat.format(interval.start) + ":" + interval.dateFormat.format(interval.end);
				else result = ":"+ interval.dateFormat.format(interval.start);
				return result;
			}
		}
		
		return "error:notlogged";
	}
	
}
