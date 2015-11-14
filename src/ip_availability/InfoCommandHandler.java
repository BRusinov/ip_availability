package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class InfoCommandHandler implements currentlyLoggedUsers {
	private final String command;
	private final Socket socket;
	public InfoCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public Boolean Info(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());

		if(currentlyLoggedUsers.contains(split[0])){
			out.println("ok:" + split[2] + ":" + (currentlyLoggedUsers.contains(split[2]) ? "true" : "false") + ":" + usersToLoginCount.get(split[2]));
			return true;
		}
		else {
			out.println("error:notlogged");
			return true;
		}
	}
	
}
