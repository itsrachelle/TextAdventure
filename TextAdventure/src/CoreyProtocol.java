import java.net.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.io.*;

public class CoreyProtocol {

	private static final int WAITING = 0;
	private static final int SENTADVS = 1;
	private static final int STARTADV = 2;

	private static final int NUMJOKES = 5;

	private int state = WAITING;

	private HashMap<String, String> adventures;
	private HashMap<String, String> curAdventure;
	private String curTile = "S";

	public CoreyProtocol() {
		
		Charset charset = Charset.forName("US-ASCII");
		
		adventures = new HashMap<String, String>();
		curAdventure = new HashMap<String, String>();
		
		//opens adventure file that contains name of corresponding adventure name
		try (BufferedReader reader = Files.newBufferedReader(Paths.get("Adventure.txt"), charset)) {
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				
				// Populate adventures map
				String[] key_value = line.split("|");
				adventures.put(key_value[0], key_value[1]);
				
				//Key is 
				System.out.println("KEY VALUE IS: "+key_value[0]);
				
			}
			
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
		
	}

	public void setAdventure(String index) {
		String path = adventures.get(index) + ".txt";
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), charset)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				// Populate the adventure
				String[] key_value = line.split("|");
				curAdventure.put(key_value[0], key_value[1]);
			}
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}

	}

	public String processInput(String input) {
		String output = null;
		if (state == WAITING) {
			Iterator<String> adv_iter = adventures.values().iterator();
			while (adv_iter.hasNext()) {
				output += adv_iter.next();
			}
			state = SENTADVS;
		} else if (state == SENTADVS) {
			setAdventure(input);
			output = curAdventure.get("S");
			if (output != null) {
				state = STARTADV;
			} else {
				output = "INVALID ADVENTURE";
			}
		} else if (state == STARTADV) {
			output = curAdventure.get(input);
			if (output != null) {
				curTile = input;
			} else {
				output = "INVALID OPTION";
			}
		}
		return output;
	}
}
