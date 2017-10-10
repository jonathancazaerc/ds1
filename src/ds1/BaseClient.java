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
	long expectedFileSize;
	long actualFileSize;
	
	FileOutputStream getFileOutputStream(String fileName) throws FileNotFoundException {
		String outputFile = "tmp/" + fileName;

		FileOutputStream fos = new FileOutputStream(outputFile);
		return fos;
	}
	
	void askFile(String fileName) throws IOException {
		BufferedWriter w = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		w.append(fileName+"\n");
		w.flush();
		socket.shutdownOutput();
	}
	
	/*
	 * Sends the file name to the server, then reads the expected file size and
	 * pipes the rest of the input stream to the file.
	 */
	public void pull(String fileName) {
		System.out.println("Pulling file: " + fileName);
		try {
			this.askFile(fileName);

			FileOutputStream fos = getFileOutputStream(fileName);
			InputStream in = socket.getInputStream();
			
			this.readExpectedFileSize(in);
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
	    this.expectedFileSize = convertByteArrayToLong(fileSizeBuffer);
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
	
	/*
	 * Converts a 8-byte byte array to long
	 */
	long convertByteArrayToLong(byte[] arr) {
	    ByteBuffer b = ByteBuffer.allocate(Long.BYTES);
	    b.put(arr);
	    b.flip();
	    return b.getLong();		
	}
}
