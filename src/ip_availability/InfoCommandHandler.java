package ip_availability;

public class InfoCommandHandler implements currentlyLoggedUsers {
	private final String command;
	public InfoCommandHandler(String command){
		this.command=command;
	}
	
	public Boolean Info(){
		final String[] split = command.split(":");
		if("info".equals(split[1])){
			if(currentlyLoggedUsers.contains(split[0])){
				System.out.println("ok:" + split[2] + ":" + (currentlyLoggedUsers.contains(split[2]) ? "true" : "false") + ":" + usersToLoginCount.get(split[2]));
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
