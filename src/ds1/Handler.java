package ds1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
			BufferedReader r = new BufferedReader(new InputStreamReader(request.getInputStream()));
			
			String s = r.readLine().replace("\n", "").replace("\r", "");

			String fileName = "files/" + s ;
			Path path = Paths.get(fileName);

			System.out.println("Writing file: " + s + "at (" + path.toAbsolutePath().toString() + ")");

			DataOutputStream w = new DataOutputStream(request.getOutputStream());

			Stream<String> stream = Files.lines(path);
			stream.forEach((String l) -> {
				System.out.println(l);
				try {
					w.writeUTF(l);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			stream.close();		

			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
