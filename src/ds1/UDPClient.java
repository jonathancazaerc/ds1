package ds1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class UDPClient {
	
	private DatagramSocket socket;
	private InetAddress host;
	private int port;

	public UDPClient(){
			try {
				host = InetAddress.getByName("localhost") ;
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        port = 1234 ;
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public void pull(String s){
		byte [] data = s.getBytes() ;
		DatagramPacket p = new DatagramPacket(data,data.length,host,port);
		try {
			socket.send(p);
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

}
