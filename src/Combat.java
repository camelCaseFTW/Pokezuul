import java.util.Random;

public class Combat {
	
	public static final String FIGHT_WON = "\nYou have won the battle! You continue...";
	public static final String NEW_LINE = "\n";	
	private Parser parser;
    private Player player;
    private Monster monster;

    public Combat(Player p1, Monster m1, Parser p) {
        player = p1;
        monster = m1;
        parser = p;
    }
    
    public String fight(String userInput) {
    	String fightStatus = "";
        BattleCommand bCommand = parser.getUserBattleCommand(userInput);
        fightStatus += processBattleCmd(bCommand);
        if (monster.isDead()) {
        	fightStatus += "Items dropped by monster:\n" + monster.getAllItems();
        	player.getRoom().removeMonster();
        	fightStatus += FIGHT_WON;
        }
    	return fightStatus;
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the battle, false otherwise.
     */
    
    private String processBattleCmd(BattleCommand command) {
    	BattleCommandTypes commandWord = command.getCommandWord();
    	String s = "";
   
        if (commandWord == BattleCommandTypes.UNKNOWN) {
            s += "Use proper battle commands. Type 'help' if you need assistance.";
        } else if (commandWord == BattleCommandTypes.FIGHT) {
            s+= creaturesBattle(true, true);
        } else if (commandWord == BattleCommandTypes.IDLE){
            s+= "You used Splash. It does nothing.\n";
            s+= creaturesBattle(false, true);
        } else if (commandWord == BattleCommandTypes.FLEE) {
            s+= playerFlees();
        } else if (commandWord == BattleCommandTypes.HELP) {
            s+= dspBattleHelp();
        }
    	return s;
    }
    
    private String playerFlees() {
    	String playerFleeStatus = "";
        Random generator = new Random();
    	int escape = generator.nextInt(3);
        
        if(escape==0) {
            playerFleeStatus += "You have fled!";
            playerFleeStatus += player.playerMove(new Command(CommandTypes.GO, "back"));
        } else {
            playerFleeStatus += "Your flee attempt was unsuccessful!";
            creaturesBattle(false, true);
        }    	
    	return playerFleeStatus; 
    }
    
    private String creatureAttacks(Creature attacker, Creature defender) {
        String displayFightStatus = "";
    	Random generator = new Random();
        int damageDone = attacker.totalAttack() + generator.nextInt((2*attacker.getDamageRange())+1) - attacker.getDamageRange();
        defender.reduceHP(damageDone);
        displayFightStatus += attacker.getName() + " has done " + damageDone + " damage!\n";
        displayFightStatus += defender.creatureHP() + NEW_LINE;
        
        return displayFightStatus;
    }
    
    private String creaturesBattle(boolean playerFights, boolean monsterFights) {
    	String displayFightTurn = "";
        if (playerFights) {
        	displayFightTurn += creatureAttacks(player, monster);
        	if(monster.isDead()) return displayFightTurn;
        }
        if (monsterFights)
        	displayFightTurn += creatureAttacks(monster, player);
        return displayFightTurn;
    }
    
    /**
     * A procedure to help the player decide what to do during a battle 
     */
    
    private String dspBattleHelp() {
    	String s = "You are currently in a battle.\nYou cannot move past this room until you kill the monster.\n";
    	s+= "Your command words are\n:" + parser.showAllBattleCommands();
    	return s;
    }
}
