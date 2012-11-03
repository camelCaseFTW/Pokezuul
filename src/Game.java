/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

/**
 * The game class has been modied from the original Zuul
 * 
 * The game processes the commands received from the parsers
 * Currently, the game handles the 'everyday' commands, and the battle commands
 * We are possibly moving all combat related things to another class
 * 
 * Author: Alok Swamy
 */

public class Game {
	
	public static final String GAME_END = "Game Over! 'Game > New Game' to start a new game.";
	public static final String NEW_LINE = "\n";
	
	private boolean gameOver;
    private Parser parser;
    private Player p1;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        parser = new Parser();
        p1 = new Player();
        gameOver = true;
    }

    /**
     * Create all the rooms and link their exits together.
     */    
    public void initializeGame() {
        Room outside, theater, pub, lab, office;
        
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        theater = new Room("in a lecture theater");
        theater.spawnMonster(new Monster("Vampire"));
        theater.getMonster().insertItem(new Weapon("SuperSword", 3));
        pub = new Room("in the campus pub");
        lab = new Room("in a computing lab");
        office = new Room("in the computing admin office");
        
        // initialise room exits

        outside.setExits("east", theater);
        outside.setExits("south", lab);
        outside.setExits("west", pub);
        theater.setExits("west", outside);
        pub.setExits("east", outside);
        lab.setExits("north", outside);
        lab.setExits("east", office);
        office.setExits("west", lab);

        outside.insertItem(new Item("GoldenKey"));
        pub.insertItem(new Weapon("Sword", 2));
        lab.insertItem(new Consumable("SmallPotion", 100));
        
        p1.setRoom(outside); 
        gameOver = false;
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
/*
    public void play() {            
        printWelcome();
        p1.look();
        
        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
            
             //* If the player and a room meet, IT'S GO TIME!
             
            if (p1.getRoom().hasMonster()) {                
                System.out.println("You have encountered a(n) " + p1.getRoom().getMonster().getName());
                Combat combat = new Combat(p1, p1.getRoom().getMonster());
                combat.battle();
                if (p1.isDead()) {
                    System.out.println("\nOh no! You have died.");
                    finished = true;
                }
                if (p1.getRoom().getMonster().isDead()) {
                    p1.getRoom().removeMonster();
                    System.out.println("\nYou have won the battle!\n");
                    p1.look();
                }
            }
        }
        System.out.println("Game Over. Thanks for playing!");
    }
*/
    
    public String playGame(String userInput) {
    	String gameStatus = "";
    	if (!(p1.inBattle())) {
    		Command command = parser.getUserCommand(userInput);
    		gameStatus += processGameCmd(command);
    		if(p1.inBattle()){
    			gameStatus += Combat.FIGHT_INITIATED;
    		}
    	} else {
    		Combat combat = new Combat(p1, p1.getRoom().getMonster());
    		gameStatus += combat.fight(userInput);
    	}
		if (p1.isDead()) {
			gameStatus += "You have died :(\n" + GAME_END;
			gameOver = true;
		}
    	return gameStatus;
    }
    
    /**
     * Print out the opening message for the player.
     */
    public String dspWelcome() {
    	String s = "";
    	s += "\n---------------------------------------\n"
    			+ "Welcome to the World of PokeZuul!"
    			+ "\nPrepare for a kick-butt adventure that will block your socks off!"
    			+ "\nType '" + CommandTypes.HELP + "' if you need help."
    			+ NEW_LINE + NEW_LINE
    			+ p1.playerLook();
    	
    	return s;
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private String processGameCmd(Command command) {
    	CommandTypes commandWord = command.getCommandWord();
    	String s = "";
    	
        if (commandWord == CommandTypes.UNKNOWN) {
            return "I don't know what you mean...";
        } else if (commandWord == CommandTypes.HELP) {
            return dspHelp();
        } else if (commandWord == CommandTypes.QUIT) {
            gameOver = true;
        	return "You have quit the game" + NEW_LINE + GAME_END;
        } else {
            s = p1.processPlayerCmd(command);
        }
        return s;
    }
    
    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    
    public String dspHelp() {
    	String s = "";
    	s += "You are lost. You are alone. You wander around at the university.\n"
    			+ p1.playerLook()
    			+ NEW_LINE
    			+ "Your command words are:\n"
    			+ parser.showAllCommands();
    	return s;
    }
    
    public boolean isGameOver() {
    	return gameOver;
    }
}
