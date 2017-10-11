package ds1;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient extends BaseClient {	
	public TCPClient() {
		try {
			this.host = InetAddress.getByName("localhost");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
        port = Constants.TCP_PORT;
		try {
			this.socket = new Socket(host, port);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestFile(String fileName) throws IOException {
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		w.append(fileName);
		w.flush();
		socket.shutdownOutput();
	}
	
	public void finish() throws IOException {
		socket.close();		
	}
	
	public void readExpectedFileSize() throws IOException {
		InputStream in = socket.getInputStream();
	    byte[] fileSizeBuffer = new byte[Long.BYTES];
	    in.read(fileSizeBuffer, 0, 8);
	    this.expectedFileSize = Util.convertByteArrayToLong(fileSizeBuffer);
	}
	
	public void readAndWriteFile(FileOutputStream fos) throws IOException {
		InputStream in = socket.getInputStream();
		
		byte[] buffer = new byte[(int) Math.pow(2, 22)];
		int count;
		
	    long actualFileSize = 0;
		while((count = in.read(buffer)) >= 0){
			System.out.println(new String(buffer));
			fos.write(buffer, 0, count);
			actualFileSize += count;
		}
		this.actualFileSize = actualFileSize;
	}
}
