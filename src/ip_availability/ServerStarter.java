package ip_availability;

import java.io.IOException;


public class ServerStarter {

		private static final int SERVER_PORT = 50002;

		public static void main(String[] args) throws IOException {
			final EchoServer server=new EchoServer(SERVER_PORT);
			server.startServer();
		}
}
