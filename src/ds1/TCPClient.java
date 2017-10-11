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

	public InputStream getInputStream() throws IOException {
		return socket.getInputStream();
	}
}
