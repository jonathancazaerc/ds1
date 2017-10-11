package ds1;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends BaseClient {
	DatagramSocket socket;

	public UDPClient() {
			try {
				host = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	        port = Constants.UDP_PORT;
			try {
				socket = new DatagramSocket(); // no arguments: no port binding
			} catch (SocketException e) {
				e.printStackTrace();
			}
	}
	
	public void requestFile(String fileName) throws IOException {
		byte[] data = fileName.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, host, port);
	    socket.send(packet);
	}

	public InputStream getInputStream() throws IOException {
		byte[] buffer = new byte[(int) Math.pow(2, 22)];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		return new DataInputStream(new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength()));
	}
}
