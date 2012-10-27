/**
 * The monster class is the Player's most annoying obsticle that dwells
 * within a room. We are still debating if it should move from room to room
 * or spawn magically into a room
 * 
 * The monster has similarities between player, but cannot be controlled
 * 
 * @author: Alok Swamy
 */

public class Monster extends Creature{
    
    public Monster(String name) {
        this.name = name;
        attackPower = 10;
        damageRange = 1; // the mosnter does a damage of 3 +/- 1
        maxHP = 8;
        currentHP = 8;
    }
}
