/**
 * A class that contains the list of battle commands, and regular commands ( and their string representations)
 */

import java.util.HashMap;

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

    // returns the command type from string
    public CommandTypes getCommandWord(String commandWord) {
        CommandTypes command = validCommands.get(commandWord);
        if(command != null) return command;
        else return CommandTypes.UNKNOWN;
    }

    // returnst the battlecommand type from string
    public BattleCommandTypes getBattleCommandWord(String bCommandWord) {
        BattleCommandTypes bCommand = validBattleCommands.get(bCommandWord);
        if(bCommand != null) return bCommand;
        else return BattleCommandTypes.UNKNOWN;
    }
    
    // checks to see if its a regular command
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }
    
    //checks to see if its a battle command
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