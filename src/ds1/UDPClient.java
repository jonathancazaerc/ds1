package ds1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends BaseClient implements Client {
	DatagramSocket socket;

	public UDPClient() {
			try {
				host = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        port = 1234;
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void pull(String fileName){
		byte[] buffer = new byte[(int) Math.pow(2, 12)];
		int count;
		DatagramPacket p = new DatagramPacket(buffer, buffer.length, host, port);
		try {
			socket.send(p);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
