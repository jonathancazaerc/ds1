package ds1;

import java.io.*;
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
			
			String outputFile = "output-"+s;
			
			FileOutputStream fos = new FileOutputStream(outputFile);
			BufferedOutputStream out = new BufferedOutputStream(fos);
			byte[] buffer = new byte[1024];
			int count;
			InputStream in = socket.getInputStream();
			while((count = in.read(buffer)) >= 0){
				fos.write(buffer, 0, count);
			}
			fos.close();
			socket.close();
			
			DataInputStream r = new DataInputStream(socket.getInputStream());
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
