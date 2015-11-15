package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class InfoCommandHandler implements General {
	private final String command;
	private final Socket socket;
	public InfoCommandHandler(String command, Socket socket){
		this.command=command;
		this.socket=socket;
	}
	
	public void Info(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.containsKey(split[0])){
			out.println("ok:" + split[2] + ":" + (currentlyLoggedUsers.containsKey(split[2]) ? "true" : "false") + ":" + usersToLoginCount.get(split[2]));
		}
		else {
			out.println("error:notlogged");
		}
	}
	
}
