package ds1;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TCPHandler extends BaseHandler {
	public Socket socket;
	
	public TCPHandler(Socket socket) {
		this.socket = socket;
	}

	public String receiveFileName() throws IOException {	    	    
		InputStream in = socket.getInputStream();
		
		System.out.println(in.available());
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		
		while(!bin.ready()) {}
		String s = bin.readLine().trim();
		return s;
	}
	
	public void sendFileSizeAndFile() throws IOException {
		OutputStream out = socket.getOutputStream();
		
	    out.write(Util.convertLongToByteArray(fileSize), 0, Long.BYTES);
	    
	    System.out.println("File size: " + fileSize);

	    int count;
	    byte[] buffer = new byte[(int) Math.pow(2, 22)];
 		while ((count = bfis.read(buffer)) >= 0) {
		    out.write(buffer, 0, count);
		    out.flush();
		}
		
		out.close();
		bfis.close();
	}
	
}
