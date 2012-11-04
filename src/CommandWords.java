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
    private HashMap<String, BattleCommandTypes> validBattleCommands;
    
    /**
     * Constructor - initialise the command words.
     */
    public CommandWords()
    {
        validCommands = new HashMap<String, CommandTypes>();
        for(CommandTypes command : CommandTypes.values())
            if(command != CommandTypes.UNKNOWN)
                validCommands.put(command.toString(), command);
        
        validBattleCommands = new HashMap<String, BattleCommandTypes>();
        for(BattleCommandTypes bCommand : BattleCommandTypes.values())
            if(bCommand != BattleCommandTypes.UNKNOWN)
                validBattleCommands.put(bCommand.toString(), bCommand);   
    }

    /**
     * Find the CommandType associated with a command word.
     * @param commandWord The word to look up.
     * @return The CommandType correspondng to commandWord, or UNKNOWN
     *         if it is not a valid command word.
     */
    public CommandTypes getCommandWord(String commandWord) {
        CommandTypes command = validCommands.get(commandWord);
        if(command != null) return command;
        else return CommandTypes.UNKNOWN;
    }

    public BattleCommandTypes getBattleCommandWord(String bCommandWord) {
        BattleCommandTypes bCommand = validBattleCommands.get(bCommandWord);
        if(bCommand != null) return bCommand;
        else return BattleCommandTypes.UNKNOWN;
    }
    
    /**
     * checks to see if its a valid command
     */
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }
    
    public boolean isBattleCommand(String aString) {
    	return validBattleCommands.containsKey(aString);
    }
    
    /**
     * Print out all the possible commands
     */
    
    public String dspAllCommands() {
    	String s = "";
    	for(String command : validCommands.keySet()) s+= "  " + command;
    	return s;
    }
    
    public String dspAllBattleCommands() {
    	String s = "";
    	for(String bCommand : validBattleCommands.keySet()) s+= "  " + bCommand;
    	return s;
    }
}