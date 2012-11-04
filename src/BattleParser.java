/**
 * Takes the inputs from the user and constructs a battle command to tell the game
 * (similar to Parser class)
 * 
 * Author: Alok Swamy
 */
import java.util.Scanner;

public class BattleParser {
    private BattleCommandWords commands;  // holds all valid command words
    private Scanner reader;         // source of command input

    /**
     * Create a parser to read from the terminal window.
     */
    public BattleParser() {
        commands = new BattleCommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * @return The next command from the user.
     */
    public BattleCommand getCommand() {
        String inputLine;   // will hold the full input line
        String word1 = null;

        System.out.print("X> ");     // print prompt

        inputLine = reader.nextLine();

        // Find up to two words on the line.
        Scanner tokenizer = new Scanner(inputLine);
        if(tokenizer.hasNext()) {
            word1 = tokenizer.next();      // get first word
        }

        return new BattleCommand(commands.getCommandWord(word1));
    }
    
    public void dspAllCommands() {
        commands.showAll();
    }
}
