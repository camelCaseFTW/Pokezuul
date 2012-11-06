import java.awt.event.*;

public class GameController {
	
	protected GameSystem model;
	protected GameView view;
	
	GameController(GameView v, GameSystem g) {
		model = g;
		view = v;
			
		view.addQuitGameListener(new QuitGameListener());
		view.addNewGameListener(new NewGameListener());
		view.addCommandListener(new CommandListener());
		view.addHelpGameListener(new HelpGameListener());
	}

	class CommandListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userInput = view.getUserInput();
			if (userInput.length()>0) {
				view.dspMessage(dspUserInput(userInput));
				model.processCmd(userInput);
				view.resetUserInput();
			} else {
				view.showError("You did not enter a command!");
			}
		}
		private String dspUserInput(String input) {
			return "\n** You typed '" + input + "'";
		}
	}
	
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.enableCommandPanel();
			view.enableGameButtons();
			model.newGame();
		}
	}
	
	class QuitGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
		}
	}
	
	class HelpGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dspMessage("\n** You clicked 'help'");
			model.processCmd("help");
		}
	}
}
