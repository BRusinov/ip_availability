package ip_availability;

public class ShutdownCommandHandler implements currentlyLoggedUsers {
	private final String command;
	public ShutdownCommandHandler(String command){
		this.command=command;
	}
	
	public Boolean Shutdown(){
		final String[] split = command.split(":");
		if("shutdown".equals(split[1])){
			if(currentlyLoggedUsers.contains(split[0])){
				System.out.println("ok");
//				System.out.println("tyka");
				return false;
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
