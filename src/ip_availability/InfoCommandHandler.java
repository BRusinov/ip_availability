package ip_availability;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;

@SuppressWarnings("unused")

public class InfoCommandHandler implements General {
	private final String command;
	private final Socket socket;
	private final User user;
	
	public InfoCommandHandler(String command, Socket socket, User user) {
		this.command = command;
		this.socket = socket;
		this.user=user;
	}

	public String Info(String string) throws IOException {
		final String[] split = string.split(":");
		User consumer= allUsers.get(split[1]);
		final PrintStream out = new PrintStream(socket.getOutputStream());
		String result = "ok";
		if (currentlyLoggedUsers.containsKey(user.name)) {
			result += ":" + split[1] + ":" + (currentlyLoggedUsers.containsKey(consumer.name) ? "true" : "false") + ":" + usersToLoginCount.get(consumer.name);
			for (Interval interval : consumer.date) {
				if (interval.end != null) result += ":" + interval.dateFormat.format(interval.start) + ":" + interval.dateFormat.format(interval.end);
				else result += ":" + interval.dateFormat.format(interval.start);
			}
			return result;
		}
		return "error:notlogged";
	}

}
