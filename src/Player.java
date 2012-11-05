import java.util.*;

/**
 * A person that will handle most of the commands given by the user.
 * This class is far the most interactive, and contains many attributes describing the player including combat stats,
 * player location, and a player log that keeps track of where he/she has been (To Undo)
 * 
 * @author Alok Swamy and Eshan
 */
public class Player extends Creature{
		
    private Room currentRoom;
    private Stack<Room> steps;
    
    protected int bonusAttack; // this attribute tells how much power the equipped item is giving the player
    private Weapon equippedItem;
    
    public Player() {
        name = "Player";
        currentRoom = null;
        steps = new Stack<Room>();
        attackPower = 5;
        bonusAttack = 0;
        damageRange = 2;
        maxHP = 20;
        currentHP = 20;
        equippedItem = null;
    }
    
    public int totalAttack() {
        return attackPower + bonusAttack;
    }
    
    public Room getRoom() {
        return currentRoom;
    }

    public void setRoom(Room room) {
        currentRoom = room;
    }
    
    public Weapon getWeapon() {
    	return equippedItem;
    }
    
    /*
     * goRoom allows the user to travel from room to room, but also keeps a log of where he/she has been
     * 'go back' typed in the user input will allow him to return to the previous room (like an UNDO)
     * 
     * Undo portion is written entirely by Eshan
     */
    
    public String playerMove(Command command) {
        // Try to leave current room.
    	Room nextRoom = null;

        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            return "Go where?";
        }
 
        String room = command.getSecondWord();
        nextRoom = currentRoom.getExitRoom(room);
        
        
        if (nextRoom == null) {
            if(!room.equalsIgnoreCase("back")) // undo command E.K
            {
            	nextRoom = currentRoom;  // for refactoring the Else to get a more efficient Code for adding the Undo Command ( Go back). E.K
                return "There is no door!";
            }
        } 

        //else { else has been refactored.
        if(room.equalsIgnoreCase("back"))
        {
            //if(steps.isEmpty()) for some reason the newly created stack shows elementCount = 1, but there is no elements in it. there fore:   E.K
            if(steps.size()<1)
            {
                nextRoom=currentRoom;
                return "** You are where you started.";
            } else {
                nextRoom = steps.pop();
            }
        }
        else if(currentRoom != nextRoom)//Keeping track of the steps. E.K
        {
            steps.push(currentRoom);
        }
    
        if (currentRoom.isExitable()) {
            if (nextRoom == null) {
                return "There is no door!\n";
            } else {
                if(nextRoom.isEnterable()) currentRoom = nextRoom;
                else return Room.unenterable;
                return playerLook();
            }
        } else {
            return Room.unexitable;
        }
    }   
    
    
    
    /**
     * Checks to see if the pickUp command is in proper format
     * This might be moved to player class soon
     */
    
    public String playerPickup (Command command) {
        Item removingItem = null;
        
        if(!command.hasSecondWord()) {
        	return "Pickup what?";
        }
        
        String itemName = command.getSecondWord();
        
        for (Item item : currentRoom.getItemList())
            if (item.getName().equals(itemName))
                removingItem = item;
            
        if (!(removingItem==null)) {
            currentRoom.removeItem(removingItem);
            insertItem(removingItem);
            return removingItem + " has been picked up";
        } else return "Item doesn't exist";
   }

    public String dspPlayerInventory() {
    	String s = "Inventory:\n";
        s+= getAllItems();	
    	return s;
    }
    
    /*
     * Player wields and item to become more powerful (but the item will still be in the inventory)
     */
    
    public String playerEquip(Command command) {
        Weapon newWeapon = null;
        String itemName;
        
        if(!command.hasSecondWord()) {
            return "Equip what?";
        }
        
        itemName = command.getSecondWord();
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                if (item instanceof Weapon) {
                    newWeapon = (Weapon)item;
                }
            }
        }
        if (!(newWeapon==null)) {
            equippedItem = newWeapon;
            bonusAttack = newWeapon.getWeaponAtk();
            return itemName + " has been equipped!";
        } else return "Item cannot be equipped or weapon doesn't exist!";
   	
    }
    
    public String playerDeequip() {
    	bonusAttack = 0;
        equippedItem = null;
        return "Item has been deequipped.";
    }

    public String playerUse(Command command) {
    	String s = "";
        Consumable consume = null;
        String itemName;
        
        if(!command.hasSecondWord()) {
            return "Use what?";
        }
        
        itemName = command.getSecondWord();
        for (Item item : items)
            if (item.getName().equals(itemName)) 
                if (item instanceof Consumable) 
                    consume = (Consumable)item;            
        
        if (!(consume==null)) {
            int healthHealed;
            if (consume.getHealthHealed()>(maxHP - currentHP))healthHealed = maxHP-currentHP;
            else healthHealed = consume.getHealthHealed();            
            currentHP += healthHealed;
            
            s = itemName + " has healed " + healthHealed +"HP!\n";
            s += creatureHP();
            removeItem(consume);
        } else s= "Item cannot be consumed or consumable doesn't exist!";
    	
    	return s;
    }

    public String playerApply(Command command) {
    	String s = "";
        Powerup powerup = null;
        String itemName;
        
        if(!command.hasSecondWord()) {
            return "Apply what?";
        }
        
        itemName = command.getSecondWord();
        for (Item item : items)
            if (item.getName().equals(itemName)) 
                if (item instanceof Powerup) 
                    powerup = (Powerup)item;            
        
        if (powerup != null) {
            int healthBoosted = powerup.getHPIncrease();
            int attackBoosted = powerup.getAtkIncrease();
            
            attackPower += attackBoosted;
            maxHP += healthBoosted;
            currentHP += healthBoosted;
            
            s = itemName + " has boosted " + attackBoosted + " attack and  " + healthBoosted +"HP!\n";
            s += creatureStatus();
            removeItem(powerup);
        } else s= "Item cannot be applied or Powerup doesn't exist!";
    	
    	return s;
    }
        
    public String playerExamine(Command command) {
        Item examine = null;
        String itemName;
        List<Item> tempList = new ArrayList<Item>();
        
        if(!command.hasSecondWord()) {
            return "Item doesn't exist in your inventory or the room";
        }
        
        itemName = command.getSecondWord();
        
        tempList.addAll(items);
        tempList.addAll(currentRoom.getItemList());
        
        for (Item item : tempList) {
            if (item.getName().equals(itemName)) {
                examine = item;
            }
        }
        if (!(examine==null)) {
            return itemName + ": " + examine.getDescription();
        } else return "Consumable cannot be used or consumable doesn't exist!";
    }
    
    /**
     * See what the room is about (its description), what it has, and what are it's exits
     */
    
    public String playerLook() {
    	String s = "";
    	
    	s += "You are " + currentRoom.getDescription() + "\n"
    			+ "Items:\n";
        s+= currentRoom.getAllItems() + "\n";
        s+= "Exits:\n" +
        		currentRoom.getAllExits();
    	return s;
    }

    public boolean inBattle() {
    	return currentRoom.hasMonster();
    }
    
    public String processPlayerCmd(Command command) {
    	String temp = "";
    	CommandTypes commandWord = command.getCommandWord();
    	
        if (commandWord == CommandTypes.LOOK) {
            temp = playerLook();
        } else if (commandWord == CommandTypes.PICKUP) {
            temp = playerPickup(command);
        } else if (commandWord == CommandTypes.INVENTORY) { 
            temp = dspPlayerInventory();
        } else if (commandWord == CommandTypes.CONSUME) { 
            temp = playerUse(command);
        } else if (commandWord == CommandTypes.APPLY) { 
            temp = playerApply(command);            
        } else if (commandWord == CommandTypes.STATUS) {
            temp = creatureStatus();
        } else if (commandWord == CommandTypes.EQUIP) {
            temp = playerEquip(command);
        } else if (commandWord == CommandTypes.DEEQUIP) {
            temp = playerDeequip();
        } else if (commandWord == CommandTypes.EXAMINE) {
            temp = playerExamine(command);
        } else if (commandWord == CommandTypes.GO) {
        	temp = playerMove(command);
        }
    	return temp;
    }
    
    public String creatureStatus() {
    	return creatureHP() + "\n" + name + "'s attack power is " + attackPower + "+" + bonusAttack;
    }
    
}
