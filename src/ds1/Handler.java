package ds1;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Handler extends Thread {
	Socket request;
	
	public Handler(Socket request) {
		this.request = request;
	}

	public void handle() {
		this.start();
	}
	
	public void run() {
		try {
			String fileName = "files/" + this.readFileName(request);
			
			Path path = Paths.get(fileName);

			System.out.println("Writing file: " + fileName + " at " + path.toAbsolutePath().toString());
			
			int count;
			byte[] buffer = new byte[(int) Math.pow(2, 10)];

			OutputStream out = request.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);
			BufferedInputStream bfis = new BufferedInputStream(fis);
			
			long fileSize = fis.getChannel().size();
		    ByteBuffer fileSizeBuffer = ByteBuffer.allocate(Long.BYTES);
		    fileSizeBuffer.putLong(fileSize);
		    out.write(fileSizeBuffer.array(), 0, Long.BYTES);
		    
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
	
	String readFileName(Socket request) throws IOException {	    	    
		InputStream in = request.getInputStream();
		
		System.out.println(in.available());
		
		BufferedReader bin = new BufferedReader(new InputStreamReader(in));
		
		while(!bin.ready()) {}
		String s = bin.readLine().replace("\n", "").replace("\r", "");
		return s;
	}
}
