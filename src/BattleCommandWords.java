import java.util.HashMap;

/**
 * Takes the enumerator of all the possible commands you can have during a battle,
 * displays them, and verifies that it is a proper command
 * 
 * Author: Alok Swamy 
 */

public class BattleCommandWords {
    // A mapping between a command word and the CommandWord
    // associated with it.
    private HashMap<String, BattleCommandTypes> validCommands;

    public BattleCommandWords() {
        validCommands = new HashMap<String, BattleCommandTypes>();
        for(BattleCommandTypes command : BattleCommandTypes.values()) {
            if(command != BattleCommandTypes.UNKNOWN) {
                validCommands.put(command.toString(), command);
            }
        }
    }

    public BattleCommandTypes getCommandWord(String commandWord) {
        BattleCommandTypes command = validCommands.get(commandWord);
        if(command != null) {
            return command;
        } else {
            return BattleCommandTypes.UNKNOWN;
        }
    }
    
    public boolean isCommand(String aString) {
        return validCommands.containsKey(aString);
    }

    public String dspAllCommands() {
    	String s = "";
    	for(String command : validCommands.keySet()) s+= command + "  ";
    	return s;
    }
}