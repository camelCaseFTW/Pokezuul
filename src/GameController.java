import java.awt.event.*;

public class GameController {
	
	protected Game model;
	protected GameView view;
	
	GameController(GameView v, Game g) {
		model = g;
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
				view.dspMessage(dspUserInput(userInput));
				view.dspMessage(status);
				view.resetUserInput();
			} catch(NullPointerException nex) {
				view.showError("You did not enter a command!");
			}
		}
		private String dspUserInput(String input) {
			String s = "\n----------------------\n";
			s += "** You typed '" + input + "'";
			return s;
		}
	}
	
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Game newGame = new Game();
			newGame.initializeGame();
			
			view.enableCommandPanel();
			view.addCommandListener(new CommandListener());
			model = newGame;
		}
	}
	
	class QuitGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
		}
	}
}
