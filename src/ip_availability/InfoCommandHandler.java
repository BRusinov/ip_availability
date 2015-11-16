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
		this.user = user;
	}

	public String Info(String string) throws IOException {
		final String[] split = string.split(":");
		final PrintStream out = new PrintStream(socket.getOutputStream());
		String result = "ok";
		if (currentlyLoggedUsers.containsKey(user.name)) {
			result += ":" + split[1] + ":" + (currentlyLoggedUsers.containsKey(split[1]) ? "true" : "false") + ":"
					+ usersToLoginCount.get(split[1]);
			for (Interval interval : user.date) {
				if (interval.end != null)
					result += ":" + interval.dateFormat.format(interval.start) + ":"
							+ interval.dateFormat.format(interval.end);
				else
					result += ":" + interval.dateFormat.format(interval.start);
			}
			
			return result;
		}

		return "error:notlogged";
	}

}
