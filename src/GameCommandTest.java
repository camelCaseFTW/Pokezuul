/**
 * A test class full of test cases I believe should be done on the Game commands
 */

import junit.framework.TestCase;

public class GameCommandTest extends TestCase {
	Game game;
	Room startingRoom;
	
	protected void setUp() {
		game = new Game();
		startingRoom = new Room("a room", "room");
		game.getPlayer().setRoom(startingRoom);
	}
	
	public void testGameOver() {
		startingRoom.spawnMonster(new Monster("monster", 1000, 1, 1000, 1000));
		game.playGame("fight");
		assertTrue("When player dies to monster, ends the game", game.isGameOver());
	}
	
	public void testFleeCommand() {
		Room room2 = new Room("a room with a monster", "room2");
		room2.spawnMonster(new Monster("monster", 0, 0, 1000, 1000));
		startingRoom.setExits(Exit.east, room2);
		
		game.playGame("go east");
				
		while(game.getPlayer().inBattle()) {
			game.playGame("flee");
		}
		
		assertTrue("When player flees, it must go back to the previous room", game.getPlayer().getRoom()==startingRoom);
	}
}
