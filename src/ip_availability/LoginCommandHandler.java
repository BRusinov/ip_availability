package ip_availability;

public class LoginCommandHandler implements currentlyLoggedUsers{
	private final String command;
	public LoginCommandHandler(String command) {
		this.command=command;
	}
	
	
	public Boolean Login(){
		final String[] split = command.split(":");
		if ("login".equals(split[0])) {
			if(currentlyLoggedUsers.contains(split[1])) {				
				if (usersToLoginCount.containsKey(split[1]))
					usersToLoginCount.put(split[1], usersToLoginCount.get(split[1]) + 1);
				else
					usersToLoginCount.put(split[1], 1);
				System.out.println("ok");
				return true;
			} 
			else {
				currentlyLoggedUsers.add(split[1]);
				if (usersToLoginCount.containsKey(split[1]))
					usersToLoginCount.put(split[1], usersToLoginCount.get(split[1]) + 1);
				else
					usersToLoginCount.put(split[1], 1);
				System.out.println("ok");
				return true;
			}
		}
		System.out.println("error:unknowncommand");
		return true;
	}
}

