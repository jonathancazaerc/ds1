package ds1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
		byte[] buffer = new byte[(int) Math.pow(2, 22)];
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		while(true){
			try {
				socket.receive(request);
				String s = new String(buffer, 0, request.getLength());
				System.out.println(s);
				request.setLength(buffer.length);
				//source: http://www.java2s.com/Code/Java/Network-Protocol/ReceiveUDPpockets.htm
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
