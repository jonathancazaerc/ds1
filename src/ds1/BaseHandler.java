package ds1;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseHandler extends Thread {
	public Path path;
	
	public void handle() {
		this.start();
	}
	
	public Path getFullPath(String fileName) {
		return Paths.get("files/" + fileName);
	}
}
