public class GameSystem {
	private Game game;
	
	public GameSystem() {
		game = new Game();
		game.initializeGame();
	}
	
	public String processInput(String s) {
		if (game.isGameOver()) return Game.GAME_END;
		else return game.playGame(s);
	}
	
	public void newGame() {
		Game newGame = new Game();
		newGame.initializeGame();
		game = newGame;
	}
	
	public Game getGame() {
		return game;
	}
}
