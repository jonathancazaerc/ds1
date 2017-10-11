package ds1;

// commands
// java -jar ds1.jar serve
// java -jar ds1.jar pull file.txt

public class DS1 {

	public static void main(String[] args) {
		if (args[0] == null) printUsage();
		if (args[1] == null) printUsage();
		if (args[0].equals("serve")) {
			System.out.println("Serving mode...");
			Server server;
			if (args[1].equals("tcp")) {
				System.out.println("TCP mode...");
				server = new TCPServer();
			} else if (args[1].equals("udp")) {
				System.out.println("UDP mode...");
				server = new UDPServer();
			} else {
				System.out.println("Neither TCP nor UDP");
				printUsage();
				return;
			}
			server.start();
		} else if (args[0].equals("pull")) {
			BaseClient client;
			if (args[2] == null) printUsage();
			if (args[1].equals("tcp")) {
				client = new TCPClient();
			} else if (args[1].equals("udp")){
				client = new UDPClient(); //changed for UDP
			} else {
				System.out.println("Neither TCP nor UDP");
				printUsage();
				return;
			}
			client.pull(args[2]);
		} else {
			printUsage();
		}
	}

	static void printUsage() {
		System.out.println("Usage:");
		System.out.println("ds1 serve tcp");
		System.out.println("ds1 serve udp");
		System.out.println("ds1 pull tcp file.txt");
		System.out.println("ds1 pull udp file.txt");
	}

}
