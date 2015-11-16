package ip_availability;

import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface General {
	final static Map<String, Socket> currentlyLoggedUsers = new HashMap<String, Socket>();
	final Map<String, Integer> usersToLoginCount = new HashMap<String, Integer>();
	final static List<String> notLoggedUsers=new LinkedList<String>();
	final Map<String,User> allUsers= new HashMap<String,User>();
}
