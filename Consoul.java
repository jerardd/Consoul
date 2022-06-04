package netbeangame;

import java.util.Scanner;
import java.util.Random;

abstract class Character{
    String Name;
    int Health;
    int Damage;
    abstract void Taunt();
    public void hurt() {
        System.out.println("\nOuch that hurts!!!");
    }
}
    
class Minotaur extends Character{
    private String charClass = "Tank";

    void Taunt(){
        System.out.println("\nMinotaur: You are no match for this horns!!!");
    }
}
    
class Vampire extends Character{
    private String charClass = "Healer";

    void Taunt(){
        System.out.println("\nVampire: Gonna add your blood to my collection!!!");
    }
}
    
class Werewolf extends Character{
    private String charClass = "Berserker";

    void Taunt(){
        System.out.println("\nWerewolf: Run while you still can!!!");
    }
}

class Enemy extends Character{
    void Taunt(){
        System.out.println("\nOgre: Adventurers suck!");
    }
}

public class NetBeanGame {
    public static void main(String[] args) {
        NetBeanGame Game = new NetBeanGame();
        Character user = null;
        Enemy enemy = new Enemy();
        Random rand = new Random();
        int round = 1, score = 0;
        intro();

        Scanner userInput = new Scanner(System.in);
        int choice = userInput.nextInt();
        int CharacChoice = 0;
        String SpecialSKills[] = {"Block incoming damage when you roll a 3!", "Gain health equal to your damage when you roll a 1!", "Double damage when you roll a 6!"};

        if(choice == 1){
            System.out.println();
            CharacChoice = Game.CharacterSelection();
        }
        switch (CharacChoice) {
            case 1:
                user = new Minotaur();
                user.Name = "Minotaur";
                user.Health = 500;
                user.Damage = 5;
                break;
            case 2:
                user = new Vampire();
                user.Name = "Vampire";
                user.Health = 250;
                user.Damage = 10;
                break;
            case 3:
                user = new Werewolf();
                user.Name = "Werewolf";
                user.Health = 100;
                user.Damage = 20;
                break;
            default:
                break;
        }
        
        System.out.println("Your journey as a " + user.Name + " has begun!");
        while (!(user.Health <= 0)) {
            int healthBound = 20 * round;
            int damageBound = 2 * round;
            int diceBound = 5;
            enemy.Name = "Ogre";
            enemy.Health = rand.nextInt(healthBound);
            enemy.Damage = rand.nextInt(damageBound) + 10;
            int BattleAction = 0, damageDealt = 0, damageReceived = 0, CheckPointAction = 0;
            
            if (round % 5 == 0 && !(user.Health <= 0)) {
                System.out.println("\nBonus Level!");
                System.out.println("\nChoose an action!");
                System.out.print("\n  [1] +100 Health");
                System.out.print("\n  [2] +10 Damage");
                System.out.print("\n  [3] Exit");
                System.out.print("\n\nChoose an action: ");
                CheckPointAction = userInput.nextInt();

                switch (CheckPointAction) {
                    case 1:
                        user.Health += 100;
                        round++;
                        break;
                    case 2:
                        user.Damage += 10;
                        round++;
                        break;
                    default:
                        System.out.println("\nThankyou for playing CONSOUL!");
                        userInput.nextLine();
                        System.exit(0);
                        break;
                }
            }
            else {
                System.out.println("\nLevel " + round + "!");
                System.out.println("\nAn enemy has appeared!");
                enemy.Taunt();
                user.Taunt();

                while ((!(enemy.Health <= 0)) && !(user.Health <= 0)) {
                    System.out.println("\n  Enemy Info:");
                    System.out.println("  *Character Name: " + enemy.Name);
                    System.out.println("  *Health: " + enemy.Health);
                    System.out.println("  *Damage: " + enemy.Damage);
                    System.out.println("\n  Your Current Stats:");
                    System.out.println("  *Character Name: " + user.Name);
                    System.out.println("  *Health: " + user.Health);
                    System.out.println("  *Damage: " + user.Damage);
                    System.out.println("  *Special Skill: " + SpecialSKills[CharacChoice-1]);

                    System.out.println("\nChoose an action!");
                    System.out.print("\n  [1] Roll and Attack");
                    System.out.print("\n  [2] Exit");
                    System.out.print("\n\nChoose an action: ");
                    BattleAction = userInput.nextInt();

                    switch (BattleAction) {
                        case 1:
                            int diceRoll = rand.nextInt(diceBound) + 1;
                            System.out.println("\nYou rolled a " + diceRoll + "!");

                            if (user.Name == "Werewolf" && diceRoll == 6) {
                                damageDealt = diceRoll * user.Damage * 6;
                            }   
                            else { damageDealt = diceRoll * user.Damage; }
                            enemy.Health -= damageDealt;
                            System.out.println("Successfully dealt " + damageDealt + " dmg!");

                            if (user.Name == "Minotaur" && diceRoll == 3) {
                                damageReceived = 0;
                                System.out.println("\nYou blocked the ogre's attack!"); 
                            }
                            else { 
                                damageReceived = enemy.Damage + (rand.nextInt(diceBound) + 1 * enemy.Damage);
                                System.out.println("The Ogre dealt " + damageReceived + " dmg!"); 
                                user.hurt();
                            }
                            user.Health -= damageReceived;

                            if (user.Name == "Vampire" && diceRoll == 1) {
                                user.Health += damageDealt;
                                System.out.println("\nYou gained " + damageDealt + " health!"); 
                            }
                            break;
                        default:
                            System.out.println("\nThankyou for playing CONSOUL!");
                            userInput.nextLine();
                            System.exit(0);
                            break;
                    }
                }
                if (user.Health <= 0) {
                    System.out.println("\nYou have died!");
                    System.out.println("You reached level " + round + "!");
                    System.out.println("You managed to score " + score + " pts!");
                    System.out.println("\nTHANKYOU FOR PLAYING CONSOUL!");
                }
                else {
                    score += 10 * round;
                    round++;
                    System.out.println("\nSuccessfully slayed the ogre!");
                    System.out.println("Moving on to the next round...");
                }
            }
        }
    }
    
    static void intro(){
        System.out.println("Welcome to CONSOUL!");
        System.out.print("\n  [1] Start a Game");
        System.out.print("\n  [2] Exit");
        System.out.print("\n\nChoose an action: ");
    }

    public int CharacterSelection(){
        int tempChoice, isLocked;
        String Names[] = {"Minotaur", "Vampire", "WereWolf"};
        int Healths[] = {500, 250, 100};
        int Damages[] = {50, 100, 300};
        String SpecialSKills[] = {"Block incoming damage when you roll a 3!", "Gain health equal to your damage when you roll a 1 or 2!", "Double damage when you roll a 6!"};

        Scanner userInput = new Scanner(System.in);
        do {
            System.out.println("Choose a character for your journey!");
            System.out.println("\n#This is not yet final, you can go back...");
            System.out.println("[1] Minotaur");
            System.out.println("[2] Vampire");
            System.out.println("[3] Werewolf");
            System.out.print("\nEnter your choice: ");
            tempChoice = userInput.nextInt();
            if(!(tempChoice > 0 && tempChoice < 4)) {
                System.out.println("\nInvalid Input!");
                System.out.println("\nThankyou for playing CONSOUL!");
                userInput.nextLine();
                System.exit(0);
            }

            System.out.println("\nCharacter Information");
            System.out.println("\n  *Character Name: " + Names[tempChoice-1]);
            System.out.println("  *Health: " + Healths[tempChoice-1]);
            System.out.println("  *Damage: " + Damages[tempChoice-1]);
            System.out.println("  *Special SKill: " + SpecialSKills[tempChoice-1]);
            System.out.print("\nDo you want to continue with this character?\n  [1] Yes\n  [2] No");
            System.out.print("\nEnter your choice: ");
            isLocked = userInput.nextInt();
            System.out.println();
        } while (isLocked != 1);

        return tempChoice;
    }
}
