package ds1;

// commands
// java -jar ds1.jar serve
// java -jar ds1.jar pull file.txt

public class DS1 {

	public static void main(String[] args) {
		System.out.println(args[0]);
		if (args[0].equals("serve")) {
			Server server = new Server();
			server.start();
		} else if (args[0].equals("pull")) {
			Client client = new Client();
			client.pull(args[1]);
		}

	}

}
