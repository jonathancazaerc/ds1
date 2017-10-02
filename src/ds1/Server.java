package ds1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	private ServerSocket socket;
	
	public Server() {
		
	}
	
	void start() {
		try {
			socket = new ServerSocket(1234);
			while(true) {
				handleRequest(socket.accept());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void handleRequest(Socket request) {
		new Handler(request).handle();
	}
}
