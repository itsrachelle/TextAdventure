import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.CharBuffer;
import java.util.ArrayList;

public class Client {

	ArrayList<String> optionList = null;
	String intialScene = "";
	BufferedReader input;
	PrintWriter output;
	Socket clientSock;

	public Client(String hostNameIn, int portNumIn) {
		// connects to server
		// grab i/o streams
		// wait for response (send/receive)

		optionList = new ArrayList<String>();
		//InetAddress loopback = null;
		//int portNumIn = 1010;
		try {
			clientSock = new Socket(hostNameIn, portNumIn);
			output = new PrintWriter(clientSock.getOutputStream());
			input = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			this.receive();
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostNameIn);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostNameIn);
			System.exit(1);
		}

	}

	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		new Client(hostName, portNumber);
	}

	private void send(String choice) {
		output.write(choice);
	}

	private void receive() {
		try {
			StringBuffer buffer = new StringBuffer();
			CharBuffer cbuf = null;

			//need to check for ending character
			//(based of code from KnockKnockClient):
			
/*			if (lineFromServer.contains("Bye")) {
				
				break;
				
			}*/
			input.read(cbuf);
			buffer.append(cbuf);
			int splitIndex = buffer.toString().lastIndexOf("|");
			String decisions = buffer.toString().substring(splitIndex, buffer.toString().length());
			String[] options = decisions.split(";");
			for (String arg : options) {
				optionList.add(arg);
			}
			decision(buffer);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void decision(StringBuffer buffer) {
		BufferedReader stdInput = new BufferedReader(new InputStreamReader(System.in));
		String inputChoice = null;
		while ((!(optionList.contains(inputChoice)) || (inputChoice == null))) {
			System.out.println(buffer.toString());
			try {
				inputChoice = stdInput.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		send(inputChoice);

	}

}
