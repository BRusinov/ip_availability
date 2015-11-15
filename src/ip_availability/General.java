package ip_availability;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface currentlyLoggedUsers {
	final List<String> currentlyLoggedUsers = new LinkedList<String>();
	final Map<String, Integer> usersToLoginCount = new HashMap<String, Integer>();
	final static List<String> notLoggedUsers=new LinkedList<String>();
}
