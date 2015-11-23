import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AdventureGenerator {

	public AdventureGenerator() {
	}

	private static ArrayList<String> getSubject() {
		ArrayList<String> subjects = new ArrayList<>(Arrays.asList("The Tenacious Group ", "Our Intrepid Adventurers ", "The Brave Companions "));
		return subjects;
	}

	private static ArrayList<String> getPredicate() {
		ArrayList<String> predicates = new ArrayList<>(Arrays.asList("get drunk with ", "rush forwards at ", "flee in terror from "));
		return predicates;
	}
	
	private static ArrayList<String> getObjects() {
		ArrayList<String> predicates = new ArrayList<>(Arrays.asList("the tiny minotaur ", "the adorable box of puppies ", "another adventuring party "));
		return predicates;
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
		
		 ArrayList<String> subject = getSubject();
		 ArrayList<String> predicate =  getPredicate();
		 ArrayList<String> object =  getObjects();
		 
		 while(true) {
			 String out = randomPick(subject) + randomPick(predicate) + randomPick(object);
			 System.out.println(out);
		 }
	
	}
}
