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
	
	public void run() {
		try {
			String relativeFileName = this.receiveFileName();
			path = getFullPath(relativeFileName);
			System.out.println("Writing file: " + relativeFileName + " at " + path.toAbsolutePath().toString());
		
			int count;
			byte[] buffer = new byte[(int) Math.pow(2, 10)];

			OutputStream out = socket.getOutputStream();
			
			FileInputStream fis = new FileInputStream(path.toAbsolutePath().toString());
			BufferedInputStream bfis = new BufferedInputStream(fis);
			long fileSize = fis.getChannel().size();
			
		    out.write(Util.convertLongToByteArray(fileSize), 0, Long.BYTES);
		    
		    System.out.println("File size: " + fileSize);

			while ((count = bfis.read(buffer)) >= 0) {
			    out.write(buffer, 0, count);
			    out.flush();
			}
			
			out.close();
			bfis.close();
			System.out.println("File is transferred!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String receiveFileName() throws IOException {	    	    
		InputStream in = socket.getInputStream();
		
		System.out.println(in.available());
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		
		while(!bin.ready()) {}
		String s = bin.readLine().trim();
		return s;
	}
}
