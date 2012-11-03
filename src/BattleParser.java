/**
 * Takes the inputs from the user and constructs a battle command to tell the game
 * (similar to Parser class)
 * 
 * Author: Alok Swamy
 */

import java.util.StringTokenizer;

public class BattleParser {
    private BattleCommandWords commands;  // holds all valid command words

    /**
     * Create a parser to read from the terminal window.
     */
    public BattleParser() {
        commands = new BattleCommandWords();
    }

    public BattleCommand getBattleCommand(String s) {
    	StringTokenizer tokenizer = new StringTokenizer(s);
        String word = "";
        
    	if(tokenizer.hasMoreTokens()) {
    		word = tokenizer.nextToken();
    	}
    	
    	return new BattleCommand(commands.getCommandWord(word));
    }
    
    public String showAllCommands() {
    	return commands.dspAllCommands();
    }
}
