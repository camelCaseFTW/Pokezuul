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

			int x = 320;
			int y = 320;
			
			int xClicked = eve.getX();
			int yClicked = eve.getY();
			
			System.out.println("X: "+ xClicked);
			System.out.println("Y: "+ yClicked);
		
			if( xClicked > x/9 && xClicked < x*2/9 && yClicked > y/2 && yClicked < y*7/9 && model.getGame().getPlayer().getRoom().getExitRoom(Exit.west) != null)
			{
				model.processCmd("go west");
			}
			else if( xClicked > x*7/9 && xClicked < x*8/9 && yClicked > y/2 && yClicked < y*7/9 && model.getGame().getPlayer().getRoom().getExitRoom(Exit.east) != null)
			{
				model.processCmd("go east");
			}
			else if( xClicked > x*4/9 && xClicked < x*5/9 && yClicked > y/2 && yClicked < y*2/3 && model.getGame().getPlayer().getRoom().getExitRoom(Exit.north) != null)
			{
				model.processCmd("go north");
			}
			else if( xClicked > x*7/18 && xClicked < x*11/18 && yClicked > y*13/18 && yClicked < y && model.getGame().getPlayer().getRoom().getExitRoom(Exit.south) != null)
			{
				model.processCmd("go south");
			}
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
