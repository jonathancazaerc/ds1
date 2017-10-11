package ds1;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPHandler extends BaseHandler {
	public DatagramSocket socket;
	public DatagramPacket packet;
	
	public UDPHandler(DatagramSocket socket, DatagramPacket packet) {
		this.socket = socket;
		this.packet = packet;
	}

	public String receiveFileName() throws IOException {
		String s = new String(packet.getData()).trim();
		return s;
	}

	public void sendFileSizeAndFile() throws IOException {
		DatagramPacket fileSizePacket = new DatagramPacket(Util.convertLongToByteArray(fileSize), 8, packet.getAddress(), packet.getPort());
		socket.send(fileSizePacket);

		int count;
		byte[] buffer = new byte[(int) Math.pow(2, 10)];
		
		while ((count = bfis.read(buffer)) >= 0) {
			DatagramPacket filePacket = new DatagramPacket(buffer, count, packet.getAddress(), packet.getPort());
			socket.send(filePacket);
		}
		
		bfis.close();
		
		String doneMessage = Constants.UDP_DONE_MESSAGE;
		DatagramPacket donePacket = new DatagramPacket(doneMessage.getBytes(), doneMessage.length(), packet.getAddress(), packet.getPort());
		socket.send(donePacket);
	}
		
}
