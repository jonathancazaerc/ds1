package ds1;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
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

	public void finish() throws IOException {}

	public void readExpectedFileSize() throws IOException {
		byte[] buffer = new byte[8];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		socket.receive(packet);
		this.expectedFileSize = Util.convertByteArrayToLong(packet.getData());
	}

	public void readAndWriteFile(FileOutputStream fos) throws IOException {
	    long actualFileSize = 0;
		while(true) {
			byte[] buffer = new byte[(int) Math.pow(2, 22)];		
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			socket.receive(packet);
			
			if (packet.getLength() == Constants.UDP_DONE_MESSAGE.length()) {
				if (new String(packet.getData()).trim().equals(Constants.UDP_DONE_MESSAGE)) {
					System.out.println("Done packet detected");
					break;
				}
			}
			
			fos.write(packet.getData(), packet.getOffset(), packet.getLength());
			actualFileSize += packet.getLength();
		}
		this.actualFileSize = actualFileSize;
	}
}
