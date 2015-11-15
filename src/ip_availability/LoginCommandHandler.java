package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

public class LoginCommandHandler implements currentlyLoggedUsers{
	private final String command;
	private final Socket socket;
	
	public LoginCommandHandler(String command, Socket socket) {
		this.command=command;
		this.socket=socket;
	}
	
	
	public void Login(String string) throws IOException{
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		if(currentlyLoggedUsers.contains(split[1])) {				
			if (usersToLoginCount.containsKey(split[1])){
				usersToLoginCount.put(split[1], usersToLoginCount.get(split[1]) + 1);
				notLoggedUsers.remove(split[1]);
			}
			else{
				usersToLoginCount.put(split[1], 1);
				notLoggedUsers.remove(split[1]);
			}
			out.println("ok");
		} 
		else {
			currentlyLoggedUsers.add(split[1]);
			if (usersToLoginCount.containsKey(split[1])){
				usersToLoginCount.put(split[1], usersToLoginCount.get(split[1]) + 1);
				notLoggedUsers.remove(split[1]);
			}else{
				usersToLoginCount.put(split[1], 1);
				notLoggedUsers.remove(split[1]);
			}
			out.println("ok");
		}
	}
}

