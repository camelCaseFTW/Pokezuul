import java.awt.event.*;

public class GameController {
	
	protected GameSystem model;
	protected GameView view;
	
	GameController(GameView v, GameSystem g) {
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
				status = model.processInput(userInput);
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
			model.newGame();
			
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
