package ds1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class UDPServer implements Server {
	public DatagramSocket socket;
	
	public UDPServer() {
		try {
			socket = new DatagramSocket(Constants.UDP_PORT);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		while(true) {
			byte[] buffer = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			handleRequest(packet);
		}
	}
	
	public void handleRequest(DatagramPacket packet) {
		new UDPHandler(socket, packet).handle();
	}

}
