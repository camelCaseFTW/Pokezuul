import java.io.Serializable;

/**
 * Creature a superclass for the monster and player and whoever that will be "living" in the world of Zuul
 *
 */
public abstract class Creature extends ItemHolder implements Serializable{
   
    protected String name;
    protected int attackPower;
    protected int damageRange;
    protected int maxHP;
    protected int currentHP;

    
    // returns the name of the creature
    public String getName() {
        return name;
    }
    
    
    // returns the atk power of the creature
    public int totalAttack() {
        return attackPower;
    }
    
    
    //returns weather the creature is dead
    public boolean isDead() {
        if (currentHP <= 0) return true;
        return false;
    }
    
    
    // retuns the damage range of the attacker
    public int getDamageRange() {
        return damageRange;
    }
    
    // reduces the creatures HP
    public void reduceHP(int damage) {
        if (damage < currentHP) currentHP = currentHP - damage;
        else currentHP = 0;
    }
    
    // returns a string representation of the creatures HP
    public String creatureHP() {
    	return name + "'s current HP is " + currentHP + "/" + maxHP;
    }
    
    // returns a string representation of the creatures status
    public abstract String creatureStatus();
}
