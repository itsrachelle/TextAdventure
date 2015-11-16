

import java.net.*;
import java.io.*;

//Will be based of TextAdventure's behavior and processing
public class TextAdventureProtocol {
    private static final int STARTINTRO = 0;
    private static final int STARTEDADVENTURE = 1;
    private static final int SENTCLUE = 2;
    private static final int DEATHSENT = 3;

    private static final int NUMJOKES = 5;

    private int state = STARTINTRO;
    private int currentJoke = 0;
    
    

    private String[] clues = { "Turnip", "Little Old Lady", "Atch", "Who", "Who" };
    private String[] answers = { "Turnip the heat, it's cold in here!",
                                 "I didn't know you could yodel!",
                                 "Bless you!",
                                 "Is there an owl in here?",
                                 "Is there an echo in here?" };

    public String processInput(String theInput) {
        String theOutput = null;


        if (state == STARTINTRO) {
            theOutput = "Knock! Knock!";
            state = STARTEDADVENTURE;
        } else if (state == STARTEDADVENTURE) {
        	
        	
            if (theInput.equalsIgnoreCase("Who's there?") || theInput.equalsIgnoreCase("Whos there?")) {
                theOutput = clues[currentJoke];
               
                theOutput = answers[currentJoke] + " Want another? (y/n)";
                state = DEATHSENT;
                
                
                //state = SENTCLUE;
            } else {
                theOutput = "You're supposed to say \"Who's there?\"! " +
			    "Try again. Knock! Knock!";
            }
        } 
//        else if (state == SENTCLUE) {
//            if (theInput.equalsIgnoreCase(clues[currentJoke] + " who?")) {
//                theOutput = answers[currentJoke] + " Want another? (y/n)";
//                state = ANOTHER;
//            } else {
//                theOutput = "You're supposed to say \"" + 
//			    clues[currentJoke] + 
//			    " who?\"" + 
//			    "! Try again. Knock! Knock!";
//                state = SENTKNOCKKNOCK;
//            }
//        } 
            else if (state == DEATHSENT) {
            if (theInput.equalsIgnoreCase("y")) {

                theOutput = "Knock! Knock!";
                if (currentJoke == (NUMJOKES - 1))
                    currentJoke = 0;
                else
                    currentJoke++;
                state = STARTEDADVENTURE;
            } else {
                theOutput = "Bye.";
                state = STARTINTRO;
            }
        }
        return theOutput;
    }
}
