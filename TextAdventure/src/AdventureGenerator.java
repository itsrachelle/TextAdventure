import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AdventureGenerator {

	public AdventureGenerator() {
	}
	
	private static ArrayList<String> getBeginnings() {
		ArrayList<String> begins = new ArrayList<>(Arrays.asList("The ", "Our  ", "That "));
		
		return begins;
	}
	
	private static ArrayList<String> getAdjectives() {
		ArrayList<String> adjects = new ArrayList<>(Arrays.asList("Tenacious ", "Intrepid ", "Brave ", "Courageous ", "Foolish ", "Haughty ", "Suicidial ", "Dim-Witted "));
		
		return adjects;
	}
	
	private static ArrayList<String> getGroupNouns() {
		ArrayList<String> groupNouns= new ArrayList<>(Arrays.asList("Group ", "Party ", "Entourage ", "Troope ", "band of Companions ", "Friends ", "pack of Mooks ", "squad of Sell-Swords "));
		
		return groupNouns;
	}

	private static ArrayList<String> getIntro() {
		ArrayList<String> subjects = new ArrayList<String>();
		
		int count = 10;
		
		while (count > 0) {
			subjects.add(randomPick(getBeginnings()) + randomPick(getAdjectives()) + randomPick(getGroupNouns()));
			count--;
		}
		return subjects;
	}
	

	private static ArrayList<String> getPredicate() {
		ArrayList<String> predicates = new ArrayList<>(Arrays.asList("get drunk with ", "rush forwards and do battle with ", "flee in terror from ", "stare, mouths agape ", "totally obliterate "));
		return predicates;
	}
	
	private static ArrayList<String> getBeginMobs() {
		ArrayList<String> mobBegins = new ArrayList<>(Arrays.asList("the ","another ","a ","a pack of ","a floating, ",""));
		return mobBegins;
	}
	
	private static ArrayList<String> getAdjectiveMobs() {
		ArrayList<String> mobAdject = new ArrayList<>(Arrays.asList("tiny ","adorable ","terrifying ","adventuring ","huge ","completely invisible "));
		return mobAdject;
	}
	
	private static ArrayList<String> getMobs() {
		ArrayList<String> mobs = new ArrayList<>(Arrays.asList("Minotaur ","Box of Puppies ","Party ","Beholder ","Displacer Beast ","Mind Flayer ", "Sphere of Ahnihilation "));
		return mobs;
	}
	
	private static ArrayList<String> getEncounters() {
		
		ArrayList<String> objects = new ArrayList<String>();
		
		int count = 10;
		
		while (count > 0) {
			objects.add(randomPick(getPredicate())+ randomPick(getBeginMobs()) + randomPick(getAdjectiveMobs()) + randomPick(getMobs()));
			count--;
		}
		
		return objects;
	}
	
	private static ArrayList<String> getComplement() {
		// do
		ArrayList<String> complements = new ArrayList<>(Arrays.asList("", "", ""));
		return complements;
	}
	
	static String randomPick(ArrayList<String> choices) {
		Random randomGenerator = new Random();
		int choice = randomGenerator.nextInt(choices.size());
		return choices.get(choice);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		 ArrayList<String> subject = getIntro();
		 ArrayList<String> object =  getEncounters();
		 
		 int count = 15;
		 while(count > 0) {
			 String out = randomPick(subject) + randomPick(object);
			 System.out.println(out);
			 count--;
		 }
	
	}
}
