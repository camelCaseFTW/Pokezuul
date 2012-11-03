public class GameSystem {
	private Game game;
	
	public GameSystem() {
		game = null;
	}
	
	public String processInput(String s) {
		if (gameFinished()) return Game.GAME_END;
		else return game.playGame(s);
	}
	
	public boolean gameFinished() {
		return game.isGameOver();
	}
	
	public void newGame() {
		Game newGame = new Game();
		newGame.initializeGame();
		game = newGame;
	}
	
	public boolean gameRunning() {
		if (game != null) return true;
		return false;
	}
	
	public String dspGameWelcome() {
		return game.dspWelcome();
	}
}
