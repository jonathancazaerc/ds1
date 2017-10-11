package ds1;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public abstract class BaseClient {
	public Socket socket;
	public InetAddress host;
	public int port;
	public long expectedFileSize;
	public long actualFileSize;
	
	/*
	 * Sends the file name to the server, then reads the expected file size and
	 * pipes the rest of the contents to the file.
	 */
	public void pull(String fileName) {
		System.out.println("Pulling file: " + fileName);
		try {			
			this.requestFile(fileName);
			
			this.readExpectedFileSize();
			
			System.out.println("Expecting " + this.expectedFileSize + " bytes");

			FileOutputStream fos = getFileOutputStream(fileName);
			this.readAndWriteFile(fos);
			
			fos.close();			
			finish();
			
			if (this.actualFileSize != this.expectedFileSize) {
				System.out.println("File size doesn't match: received " + this.expectedFileSize + " vs " + this.actualFileSize);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	FileOutputStream getFileOutputStream(String fileName) throws FileNotFoundException {
		String outputFile = "tmp/" + fileName;

		FileOutputStream fos = new FileOutputStream(outputFile);
		return fos;
	}
	
	/*
	 * Send file name
	 */
	abstract public void requestFile(String filename) throws IOException;
	
	/*
	 * Close sockets if necessary
	 */
	abstract public void finish() throws IOException;
	
	/*
	 * Read expected byte size from the first 8 bytes
	 */
	abstract public void readExpectedFileSize() throws IOException;
	
	/*
	 * Writes to the file until EOF, counting the size
	 */
	abstract void readAndWriteFile(FileOutputStream fos) throws IOException;

}
