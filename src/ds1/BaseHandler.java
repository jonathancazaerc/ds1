package ds1;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseHandler extends Thread {
	public Path path;
	public BufferedInputStream bfis;
	public long fileSize;
	
	public void handle() {
		this.start();
	}
	
	public Path getFullPath(String fileName) {
		return Paths.get("files/" + fileName);
	}
	
	public void run() {
		try {
			String relativeFileName;
			relativeFileName = this.receiveFileName();
			path = getFullPath(relativeFileName);
			
			FileInputStream fis = new FileInputStream(path.toAbsolutePath().toString());
			bfis = new BufferedInputStream(fis);
			fileSize = fis.getChannel().size();
			
			System.out.println(
					"Writing file: " + relativeFileName
					+ " at " + path.toAbsolutePath().toString()
					+ " with size " + fileSize);
			
			sendFileSizeAndFile();
			
			System.out.println("File transferred");
			
			bfis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	abstract public String receiveFileName() throws IOException;
	abstract public void sendFileSizeAndFile() throws IOException;

}
