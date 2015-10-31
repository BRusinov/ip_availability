package ip_availability;
import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;

import java.util.LinkedList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		System.out.print("Въведете команда: ");
		Scanner in = new Scanner(System.in);
		String name = in.next();
		while(!name.equals("shutdown")){	
			if(!execute(name))
				break;
			System.out.print("Въведете команда: ");
			name = in.next();
		}
		in.close();
	}
	
	final static Map<String, Integer> usersToLoginCount = new HashMap<String, Integer>();
	final static List<String> currentlyLoggedUsers = new LinkedList<String>();
	static int loginCounter=0;
	
	private static Boolean execute(String command){
		
		if (!command.contains(":")){
			System.out.println("error:unknowncommand");
			return true;
		}
		
		final String[] split = command.split(":");
		
		if ("login".equals(split[1])) {
			if(currentlyLoggedUsers.contains(split[0])) {				
				if (usersToLoginCount.containsKey(split[0]))
					usersToLoginCount.put(split[0], usersToLoginCount.get(split[0]) + 1);
				else
					usersToLoginCount.put(split[0], 1);
				
				System.out.println("ok");
				return true;
			} 
			else {
				currentlyLoggedUsers.add(split[0]);
				if (usersToLoginCount.containsKey(split[0]))
					usersToLoginCount.put(split[0], usersToLoginCount.get(split[0]) + 1);
				else
					usersToLoginCount.put(split[0], 1);
				
				System.out.println("ok");
				return true;
			}
		}
		
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
		
		if("shutdown".equals(split[1])){
			if(currentlyLoggedUsers.contains(split[0])){
				System.out.println("ok");
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
