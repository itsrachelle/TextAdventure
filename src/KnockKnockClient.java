
import java.io.*;
import java.net.*;

public class KnockKnockClient {
	public static void main(String[] args) throws IOException {

		if (args.length != 2) {
			System.err.println("Usage: java EchoClient <host name> <port number>");
			System.exit(1);
		}

		String hostName = args[0];
		int portNumber = Integer.parseInt(args[1]);

		try (Socket kkSocket = new Socket(hostName, portNumber);
				PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
				BufferedReader inputComingFromServer = new BufferedReader(
						new InputStreamReader(kkSocket.getInputStream()));) {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String lineFromServer;
			String stdInFromUser;

			while ((lineFromServer = inputComingFromServer.readLine()) != null) {
				System.out.println("Server: " + lineFromServer);
				if (lineFromServer.contains("Bye")){
					//System.out.println("3333");
					break;
				}

				stdInFromUser = stdIn.readLine();
				if (stdInFromUser != null) {
					System.out.println("Client: " + stdInFromUser);

					out.println(stdInFromUser);
					
				}
			}
			System.out.println("Done");
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host " + hostName);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " + hostName);
			System.exit(1);
		}
	}
}
