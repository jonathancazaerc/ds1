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
		byte[] buffer = new byte[2048];
		DatagramPacket request = new DatagramPacket(buffer, buffer.length);
		while(true){
			try {
				socket.receive(request);
				String s = new String(buffer, 0, request.getLength());
				System.out.println(s);
				request.setLength(buffer.length);
				//source: http://www.java2s.com/Code/Java/Network-Protocol/ReceiveUDPpockets.htm
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
