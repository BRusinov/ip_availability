package ip_availability;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

@SuppressWarnings("unused")
public class LoginCommandHandler implements General{
	private final String command;
	private final Socket socket;
	public LoginCommandHandler(String command, Socket socket) {
		this.command=command;
		this.socket=socket;
	}
	public void Login(String string, User user) throws IOException{
		final String[] split = string.split(":");
		if(currentlyLoggedUsers.containsKey(split[1])) {				
			if (notLoggedUsers.contains(split[1])) notLoggedUsers.remove(split[1]);
			else{
				LinkedList<String> usernames= new LinkedList<String>();
				for (String name : currentlyLoggedUsers.keySet()) {
					if(name.equals(split[1]) && currentlyLoggedUsers.get(name) != this.socket) usernames.add(name);
				}
				for (String name : usernames) {
					currentlyLoggedUsers.get(name).shutdownInput();
					currentlyLoggedUsers.get(name).close();
					currentlyLoggedUsers.remove(name);
				}
				if (usernames.size() > 0) this.AddNameToLists(split[1]);
			}
		} 
		else this.AddNameToLists(split[1]);
	}
	
	private void AddNameToLists(String name)
	{
		currentlyLoggedUsers.put(name,this.socket);
		if (usersToLoginCount.containsKey(name)){
			usersToLoginCount.put(name, usersToLoginCount.get(name) + 1);
			notLoggedUsers.remove(name);
		}else{
			usersToLoginCount.put(name, 1);
			notLoggedUsers.remove(name);
		}
	}
}

