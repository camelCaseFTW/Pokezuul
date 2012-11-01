import java.awt.event.*;

public class GameController {
	private Game model;
	private GameView view;
	
	GameController(GameView v) {
		//model = g;
		view = v;
		
		v.addCommandListener(new CommandListener());
	}
	
	class CommandListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String userInput = view.getUserInput();
			System.out.println("MOO");
			try {
				if (!(userInput.length()>0)) throw new NullPointerException();
				view.dspMessage("WASSAP!");
				view.resetUserInput();
			} catch(NullPointerException nex) {
				view.showError("You did not enter a command!");
			}
		}
	}
}
