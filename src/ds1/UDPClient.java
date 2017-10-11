package ds1;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient extends BaseClient implements Client {
	DatagramSocket socket;

	public UDPClient() {
			try {
				host = InetAddress.getByName("localhost");
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
	        port = Constants.UDP_PORT;
			try {
				socket = new DatagramSocket(Constants.UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
	}
}
