/**
 * A command readable by the Game class constructed by the Parser
 * The parser reads the user inputs and creates an instance of this class
 * 
 * (similar to Command class)
 * 
 * author: Alok Swamy
 */
public class BattleCommand {
    private BattleCommandTypes commandWord;

    /**
     * Create a command object. First and second word must be supplied, but
     * either one (or both) can be null.
     * @param firstWord The first word of the command. Null if the command
     *                  was not recognised.
     * @param secondWord The second word of the command.
     */
    public BattleCommand(BattleCommandTypes firstWord)
    {
        this.commandWord = firstWord;
    }

    /**
     * Return the command word (the first word) of this command. If the
     * command was not understood, the result is null.
     * @return The command word.
     */
    public BattleCommandTypes getCommandWord()
    {
        return commandWord;
    }

    /**
     * @return true if this command was not understood.
     */
    public boolean isUnknown()
    {
        return (commandWord == BattleCommandTypes.UNKNOWN);
    }
}

