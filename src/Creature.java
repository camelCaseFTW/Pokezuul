public abstract class Creature extends ItemHolder {
    /*
    public int totalAttack();
    public boolean isDead();
    public void dspStatus();
    */
   
    protected String name;
    protected int attackPower;
    protected int damageRange;
    protected int maxHP;
    protected int currentHP;

    public String getName() {
        return name;
    }
    
    public int totalAttack() {
        return attackPower;
    }
    
    public boolean isDead() {
        if (currentHP <= 0) return true;
        return false;
    }
    
    public int getDamageRange() {
        return damageRange;
    }
    
    public void reduceHP(int damage) {
        if (damage < currentHP) currentHP = currentHP - damage;
        else currentHP = 0;
    }
    
    public void dspHP() {
        System.out.println(name + "'s current HP is " + currentHP + "/" + maxHP);
    }
    
    public void dspStatus() {
        dspHP();
        System.out.println(name + "'s attack power is " + attackPower);
    }
}
