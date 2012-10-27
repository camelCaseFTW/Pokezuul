import java.util.Random;

public class Combat {
    private BattleParser bParser;
    private Player player;
    private Monster monster;

    public Combat(Player p1, Monster m1) {
        player = p1;
        monster = m1;
        bParser = new BattleParser();
    }
    
    public void battle() {
        boolean battleFinished = false;
        while (! battleFinished) {
            BattleCommand bCommand = bParser.getCommand();
            battleFinished = processBattleCommand(bCommand);
        }
    }
    
    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the battle, false otherwise.
     */
    private boolean processBattleCommand(BattleCommand command) {
        boolean battleOver = false;
        Random generator = new Random();
        
        BattleCommandTypes commandWord = command.getCommandWord();
        if (commandWord == commandWord.UNKNOWN) {
            System.out.println("Use proper battle commands. Type 'help' if you need assistance.");
            return false;            
        } else if (commandWord == commandWord.FIGHT) {
            //System.out.println("** No stable battle class was completed");
            creatureAttacks(player, monster);
            if(monster.isDead()) return true;
            creatureAttacks(monster, player);
            if(player.isDead()) return true;
        } else if (commandWord == commandWord.IDLE){
            System.out.println("You used Splash. It does nothing");
            creatureAttacks(monster, player);
            if(player.isDead()) return true;
            } else if (commandWord == commandWord.FLEE) {
            int escape = generator.nextInt(3);
            
            if(escape==0) {
                System.out.println("You have fled!");
                player.goRoom("back");
                battleOver = true;
            } else {
                System.out.println("Your flee attempt was unsuccessful!");
                creatureAttacks(monster, player);
                if(player.isDead()) return true;
            }
        } else if (commandWord == commandWord.HELP) {
            printBattleHelp();
        }
        return battleOver;
    }

    private void creatureAttacks(Creature attacker, Creature defender) {
        Random generator = new Random();
        int damageDone = attacker.totalAttack() + generator.nextInt((2*attacker.getDamageRange())+1) - attacker.getDamageRange();
        defender.reduceHP(damageDone);
        System.out.println(attacker.getName() + " has done " + damageDone + " damage!");
        defender.dspHP();    
    }
    
    /**
     * A procedure to help the player decide what to do during a battle 
     */
    private void printBattleHelp() {
        System.out.println("You are currently in a battle.\nYou cannot move past this room until you kill the monster.");
        System.out.println("Your command words are:");
        bParser.dspAllCommands();
    }
}
