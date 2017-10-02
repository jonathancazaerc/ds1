package ds1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class UDPServer implements Server {
	private DatagramSocket socket;
	
	public UDPServer() {
		try {
			socket = new DatagramSocket(1234);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		byte[] buffer = new byte[1000];
		try {
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			socket.receive(request);
			DatagramPacket reply = new DatagramPacket(
					request.getData(),
					request.getLength(),
					request.getAddress(),
					request.getPort());
			socket.send(reply);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
