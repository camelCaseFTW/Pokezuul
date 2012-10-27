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
    
    /*
     * goRoom allows the user to travel from room to room, but also keeps a log of where he/she has been
     * 'go back' typed in the user input will allow him to return to the previous room (like an UNDO)
     * 
     * Undo portion is written entirely by Eshan
     */
    public void goRoom(String room) {
        // Try to leave current room.
        Room nextRoom = null;
        nextRoom = currentRoom.getExitRoom(room);
        if (nextRoom == null) {
            if(!room.equalsIgnoreCase("back")) // undo command E.K
            {
                System.out.println("There is no door!");
                nextRoom = currentRoom; // for refactoring the Else to get a more efficient Code for adding the Undo Command ( Go back). E.K
            }
        } 
        //else { else has been refactored.
        if(room.equalsIgnoreCase("back"))
        {
            //if(steps.isEmpty()) for some reason the newly created stack shows elementCount = 1, but there is no elements in it. there fore:   E.K
            if(steps.size()<1)
            {
                System.out.println("** You are where you started.");
                nextRoom=currentRoom;
            }
            else
            {
                nextRoom = steps.pop();
            }
        }
        else if(currentRoom != nextRoom)//Keeping track of the steps. E.K
        {
            steps.push(currentRoom);
        }
    
        if (currentRoom.isExitable()) {
            if (nextRoom == null) {
                System.out.println("There is no door!");
            } else {
                if(nextRoom.isEnterable()) currentRoom = nextRoom;
                else System.out.println(Room.unenterable);
                System.out.println();
                currentRoom.look();
            }
        } else {
            System.out.println(Room.unexitable);
        }
    }
    
    /**
     * Checks to see if the pickUp command is in proper format
     * This might be moved to player class soon
     */
    
    public void pickUpItem(Command command) {
        Item removingItem = null;
        
        if(!command.hasSecondWord()) {
            System.out.println("Pickup what?");
            return;
        }
        
        String itemName = command.getSecondWord();
        
        for (Item item : currentRoom.getItemList()) {
            if (item.getName().equals(itemName)) {
                removingItem = item;
            }
        }
        if (!(removingItem==null)) {
            System.out.println(removingItem + " has been picked up");
            currentRoom.removeItem(removingItem);
            insertItem(removingItem);
        } else System.out.println("Item doesn't exist");
    }
    
    public void dspInventory() {
        String allItems = getAllItems();
        System.out.print("Inventory:");
        if (allItems.length()>0) System.out.print(allItems);
        else System.out.print(" -Empty-");
        System.out.println();
    }
    
    /*
     * Player wields and item to become more powerful (but the item will still be in the inventory)
     */
    public void equipItem(Command command) {
        Weapon newWeapon = null;
        String itemName;
        
        if(!command.hasSecondWord()) {
            System.out.println("Equip what?");
            return;
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
            System.out.println(itemName + " has been equipped!");
        } else System.out.println("Weapon cannot be equipped or weapon doesn't exist!");
    }
    
    public void deequipItem() {
        bonusAttack = 0;
        equippedItem = null;
    }

    public void useItem(Command command) {
        Consumable consume = null;
        String itemName;
        
        if(!command.hasSecondWord()) {
            System.out.println("Use what?");
            return;
        }
        
        itemName = command.getSecondWord();
        for (Item item : items) {
            if (item.getName().equals(itemName)) {
                if (item instanceof Consumable) {
                    consume = (Consumable)item;
                }
            }
        }
        if (!(consume==null)) {
            int healthHealed;
            if (consume.getHealthHealed()>(maxHP - currentHP))healthHealed = maxHP-currentHP;
            else healthHealed = consume.getHealthHealed();            
            currentHP += healthHealed;
            
            System.out.println(itemName + " has healed " + healthHealed +"HP!");
            dspHP();
            removeItem(consume);
        } else System.out.println("Consumable cannot be used or consumable doesn't exist!");
    }

    public void examineItem(Command command) {
        Item examine = null;
        String itemName;
        List<Item> tempList = new ArrayList<Item>();
        
        if(!command.hasSecondWord()) {
            System.out.println("Item doesn't exist in your inventory or the room");
            return;
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
            System.out.println(itemName + ": " + examine.getDescription());
        } else System.out.println("Consumable cannot be used or consumable doesn't exist!");
    }    
    
    public void dspStatus() {
        dspHP();
        System.out.println(name + "'s attack power is " + attackPower + "+" + bonusAttack);
    }

    /**
     * See what the room is about (its description), what it has, and what are it's exits
     */
    public void look() {
        String tempItemList = currentRoom.getAllItems();
        
        System.out.println("You are " + currentRoom.getDescription());
        System.out.print("Items:");
        if (tempItemList.length()>0) System.out.print(currentRoom.getAllItems());
        else System.out.print(" -None-");
        System.out.println();
        System.out.print("Exits:");
        System.out.print(currentRoom.getAllExits());
        System.out.println();
    }
    
    public void processCommand(Command command) {
         CommandTypes commandWord = command.getCommandWord();
        //This system must be changed later to seperate the commands sent to game and commands sent to players
        if (commandWord == commandWord.LOOK) {
            look();
        } else if (commandWord == commandWord.PICKUP) {
            pickUpItem(command);
        } else if (commandWord == commandWord.INVENTORY) { 
            dspInventory();
        } else if (commandWord == commandWord.USE) { 
            useItem(command);
        } else if (commandWord == commandWord.STATUS) {
            dspStatus();
        } else if (commandWord == commandWord.EQUIP) {
            equipItem(command);
        } else if (commandWord == commandWord.DEEQUIP) {
            deequipItem();
        } else if (commandWord == commandWord.EXAMINE) {
            examineItem(command);
        }
    }

}
