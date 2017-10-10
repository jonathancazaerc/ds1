package ds1;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Handler extends Thread {
	Socket request;
	private BufferedInputStream in;
	
	public Handler(Socket request) {
		this.request = request;
	}

	public void handle() {
		this.start();
	}
	
	public void run() {
		try {
			BufferedReader r = new BufferedReader(new InputStreamReader(request.getInputStream()));
			
			String s = r.readLine().replace("\n", "").replace("\r", "");

			String fileName = "files/" + s ;
			Path path = Paths.get(fileName);

			System.out.println("Writing file: " + s + " at (" + path.toAbsolutePath().toString() + ")");
			
			int count;
			byte[] buffer = new byte[1024];

			OutputStream out = request.getOutputStream();
			FileInputStream fis = new FileInputStream(fileName);
			in = new BufferedInputStream(fis); 
			
			long fileSize = fis.getChannel().size();
		    ByteBuffer fileSizeBuffer = ByteBuffer.allocate(Long.BYTES);
		    fileSizeBuffer.putLong(fileSize);
		    out.write(fileSizeBuffer.array(), 0, Long.BYTES);
			
			while ((count = in.read(buffer)) > 0) {
			     out.write(buffer, 0, count);
			     out.flush();
			}
			
			System.out.println("File is transfered!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
}
