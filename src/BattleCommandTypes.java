/**
 * Enumeration class CommandTypes - all possible commands during combat
 * 
 * @author Alok Swamy
 */
public enum BattleCommandTypes {

    FIGHT("fight"), FLEE("flee"), HELP("help"), IDLE("idle"), UNKNOWN("?");
    
    private String commandString;
    
    BattleCommandTypes(String commandString) {
        this.commandString = commandString;
    }
    
    public String toString() {
        return commandString;
    }
}
