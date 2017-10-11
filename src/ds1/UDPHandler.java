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
	
	public void run() {
		try {
			String relativeFileName = this.receiveFileName();
			path = getFullPath(relativeFileName);
		
			FileInputStream fis = new FileInputStream(path.toAbsolutePath().toString());
			BufferedInputStream bfis = new BufferedInputStream(fis);
			long fileSize = fis.getChannel().size();
			
			System.out.println(
					"Writing file: " + relativeFileName
					+ " at " + path.toAbsolutePath().toString()
					+ " with size " + fileSize);

			DatagramPacket fileSizePacket = new DatagramPacket(Util.convertLongToByteArray(fileSize), 8, socket.getInetAddress(), socket.getPort());
			socket.send(fileSizePacket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String receiveFileName() throws IOException {
		String s = new String(packet.getData()).trim();
		return s;
	}
	
}
