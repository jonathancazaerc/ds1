package ds1;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
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
		try {
			Writer w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			w.append("abc");
			w.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
