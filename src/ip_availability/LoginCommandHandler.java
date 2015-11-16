package ip_availability;

import java.io.IOException;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;


@SuppressWarnings("unused")
public class LoginCommandHandler implements General{
	private final String command;
	private final Socket socket;
	private final User user;
	public LoginCommandHandler(String command, Socket socket, User user) {
		this.command=command;
		this.socket=socket;
		this.user=user;
	}
	 
	public String Login(String string, User user) throws IOException{
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
		user.date.add(new Interval(new Date()));
		user.name = split[1];
		return "ok";
	}
	
	private void AddNameToLists(String name)
	{
		Date start= new Date();
		currentlyLoggedUsers.put(name,this.socket);
		if (usersToLoginCount.containsKey(name)){
			Interval interval= new Interval(start);
			usersToLoginCount.put(name, usersToLoginCount.get(name) + 1);
			notLoggedUsers.remove(name);
		}else{
			Interval interval= new Interval(start);
			usersToLoginCount.put(name, 1);
			notLoggedUsers.remove(name);
			allUsers.put(name,user);
		}
	}
}

