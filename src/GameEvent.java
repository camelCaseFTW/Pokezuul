import java.util.EventObject;

public class GameEvent extends EventObject {	
	private static final long serialVersionUID = 1L;

	private String gameStatus;
	
	public GameEvent(Object source) {
		super(source);
		gameStatus = ((GameSystem)source).getGameStatus();
	}
	
	public String getGameStatus() {
		return gameStatus;
	}
}
