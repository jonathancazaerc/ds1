package ds1;

// commands
// java -jar ds1.jar serve
// java -jar ds1.jar pull file.txt

public class DS1 {

	public static void main(String[] args) {
		if (args[0] == null) printUsage();
		if (args[1] == null) printUsage();
		if (args[0].equals("serve")) {
			Server server;
			if (args[1] == "tcp") {
				server = new TCPServer();
			} else if (args[1] == "udp") {
				server = new UDPServer();
			} else {
				printUsage();
				return;
			}
			server.start();
		} else if (args[0].equals("pull")) {
			Client client = new Client();
			client.pull(args[1]);
		} else {
			printUsage();
		}
	}
	
	static void printUsage() {
		System.out.println("Usage:");
		System.out.println("ds1 serve tcp");
		System.out.println("ds1 serve udp");
		System.out.println("ds1 pull file.txt");
	}

}
