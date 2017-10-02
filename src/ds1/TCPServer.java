package ds1;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer implements Server {
	private ServerSocket socket;
	
	public TCPServer() {
		try {
			socket = new ServerSocket(1234);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		System.out.println("Starting server...");			
		while(true) {
			Socket request;
			try {
				request = socket.accept();
				handleRequest(request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void handleRequest(Socket request) {
		new Handler(request).handle();
	}
}
