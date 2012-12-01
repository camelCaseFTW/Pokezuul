
public class Main 
{
	// main method, sets up the game, controller, and view
	public static void main(String[] args) 
	{
			GameSystem g = new GameSystem();
			GameView v= new GameView(g);
			g.addGameListener(v);
			GameController c = new GameController(v, g);
			
			v.setVisible(true);
			v.setLocationRelativeTo(null);
	}
	
	
}
