
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.ObjectOutputStream.PutField;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;


public class Servers extends Thread {

	private ArrayList<SubServer> clientList;
    final private ServerSocket masterServerSock;
    final public static int MAX_CLIENTS = 15;
   
    final private SubServer[] subServersForClients = new SubServer[ MAX_CLIENTS ];
    //public ConcurrentHashMap<Object,Socket> clients;
    
    
    Semaphore token;
    
    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println("Usage: java Servers <port number>");
            System.exit(1);
        }
        
        	//get the port# as an int
            int portNumber = Integer.parseInt(args[0]);
            new Servers(portNumber);
            

        }

    public Servers( int port ) throws IOException {
    	
         this.masterServerSock = new ServerSocket( port );
         token = new Semaphore(2);
         
         /*
          * Trying to figure out implementation to have a list of clients
          * to send them all messages
         clientList = new ArrayList<SubServer>();
         clients = new HashMap<Object,Socket>();
         Collections.synchronizedMap(clients);
         */
         
       
         
         start();
    
    }

    @Override
    public void run() {
    	
    	
        while ( !Servers.interrupted() ) {
             //wait for clients
             Socket connection;
             
			try {
				
				//handle connection
				connection = this.masterServerSock.accept();
				
				//sub server to handle clients
				assignConnectionToSubServer( connection );
				
				//print out information
				System.out.println(connection.toString());
			} catch (IOException e) {
				
				System.err.println("Failed to connect to client ");
				e.printStackTrace();
				System.exit(1);
			}
             
        }
	}

	public void assignConnectionToSubServer(Socket connection) {

		for (int i = 0; i < MAX_CLIENTS; i++) {

			// find an unassigned subserver (waiter)
			SubServer currentSubServer = this.subServersForClients[i];
			
			if (currentSubServer == null) {

				currentSubServer = new SubServer(connection, i, token);
//				/*Trying to figure out implementation to have a list of clients
//				 * to send them all messages
//				clients = new ConcurrentHashMap<Object,Socket>();
//				clients.put(connection.toString(), connection);

				break;
				
			}
		}
	}
	
	public void send(String msg) {
		
//		for (SubServer client: clientList){
//			
//			client.output.write(msg);
//			System.out.println("Supposed to go through clients");
//			client.output.println(msg);
//			client.output.flush();
//		}
//		for (int i = 0; i < MAX_CLIENTS; i++) {
//		// find an unassigned subserver (waiter)
//					SubServer currentSubServer = this.subServersForClients[i];
//					
//					//occupied server! (handling existing client)
//					if (currentSubServer != null) {
//
//						PrintStream outToClient = null;
//						try{
//							
//							outToClient = new PrintStream((lineFromServer));
//							outToClient.println(msg);
//						} catch (IOException e){
//							
//							System.err.println("Something wrong in sending to all clients");
//							
//						}
						
						/*
						* Trying to figure out implementation to have a list of clients
						* to send them all messages
						* */
						//clientList.add(currentSubServer);
						//break;
						//
					//}
//		}
//		
//
//		Iterator<Object> it = clients.keySet().iterator();
//
//		while (it.hasNext()) {
//			try {
//
//				PrintStream output = new PrintStream(clients.get(it.next()).getOutputStream(), true);
//				System.out.println("Supposed to go through clients");
//				PrintWriter out = new PrintWriter(clients.get(it.next()).getOutputStream(), true);
//				out.println(msg);
//				output.println(msg);
//			} catch (IOException e) {
//				System.out.println("ERROR");
//			}
//		}
	}
	

    protected class SubServer extends Thread {

        final private int clientId;
        final private Socket clientConnection;
        private OutputStream output;
        private Semaphore token;

        //you can store additional client properties here if you want, for example:
        //private int m_gameRating = 1500;

		public SubServer(Socket connection, int id, Semaphore semaIn) {
			this.clientId = id;
			this.clientConnection = connection;
			try {
				output = clientConnection.getOutputStream();
			} catch (IOException e) {
				System.err.println("A player's connection has been lost!");
				// e.printStackTrace();
			}
			this.token = semaIn;
			start();
        }

		@Override
		public void run() {
			while (!SubServer.interrupted()) {
				// process a client request

				// Using the TextAdventure protocol (to be implemented) 
				// write all client replies to a file
				try {
					token.acquire();
				} catch (InterruptedException e) {
					//
					System.err.println("Token interruption");
					e.printStackTrace();
				}

				processMessage();
				token.release();

				
				
			}
		}

        //as an example, if you read String messages from your client,
        //just call this method from the run() method to process the client request
        public void processMessage() {
        	

			
			PrintWriter output=null;
			try {
				output = new PrintWriter(clientConnection.getOutputStream(), true);
			} catch (IOException e1) {
				System.err.println("A player's connection has been lost!");
				// e.printStackTrace();
			}
			
			
			// Initiate conversation with client
			// Knock Knock Protocol:
			/*
			 * Serves up the jokes. 1) It keeps track of the current joke 2) the
			 * current state (sent knock knock, sent clue, and so on), 3)
			 * returns the various text pieces of the joke depending on the
			 * current state.
			 * 
			 */
			
			//Welcome being sent
			String inputLine;
			String outputLine;
			
			//TODO
			//Change to create new Adventure Main and recieve the strings sent
			KnockKnockProtocol kkp = new KnockKnockProtocol();
			//No client input to pass
			//Passing null tells the protocol that
			//we want the welcome message
			
			//TODO
			//recieve line from Adventure Main and print to the client
			outputLine = kkp.processInput(null);
			output.println(outputLine);
			
			

			
			// While loop initiates when client replies
			try {

				//File to write responses in (for multiclient implementation)
				//String fileName = "responses.txt";
				//PrintWriter file = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)));
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));

				while ((inputLine = reader.readLine()) != null) {

					outputLine = kkp.processInput(inputLine);

					output.println(outputLine);
				
					//output.println(inputLine);

					//Timer placeholder (future implementation)

					// if server is done speaking to client exit
					// without saying anything
					if (outputLine.equals("Bye.")){

						break;
					}
				}
				
			} catch (SocketTimeoutException ste) {
				System.err.println("A player is taking too long! \n" + "\n....\n" + " \nPlayer has been TERMINATED"
						+ "\n---------------------------\n");
			} catch (IOException e) {
				System.err.println("A player's connection has been lost!...Dropping them");
				token.release();
				close();
				//e.printStackTrace();
				//System.exit(-1);
			} 
        }

         //terminates the connection with this client (i.e. stops serving him)
        public void close() {
            try {
                 this.clientConnection.close();
            } catch ( IOException e ) {
                 //ignore
            }
        }
        
        
    }
    
    


}