package ds1;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
	Socket socket;
	
	public Client() {
		try {
			socket = new Socket("localhost", 1234);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void pull(String s) {
		System.out.println("Pulling file: " + s);
		try {
			BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			w.append(s);
			w.flush();
			socket.shutdownOutput();
			
			DataInputStream r = new DataInputStream(socket.getInputStream());
			System.out.print(r.readUTF());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
