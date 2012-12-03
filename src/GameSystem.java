/*
 * A game system makes new games, destroys old ones, tells when the game is over, and whether it is still running (or shutdown)
 * It is equivalent to a gaming console (Game class represents each game you put in the console)
 */
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class GameSystem  implements  java.io.Serializable{
	/**
	 * 
	 */
	private Game game;
	private String gameStatus;
	private List<GameListener> listenerList = new ArrayList<GameListener>();
	public List<GameListener> getListenerList() {
		return listenerList;
	}
	public void setListenerList(List<GameListener> listenerList) {
		this.listenerList = listenerList;
	}

	private static final long serialVersionUID = -6303923165721315457L;

	
	public GameSystem() {
		game = null;
		gameStatus = new String();
		
	}
	public void setGameStatus(String gameStatus) {
		this.gameStatus = gameStatus;
	}
	
	// Add people who want to listen to the game
	public synchronized void addGameListener(GameListener g) {
		listenerList.add(g);
	}
	
	// removes people that don't want to listen to the game
	public synchronized void removeGameListener(GameListener g) {
		listenerList.remove(g);
	}
	
	// process a user input send as a string
	public void processCmd(String s) {
		if (gameFinished()) gameStatus = Game.GAME_END;
		else gameStatus = game.playGame(s);
		announceGameStatus(new GameEvent(this));

		if(gameStatus.contains("You have finished this level :), congradulation!!!"))
		{
			JOptionPane.showMessageDialog (null, "WOOW, You have grabed All the items and killed all the monsters. Press Ok to continue to the next level", "Title", JOptionPane.INFORMATION_MESSAGE);
			Game.nextLevel();
			Game newGame = new Game();
			newGame.initializeGame();
			game = newGame;
			gameStatus = game.dspWelcome();
			announceGameStatus(new GameEvent(this));
		}
		if (gameFinished()) announceGameEnded();
	}
	
	// announce the game status to all that want to listen (GameListeners)
	protected void announceGameStatus(GameEvent e) {
		for (GameListener g : listenerList) g.commandProcessed(e);
	}
	
	// announce the game has ended :(
	protected void announceGameEnded() {
		for (GameListener g : listenerList) g.endGame();
	}
	
	// This is just for checking purposes to see if any of the buttons still interact with the game when this function is true
	private boolean gameFinished() {
		return game.isGameOver();
	}
	
	// Creates a brand new Zuul game and initializes all the rooms, and the player
	public void newGame() {
		Game newGame = new Game();
		newGame.initializeGame();
		game = newGame;
		gameStatus = game.dspWelcome();
		announceGameStatus(new GameEvent(this));
	}
	
	public void openGame(Game g) {
		Game newGame = g;
		//newGame.initializeGame();
		
		game = newGame;
		gameStatus = game.dspWelcome();
		//announceGameStatus(new GameEvent(this));
	}
	
	
	// Determines whether the game console is on, but no game is running
	public boolean gameRunning() {
		if (game != null) return true;
		return false;
	}
	
	public Game getGame()
	{
		return this.game;
	}
	// returns the status of the game
	public String getGameStatus() {
		return gameStatus;
	}
	
	// a String that contains the welcome message from the game
	public String dspGameWelcome() {
		return game.dspWelcome();
	}
	
	public void setGame(Game g)
	{
		this.game = g;
	}
	
	 public void writeObject( ObjectOutputStream aOutputStream) throws IOException {
		 
		  aOutputStream.writeObject(this);
		  aOutputStream.writeObject(this.game);
		  aOutputStream.writeObject(this.game.getPlayer());
	 }
	 
	 public void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
		   //perform the default serialization for all non-transient, non-static fields
		 
		   GameSystem  gs= (GameSystem)ois.readObject();
		   Game  g= (Game)ois.readObject();
		   Player p = (Player)ois.readObject();
		   gs.setGame(g);
		   g.setPlayer(p);
		   gs.gameStatus = new String();		   
		   GameView gv = new GameView(gs);
		   gs.listenerList = new ArrayList<GameListener>();
		   gs.addGameListener(gv);
		   GameController c = new GameController(gv, gs);
		   gv.createGameFrames();
		   
		   gv.createCommandListFrame();
		   gs.addGameListener(gv.getInventoryView());
		   gs.addGameListener(gv.getRoomItemView());
		   gs.addGameListener(gv.getCommandListView());
			
		   gv.setVisible(true);
		   gv.setLocationRelativeTo(null);
		   gv.enableCommandPanel();
		   gv.enableGameButtons();
		  
			
			
	
	 }
	
}
