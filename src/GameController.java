import java.awt.Point;
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
		view.addDrawingMouseListener(new DrawingMouseListener());
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
	
	class DrawingMouseListener implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent eve) {
			String exitClicked = view.get3DPanel().pointInExit(new Point(eve.getX(), eve.getY()));
			if (exitClicked != null) model.processCmd("go " + exitClicked);
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}
}
