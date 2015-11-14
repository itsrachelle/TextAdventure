
import java.net.*;
import java.io.*;

public class KKMultiServer {
	
	
	
    public static void main(String[] args) throws IOException {

    if (args.length != 1) {
        System.err.println("Usage: java KKMultiServer <port number>");
        System.exit(1);
    }
    
    	//get the port# as an int
        int portNumber = Integer.parseInt(args[0]);
        boolean listening = true;

    	//server socket constructor handles any port related exceptions
        try (ServerSocket serverSocket = new ServerSocket(portNumber)) { 
        	//always accepting new clients and starting a new thread
        	// might use tokens (semaphore implementation)
            while (listening) {
	            new KKMultiServerThread(serverSocket.accept()).start();
	            
	        }
	    } catch (IOException e) {
            System.err.println("Could not listen on port " + portNumber);
            System.exit(-1);
        }
    }
}
