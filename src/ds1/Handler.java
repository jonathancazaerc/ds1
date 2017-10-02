package ds1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Handler extends Thread {
	Socket request;
	
	public Handler(Socket request) {
		this.request = request;
	}

	public void handle() {
		this.start();
	}
	
	public void run() {
		BufferedReader b;
		try {
			b = new BufferedReader(new InputStreamReader(request.getInputStream()));
			String s = b.readLine();
			System.out.println(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
