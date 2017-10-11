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

public abstract class BaseClient {
	public Socket socket;
	public InetAddress host;
	public int port;
	public long expectedFileSize;
	public long actualFileSize;
	
	FileOutputStream getFileOutputStream(String fileName) throws FileNotFoundException {
		String outputFile = "tmp/" + fileName;

		FileOutputStream fos = new FileOutputStream(outputFile);
		return fos;
	}
	
	abstract public void requestFile(String filename) throws IOException;
	abstract public InputStream getInputStream() throws IOException;
	
	/*
	 * Sends the file name to the server, then reads the expected file size and
	 * pipes the rest of the input stream to the file.
	 */
	public void pull(String fileName) {
		System.out.println("Pulling file: " + fileName);
		try {			
			this.requestFile(fileName);
			
			InputStream in = getInputStream();
			this.readExpectedFileSize(in);
			
			FileOutputStream fos = getFileOutputStream(fileName);
			this.readAndWriteFile(in, fos);
			
			fos.close();
			socket.close();
			
			if (this.actualFileSize != this.expectedFileSize) {
				System.out.println("File size doesn't match: received " + this.expectedFileSize + " vs " + this.actualFileSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Read expected byte size from the first 8 bytes from a stream
	 */
	void readExpectedFileSize(InputStream in) throws IOException {
	    byte[] fileSizeBuffer = new byte[Long.BYTES];
	    in.read(fileSizeBuffer, 0, 8);
	    this.expectedFileSize = Util.convertByteArrayToLong(fileSizeBuffer);
	    System.out.println(this.expectedFileSize);
	}
	
	/*
	 * Pipes the in stream to the out stream until EOF, counting the size
	 */
	void readAndWriteFile(InputStream in, FileOutputStream fos) throws IOException {
		byte[] buffer = new byte[(int) Math.pow(2, 22)];
		int count;
		
	    long actualFileSize = 0;
		while((count = in.read(buffer)) >= 0){
			fos.write(buffer, 0, count);
			actualFileSize += count;
		}
		this.actualFileSize = actualFileSize;
	}

}
