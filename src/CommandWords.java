import java.util.HashMap;
/**
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 * 
 * This class holds an enumeration of all command words known to the game.
 * It is used to recognise commands as they are typed in.
 *
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

/**
 * Pretty much the same as the CommandWords in Zuul-better-version2
 * author: Alok Swamy
 */

public class CommandWords
{
    // A mapping between a command word and the CommandWord
    // associated with it.
    private HashMap<String, CommandTypes> validCommands;

    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandTypes>();
        for(CommandTypes command : CommandTypes.values()) {
            if(command != CommandTypes.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    /**
     * Find the CommandType associated with a command word.
     * @param commandWord The word to look up.
     * @return The CommandType correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandTypes getCommandWord(String commandWord)
    {
        CommandTypes command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        } else {
            return CommandTypes.UNKNOWN;
        }
    }
    
    /**
     * checks to see if its a valid command
     */
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }
    
    /**
     * Print out all the possible commands
     */
    
    public String dspAllCommands() {
    	String s = "";
    	for(String command : validCommands.keySet()) s+= command + "  ";
    	return s;
    }
}