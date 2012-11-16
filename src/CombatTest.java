/**
 * Commands I feel should be tested in the combat class (ones that do more than just announce status)
 */
import junit.framework.TestCase;

public class CombatTest extends TestCase {
	Combat combat;
	Player player;
	Monster monster;
	Parser parser;
	
	protected void setUp() {
		player = new Player();
		monster = new Monster("monster");
		parser = new Parser();
		combat = new Combat(player, monster, parser);
	}
	
	public void testFightCommand() {
		combat.fight("fight");
		
		if (player.totalAttack() > 0 && monster.totalAttack()>0) {
			assertTrue("HP of player should be changed once in a fight",
				player.currentHP != player.maxHP);
			assertTrue("HP of monster should be changed once in a fight",
				monster.currentHP != monster.maxHP);
		}
	}
	
	public void testIdleCommand() {
		combat.fight("idle");
		
		if (monster.totalAttack()> 0) {
			assertTrue("HP of player should be changed once in a fight",
					player.currentHP != player.maxHP);
			assertTrue("HP of monster should NOT be changed when player idles",
					monster.currentHP == monster.maxHP);

		}
	}
}
