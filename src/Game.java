import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

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
 * 
 * Author: Alok Swamy
 */

public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1748238845495656824L;
	public static final String GAME_END = "Game Over! 'Game > New Game' to start a new game.";
	public static final String NEW_LINE = "\n";
	public static int level = 1;
	
	private boolean gameOver;
    private Parser parser;
    private Player p1;
	final ArrayList<Room> rooms = new ArrayList<Room>();
	private Boolean isCorrentLevel = false;
    
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
     * @throws SAXException 
     * @throws ParserConfigurationException 
     * @throws IOException 
     */    
    public void initializeGame(){
        
    	File f = new File("Levels.xml");
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser s= null;
		try {
			s = spf.newSAXParser();
		} catch (ParserConfigurationException | SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DefaultHandler dh = new DefaultHandler(){
			
		Room room;
		Weapon weapon;
		String element="";
		Consumable consumable;
		Powerup powerup;
		Monster monster;
		
		public void startElement(String u, String ln, String qName, Attributes a)
		{
			if( qName.equalsIgnoreCase("level") && level == Integer.parseInt(a.getValue(0)))
				isCorrentLevel = true;
					
			if(isCorrentLevel)
			{
				if( qName.equalsIgnoreCase("room"))
					room = new Room(a.getValue(1), a.getValue(0));
			
				if( qName.equalsIgnoreCase("weapon"))
				{
					weapon = new Weapon(a.getValue(0), 0);
					element = "weapon";
				}
				if( qName.equalsIgnoreCase("consumable"))
				{
					consumable = new Consumable(a.getValue(0), 0);
					element = "consumable";
				}
				if( qName.equalsIgnoreCase("powerup"))
				{
					powerup = new Powerup(a.getValue(0), a.getValue(1), 0, 0);
					element = "powerup";
				}
				if( qName.equalsIgnoreCase("attack"))
					element = "attack";
				if( qName.equalsIgnoreCase("health"))
					element = "health";
				if( qName.equalsIgnoreCase("monster"))
				{
					if(! a.getValue(0).trim().equalsIgnoreCase(""))
					{
						monster = new Monster(a.getValue(0));
						element = "monster";
						room.spawnMonster(monster);
					}
				}
				if( qName.equalsIgnoreCase("weapon") && element.equalsIgnoreCase("monster"))
				{
					weapon = new Weapon(a.getValue(0), 0);
					element="monster weapon";
				}
				
				if( qName.equalsIgnoreCase("location"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(a.getValue(0)))
							room = r;

				}
				if( qName.equalsIgnoreCase("exit"))
				{
					if(a.getValue(0).equalsIgnoreCase("east"))
						element = "exit.east";
					else if(a.getValue(0).equalsIgnoreCase("west"))
						element = "exit.west";
					else if(a.getValue(0).equalsIgnoreCase("north"))
						element = "exit.north";
					else if(a.getValue(0).equalsIgnoreCase("teleporter"))
						element = "exit.teleporter";
					else
						element = "exit.south";				
				}	
			}
		}
		public void endElement(String uri, String localName, String qName){
		
		if(qName.equalsIgnoreCase("room") && isCorrentLevel )
			rooms.add(room);
		else if(qName.equalsIgnoreCase("level"))
			isCorrentLevel = false;
		}
		public void characters(char[] ch,int start, int length)
		{
			if(! new String(ch, start, length).trim().equalsIgnoreCase("") && isCorrentLevel)
			{
				if(element.equalsIgnoreCase("weapon"))
				{
					weapon.setWeaponAtk(Integer.parseInt(new String(ch, start, length)));
					room.insertItem(weapon);
				}
				if(element.equalsIgnoreCase("consumable"))
				{
					consumable.setRegenHP(Integer.parseInt(new String(ch, start, length)));
					room.insertItem(consumable);	
				}
				if(element.equalsIgnoreCase("attack"))
				{
					powerup.setIncreaseAttack(Integer.parseInt(new String(ch, start, length)));
				}
				if(element.equalsIgnoreCase("consumable"))
				{
					powerup.setIncreaseHP(Integer.parseInt(new String(ch, start, length)));
					room.insertItem(powerup);	
				}
				if(element.equalsIgnoreCase("monster weapon"))
				{
					weapon.setWeaponAtk(Integer.parseInt(new String(ch, start, length)));
					room.getMonster().insertItem(weapon);
				}
				
				////EXITS
				if(element.equalsIgnoreCase("exit.east"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(new String(ch, start, length)))
							room.setExits(Exit.east, r);
				}
				if(element.equalsIgnoreCase("exit.west"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(new String(ch, start, length)))
							room.setExits(Exit.west, r);
				}				
				if(element.equalsIgnoreCase("exit.north"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(new String(ch, start, length)))
							room.setExits(Exit.north, r);
				}				
				if(element.equalsIgnoreCase("exit.south"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(new String(ch, start, length)))
							room.setExits(Exit.south, r);
				}
				if(element.equalsIgnoreCase("exit.teleporter"))
				{
					for(Room r: rooms)
						if(r.getRoomName().equalsIgnoreCase(new String(ch, start, length)))
							room.setExits(Exit.teleporter, r);
				}
				
			}
				
		}
		};
		try {
			s.parse(f, dh);
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   	
    	
		for(Room r: rooms)
			if(r.getRoomName().equalsIgnoreCase("outside"))
				p1.setRoom(r);

        gameOver = false;
    }

    /**
     *  processes user inputs and converts it into a command that the game can read (battle command or regular command)
     */    
    public String playGame(String userInput) {
    	String gameStatus = "";
    	if (!(p1.inBattle())) {
    		Command command = parser.getUserCommand(userInput);
    		gameStatus += processGameCmd(command);
    		if(p1.inBattle()){
    			gameStatus += "\nYou have encountered a " + p1.getRoom().getMonster().getName();
    		}
    	} else {
    		Combat combat = new Combat(p1, p1.getRoom().getMonster(), parser);
    		gameStatus += combat.fight(userInput);
    	}
		if (p1.isDead()) {
			gameStatus += "You have died :(\n" + GAME_END;
			gameOver = true;
		}
		Boolean flag = true;
		for(Room room: rooms)
		{
			if(((! room.getAllItems().trim().equalsIgnoreCase("-Empty-") ) && ! room.getAllItems().trim().equalsIgnoreCase(""))
				|| room.getMonster() != null)
			{
				flag = false;
				break;
			}
		}
		if(flag== true)
		{
			gameStatus += "You have finished this level :), congradulation!!! \n";
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
    
    /*
     * returns whether a game is over
     */
    public boolean isGameOver() {
    	return gameOver;
    }
    
    /*
     * returns the player
     */
    public Player getPlayer()
    {
    	return p1;
    }
 
    /*
     * returns the parser
     */
    public Parser getParser() {
    	return parser;
    }

	public void setPlayer(Player player) {
		this.p1 = player;
		
	}
    
	public static void nextLevel()
	{
		level++;
	}
  
	 
}
