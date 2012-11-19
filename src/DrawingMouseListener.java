import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


public class DrawingMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent eve) {

		int x = 320;
		int y = 320;
		
		GameSystem game = Drawing3DArea.getGameSystem();
		int xClicked = eve.getX();
		int yClicked = eve.getY();
		
		System.out.println("X: "+ xClicked);
		System.out.println("Y: "+ yClicked);
	
		if( xClicked > x/9 && xClicked < x*2/9 && yClicked > y/2 && yClicked < y*7/9 && game.getGame().getPlayer().getRoom().getExitRoom(Exit.west) != null)
		{
			GameView.setCommandInput("go west");
		}
		else if( xClicked > x*7/9 && xClicked < x*8/9 && yClicked > y/2 && yClicked < y*7/9 && game.getGame().getPlayer().getRoom().getExitRoom(Exit.east) != null)
		{
			GameView.setCommandInput("go east");
		}
		else if( xClicked > x*4/9 && xClicked < x*5/9 && yClicked > y/2 && yClicked < y*2/3 && game.getGame().getPlayer().getRoom().getExitRoom(Exit.north) != null)
		{
			GameView.setCommandInput("go north");
		}
		else if( xClicked > x*7/18 && xClicked < x*11/18 && yClicked > y*13/18 && yClicked < y && game.getGame().getPlayer().getRoom().getExitRoom(Exit.south) != null)
		{
			GameView.setCommandInput("go south");
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
