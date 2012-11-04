import java.util.ArrayList;
import java.util.List;

public class GameSystem {
	private Game game;
	private String gameStatus;
	private List<GameListener> listenerList;
	
	public GameSystem() {
		game = null;
		gameStatus = new String();
		listenerList = new ArrayList<GameListener>();
	}
	
	public synchronized void addGameListener(GameListener g) {
		listenerList.add(g);
	}
	
	public synchronized void removeGameListener(GameListener g) {
		listenerList.remove(g);
	}
	
	public void processCmd(String s) {
		if (gameFinished()) gameStatus = Game.GAME_END;
		else gameStatus = game.playGame(s);
		announceGameStatus(new GameEvent(this));
		if (gameFinished()) announceGameEnded();
	}
	
	protected void announceGameStatus(GameEvent e) {
		for (GameListener g : listenerList) g.commandProcessed(e);
	}
	
	protected void announceGameEnded() {
		for (GameListener g : listenerList) g.endGame();
	}
	
	private boolean gameFinished() {
		return game.isGameOver();
	}
	
	public void newGame() {
		Game newGame = new Game();
		newGame.initializeGame();
		game = newGame;
		gameStatus = game.dspWelcome();
		announceGameStatus(new GameEvent(this));
	}
	
	public boolean gameRunning() {
		if (game != null) return true;
		return false;
	}
	
	public String getGameStatus() {
		return gameStatus;
	}
	
	public String dspGameWelcome() {
		return game.dspWelcome();
	}
}
