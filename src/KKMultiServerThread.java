
import java.net.*;
import java.io.*;

public class KKMultiServerThread extends Thread {
	private Socket socket = null;
	private static int twoMinutesInMilliseconds = 12 * 10000;
	private static int oneMinuteInMilliseconds = 12 * 10000;

	public KKMultiServerThread(Socket socket) {
		super("KKMultiServerThread");
		this.socket = socket;
	}

	public void run() {

		try (

		PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));) {

			String inputLine;
			String outputLine;

			// Initiate conversation with client
			// Knock Knock Protocol:
			/*
			 * Serves up the jokes. 1) It keeps track of the current joke 2) the
			 * current state (sent knock knock, sent clue, and so on), 3)
			 * returns the various text pieces of the joke depending on the
			 * current state.
			 * 
			 */
			KnockKnockProtocol kkp = new KnockKnockProtocol();
			outputLine = kkp.processInput(null);
			output.println(outputLine);
			// server timeout
			Timer timer = new Timer(twoMinutesInMilliseconds);

			// socket timeout
			socket.setSoTimeout(oneMinuteInMilliseconds);

			System.out.println("");
			System.out.println("\nStarting Server Timeout Timer \n");
			// starting timer
			timer.start();

			// while loop initiates when client replies
			try {
				while ((inputLine = input.readLine()) != null) {
					outputLine = kkp.processInput(inputLine);
					output.println(outputLine);

					timer.reset();

					// if server is done speaking to client exit
					// without saying anything
					if (outputLine.equals("Bye."))
						break;
				}
			} catch (SocketTimeoutException ste) {
				System.err.println("Someone is taking too long! \n" + "\n....\n" + " \nClient has been TERMINATED"
						+ "\n---------------------------\n");
			}
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}