public class Powerup extends Item {
    protected int increaseAttack;
    protected int increaseHP;

    public Powerup (String name, String description, int atkIncrease, int hpIncrease) {
    	super(name, description);
    	increaseAttack = atkIncrease;
    	increaseHP = hpIncrease;
    }
    
    public int getAtkIncrease() {
    	return increaseAttack;
    }
    
    public int getHPIncrease() {
    	return increaseHP;
    }
}
