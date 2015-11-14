package ip_availability;

public class LogoutCommandHandler implements currentlyLoggedUsers {
	private final String command;
	
	
	public LogoutCommandHandler(String command){
		this.command=command;
	}
	
	public Boolean Logout(){
		final String[] split = command.split(":");
		
		if("logout".equals(split[1])){
			if(currentlyLoggedUsers.contains(split[0]) && usersToLoginCount.containsKey(split[0])){
				currentlyLoggedUsers.remove(split[0]);
				return true;
			}
			else {
				System.out.println("error:notlogged");
				return true;
			}
		}
		System.out.println("error:unknowncommand");
		return true;
	}
}
