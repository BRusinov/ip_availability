package ip_availability;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

//import com.sun.security.ntlm.Client;

public class EchoServer implements General {
	private final int port;
	private boolean running;
	private final List<User> clients =Collections.synchronizedList(new LinkedList<User>());
	private ServerSocket serverSocket;
	
	public EchoServer(int port){
		this.port=port;
	}
	
	public void startServer() throws IOException {
		final ServerSocket localServerSocket= createServerSocket();
		while(isRunning()){
			final Socket socket;
			try{
				socket=localServerSocket.accept();
			} catch (SocketException e) {
				if(!localServerSocket.isClosed()){
					throw e;
				}
				break;
			}
			final User client=new User(this, socket);
			clients.add(client);
			new Thread(client).start();
		}
	}
	
	private synchronized ServerSocket createServerSocket() throws IOException {
		if(running){
			throw new IllegalStateException("Already running");
		}
		running=true;
		serverSocket= new ServerSocket(port);
		return serverSocket;
	}
	
	public synchronized boolean isRunning() {
		return running;
	}
	
	public synchronized void stopServer() throws IOException{
		if(!running){
			throw new IllegalStateException("Not running");
		}
		running=false;
		serverSocket.close();
		serverSocket=null;
		for (User next:clients) {
			next.stopClient();
		}
	}

	public void onClientStopped(User user){
		LinkedList<String> usernames= new LinkedList<String>();
		currentlyLoggedUsers.remove(user);
		for (String name : currentlyLoggedUsers.keySet()) {
			if(currentlyLoggedUsers.get(name)==user.getSocket()){
				usernames.add(name);
			}
		}
		for (String name : usernames) {
			currentlyLoggedUsers.remove(name);
		}
		clients.remove(user);
	}
}
