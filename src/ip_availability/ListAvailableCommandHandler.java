package ip_availability;

public class ListAvailableCommandHandler implements currentlyLoggedUsers {
	private final String command;
	public ListAvailableCommandHandler(String command){
		this.command=command;
	}
	
	public Boolean ListAvailable(){
		final String[] split = command.split(":");
		if("listavailable".equals(split[1])){
			String users = "ok";
			if(currentlyLoggedUsers.contains(split[0])){
				for (String name : currentlyLoggedUsers) {
					users += ":" + name;
				}
				System.out.println(users);
				return true;
			}
			else{
				System.out.println("error:notlogged");
				return true;
			}
		}
		System.out.println("error:unknowncommand");
		return true;
	}
}
