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
				if (model.gameFinished()) view.disableCommandPanel();
			} catch(NullPointerException nex) {
				view.showError("You did not enter a command!");
			}
		}
		private String dspUserInput(String input) {
			return "\n** You typed '" + input + "'";
		}
	}
	
	class NewGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (!(model.gameRunning())) {
				view.enableCommandPanel();
				view.addCommandListener(new CommandListener());
			}
			model.newGame();
			view.dspMessage(model.dspGameWelcome());
		}
	}
	
	class QuitGameListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			view.dispose();
		}
	}
}
