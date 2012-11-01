import java.awt.event.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GameController {
	private CommandWords commands;
	
	protected Game model;
	protected GameView view;
	
	GameController(GameView v) {
		//model = g;
		view = v;
		
		commands = new CommandWords();
		
		view.addQuitGameListener(new QuitGameListener());
		view.addNewGameListener(new NewGameListener());
	}
	
    public Command getCommand(String s) {
        String inputLine;   // will hold the full input line
        String word1 = null;
        String word2 = null;

        System.out.print("> ");     // print prompt

        inputLine = s;

        // Find up to two words on the line.
        StringTokenizer tokenizer = new StringTokenizer(s);
        
        if(tokenizer.hasMoreTokens()) {
            word1 = tokenizer.nextToken();      // get first word
            if(tokenizer.hasMoreTokens()) {
                word2 = tokenizer.nextToken();      // get second word
                // note: we just ignore the rest of the input line.
            }
        }

        return new Command(commands.getCommandWord(word1), word2);
    }
	
	class CommandListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userInput = view.getUserInput();
			try {
				if (!(userInput.length()>0)) throw new NullPointerException();
				view.dspMessage("WASSAP!");
				view.resetUserInput();
			} catch(NullPointerException nex) {
				view.showError("You did not enter a command!");
			}
		}
	}
	
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.enableCommandPanel();
			view.addCommandListener(new CommandListener());
		}
	}
	
	class QuitGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
		}
	}
}
