import java.awt.event.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class GameController {
	
	protected Game model;
	protected GameView view;
	
	GameController(GameView v) {
		//model = g;
		view = v;
			
		view.addQuitGameListener(new QuitGameListener());
		view.addNewGameListener(new NewGameListener());
	}

	class CommandListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userInput = view.getUserInput();
			String status = "";
			try {
				if (!(userInput.length()>0)) throw new NullPointerException();
				status = model.playGame(userInput);
				view.dspMessage(status);
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
			model.initializeGame();
		}
	}
	
	class QuitGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
		}
	}
}
