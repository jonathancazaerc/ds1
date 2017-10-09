package ds1;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.ByteBuffer;

public class BaseClient {
	Socket socket;
	InetAddress host;
	int port;
	
	FileOutputStream getFileOutputStream(String fileName) throws FileNotFoundException {
		String outputFile = "tmp/" + fileName;

		FileOutputStream fos = new FileOutputStream(outputFile);
		return fos;
	}
	
	void askFile(String fileName) throws IOException {
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		w.append(fileName);
		w.flush();
		socket.shutdownOutput();
	}
	
	public void pull(String fileName) {
		System.out.println("Pulling file: " + fileName);
		try {
			askFile(fileName);

			FileOutputStream fos = getFileOutputStream(fileName);
			InputStream in = socket.getInputStream();
			
			byte[] buffer = new byte[(int) Math.pow(2, 22)];
			int count;
			
		    byte[] fileSizeBuffer = new byte[Long.BYTES];
		    in.read(fileSizeBuffer, 0, 8);
		    long fileSize = convertByteArrayToLong(fileSizeBuffer);
		    long actualFileSize = 0;

			while((count = in.read(buffer)) >= 0){
				fos.write(buffer, 0, count);
				actualFileSize += count;
			}
			
			if (actualFileSize != fileSize) {
				System.out.println("File size doesn't match: received " + fileSize + " vs " + actualFileSize);
			}
			fos.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	long convertByteArrayToLong(byte[] arr) {
	    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
	    b.put(arr);
	    b.flip();
	    return b.getLong();		
	}
}
