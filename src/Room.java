import java.util.*;
/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2011.07.31
 */

/**
 * This class possibly has a monster. It can be blocked so others cannot enter, or leave.
 * The room doesn't know about the Player, but the room is where the Player will be throughout the game
 * @author: Alok Swamy
 */

public class Room extends ItemHolder {
    
    public static final String unexitable = "** You cannot leave the room";
    public static final String unenterable = "** You cannot enter the room - It seems to be locked!";
    
    public String description;
    private Map<String, Room> exits;
    private Monster monster;
    private boolean exitable;
    private boolean enterable;
    
    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<String, Room>();
        exitable = true;
        enterable = true;
        monster = null;
    }

    public boolean hasMonster() {
        if(!(monster==null)) return true;
        return false;
    }
    
    public void removeMonster() {
        items.addAll(monster.getItemList());
        monster = null;
    }
    
    /*
     * Puts a monster in the room when the game is being initiated.
     * We could allow the monsters to spawn in the middle of the game later on.
     */
    public void spawnMonster(Monster mon) {
        monster = mon;
        //exitable = false;
    }
    
    public Monster getMonster() {
        return monster;
    }
    
    /**
     * Define the exits of this room.  Every direction either leads
     * to another room
     */
    public void setExits(String roomName, Room roomType) {
        exits.put(roomName, roomType);
    }
    
    public Room getExitRoom(String exitString) {
        return exits.get(exitString);
    }
    
    /**
     * Returns a list of exits as a string
     */
    public String getAllExits() {
        String allExits = new String();
        
        for (String room : exits.keySet()){
        	allExits = allExits + "  " + room;
        }
        
        return allExits;
    }
    
    public Room roomEntered(String room) {
        return this;
    }
    
    public void setExitable(boolean canExit) {
        exitable = canExit;
    }
    
    public boolean isExitable() {
        return exitable;
    }
    
    public void setEnterable(boolean canEnter) {
        enterable = canEnter;
    }
    
    public boolean isEnterable() {
        return enterable;
    }
    
    /**
     * See what the room is about (its description), what it has, and what are it's exits
     */
    public void look() {
        String tempItemList = getAllItems();
        
        System.out.println("You are " + getDescription());
        System.out.print("Items:");
        if (tempItemList.length()>0) System.out.print(getAllItems());
        else System.out.print(" -None-");
        System.out.println();
        System.out.print("Exits:");
        System.out.print(getAllExits());
        System.out.println();
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

}
