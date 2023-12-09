import java.lang.*;
import java.util.HashMap;
// import java.awt.Point;

public class Main {
    public static void main(String[] args) {

        // Asking for user's name, Creating hero object, Initializing enemy generator
        System.out.println("------------------------------------------------------------\n");
        System.out.print("                What is your name, traveler?                \n\t\t\t\t\t —> ");
        String name = CheckInput.getString();
        Hero hero = new Hero(name);
        EnemyGenerator enemyGenerator = new EnemyGenerator();

        // Setting program to loop until user quits or dies
        boolean userQuit = false;
        boolean userDeath = false;
        do {
            System.out.println("\n------------------------------------------------------------\n");

            // Prompt user to either quit or move their character up, down, left, right
            int direction = mainMenu(hero);

            // Get char value of room revealed by user's movement (or quit program)
            char roomChar =' ';
            if (direction == 5) {
                userQuit = true;
            } else {
                System.out.println();
                if (direction == 1) {
                    roomChar = hero.goNorth();
                } else if (direction == 2) {
                    roomChar = hero.goSouth();
                } else if (direction == 3) {
                    roomChar = hero.goEast();
                } else if (direction == 4) {
                    roomChar = hero.goWest();
                }
            }

            // Continue program based on type of room uncovered
            String roomString = String.valueOf(roomChar);
            if (!userQuit) {

                // 'x' - Invalid direction of movement
                if (roomString.equals("x")) {
                   System.out.println("You cannot go that way.");

                // 'n' - Nothing special in room
                } else if (roomString.equals("n")) {
                   System.out.println("There's nothing here.");

                // 's' - Run the store() method
                } else if (roomString.equals("s")) {
                   store(hero);

                // 'f' - Level up to next map if user has key
                } else if (roomString.equals("f")) {
                    System.out.println("You find a gate to the next area, but it seems to be locked...");
                    if (hero.hasKey()) {
                        hero.useKey();
                        hero.levelUp();
                        System.out.println("Luckily, you have a key in your inventory!" +
                                            "\nYou use it to open the gate and advance to the next level.");
                    } else {
                        System.out.println("You'll need a key to advance to the next level." +
                                            "\nFind one or buy one from the shop!");
                    }

                // 'i' - Pick up either a health potion or a key (chosen at random)
                } else if (roomString.equals("i")) {
                    int randomItem = (int) ((Math.random() * 2) + 1);
                    if (randomItem == 1) {
                        hero.pickUpPotion();
                        System.out.println("You found a health potion!");
                    } else {
                        hero.pickUpKey();
                        System.out.println("You found a key!");
                    }
                    Map.getInstance().removeCharAtLoc(hero.getLocation());

                // 'm' - Create a new enemy and run the monsterRoom() method
                } else if (roomString.equals("m")) {
                    Enemy enemy = enemyGenerator.generateEnemy(hero.getLevel());
                    if (enemy.getName().charAt(0) == 'O') {
                        System.out.println("You've encountered an " + enemy.getName() + "!\n");
                    } else {
                        System.out.println("You've encountered a " + enemy.getName() + "!\n");
                    }
                    userDeath = !monsterRoom(hero, enemy);
                }
            }
        } while (!userQuit && !userDeath);

        // End with game over screen
        System.out.println("\n------------------------------------------------------------\n");
        System.out.println("                         Game  Over                         ");
        System.out.println("\n------------------------------------------------------------\n");
    }

    /**
     * Prompts user to choose a direction to move their character (or to quit program)
     * @param h hero object that will be moved around the map
     * @return user's choice of direction (up, down, left, right, or quit)
     */
    public static int mainMenu(Hero h) {
        System.out.println(h);
        System.out.println("""
                            Movement Options:
                            1. Go North
                            2. Go South
                            3. Go East
                            4. Go West
                            5. Leave (Quit)
                            """);
        System.out.print("Choose Direction: ");
        return CheckInput.getIntRange(1, 5);
    }

    /**
     * Prompts user to either fight enemy, run away, or drink a potion
     * @param h hero object present in the encounter
     * @param e enemy object present in the encounter
     * @return true if hero lives through encounter, false if otherwise
     */
    public static boolean monsterRoom(Hero h, Enemy e) {

        // Set encounter to loop until user runs, dies, or defeats enemy
        boolean runAway = false;
        boolean userDeath = false;
        boolean enemyDefeated = false;
        do {
            System.out.println(e + "\n");
            System.out.println("""
                                Encounter Options:
                                1. Fight
                                2. Run Away
                                3. Drink Potion
                                """);

            // Prompt user to choose an action
            System.out.println("(You have " + h.getHp() + "hp.)");
            System.out.print("Choose action: ");
            int encounterChoice = CheckInput.getIntRange(1, 3);

            // If user chooses to fight, run fight() method
            if (encounterChoice == 1) {
                userDeath = !fight(h, e);
                if (e.getHp() == 0) {
                    enemyDefeated = true;
                }

            // If user chooses to run, move hero in valid random direction
            } else if (encounterChoice == 2) {
                char runAwayChar = ' ';
                boolean heroMoved = false;
                do {
                    int randomDirection = (int) ((Math.random() * 4) + 1);
                    if ((randomDirection == 1) && (h.getLocation().getX() != 0)) {
                        runAwayChar = h.goNorth();
                        heroMoved = true;
                    } else if ((randomDirection == 2) && (h.getLocation().getX() != 4)) {
                        runAwayChar = h.goSouth();
                        heroMoved = true;
                    } else if ((randomDirection == 3) && (h.getLocation().getY() != 4)) {
                        runAwayChar = h.goEast();
                        heroMoved = true;
                    } else if ((randomDirection == 4) && (h.getLocation().getY() != 0)) {
                        runAwayChar = h.goWest();
                        heroMoved = true;
                    }
                } while (!heroMoved);

                // Inform user if they miss a room encounter while fleeing
                String runAwayString = String.valueOf(runAwayChar);
                System.out.print("\nYou managed to flee the battle!");
                if (runAwayString.equals("s")) {
                    System.out.println("\nThough in your rush you run right past the shop...");
                } else if (runAwayString.equals("f")) {
                    System.out.println("\nThough in your rush you run right past the exit...");
                } else if (runAwayString.equals("i")) {
                    System.out.println("\nThough in your rush you miss something in this room...");
                } else if (runAwayString.equals("m")) {
                    System.out.println("\nAnd in your rush you manage to evade another enemy!");
                } else {
                    System.out.println();
                }
                runAway = true;

            // If user chooses to heal, use one potion and restore hero health
            } else {
                if (h.hasPotion()) {
                    h.usePotion();
                    h.heal();
                    System.out.println("\nYou drank a potion and are now fully healed!\n");
                }
                else {
                    System.out.println("You don't have any potions to heal yourself with!");
                }
            }
        } while (!userDeath && !runAway && !enemyDefeated);

        // If user dies, display defeat message
        if (userDeath) {
            System.out.println("You were defeated by the " + e.getName() + "...");
        }

        // If user defeats enemy, Display victory message and collect gold drop
        if (enemyDefeated) {
            System.out.println("\nYou defeated the " + e.getName() + "!");
            Map.getInstance().removeCharAtLoc(h.getLocation());

            // Pick up random amount of gold in range: 6 - 10
            int randomGoldDrop = (int) ((Math.random() * 5) + 6);
            h.collectGold(randomGoldDrop);
            System.out.println("And found " + randomGoldDrop + " gold on its corpse.");
        }
        return !userDeath;
    }

    /**
     * Run through one round of battle between hero and enemy
     * @param h hero object that will be fighting in battle
     * @param e enemy object that will be fighting in battle
     * @return true if hero survives round, false if otherwise
     */
    public static boolean fight(Hero h, Enemy e) {

        // Prompt user to choose attack type (Physical, Magical, Ranged)
        System.out.println("\nAttack Type Menu:\n" + h.getAttackMenu() + "\n");
        System.out.print("Choose an Attack Type (1-" + h.getNumAttackMenuItems() + "): ");
        int attackType = CheckInput.getIntRange(1, h.getNumAttackMenuItems());

        // Prompt user to choose attack based on type choice
        if (attackType == 1) {
            System.out.println("\nPhysical Attack Menu:\n" + h.getSubAttackMenu(attackType) + "\n");
            System.out.print("Choose a Physical Attack (1-" + h.getNumSubAttackMenuItems(attackType) + "): ");
        } else if (attackType == 2) {
            System.out.println("\nMagical Attack Menu:\n" + h.getSubAttackMenu(attackType) + "\n");
            System.out.print("Choose a MagicalAttack (1-" + h.getNumSubAttackMenuItems(attackType) + "): ");
        } else {
            System.out.println("\nRanged Attack Menu:\n" + h.getSubAttackMenu(attackType) + "\n");
            System.out.print("Choose a Ranged Attack (1-" + h.getNumSubAttackMenuItems(attackType) + "): ");
        }
        int attackChoice = CheckInput.getIntRange(1, h.getNumSubAttackMenuItems(attackType));

        // Hero attacks enemy, Enemy fights back if still alive
        System.out.println("\n" + h.attack(e, attackType, attackChoice));
        if (e.getHp() > 0) {
            System.out.println(e.attack(h) + "\n");
        }
        return h.getHp() > 0;
    }

    /**
     * Allow user to buy potions or keys with collected gold
     * @param h hero object whose gold will be spent
     */
    public static void store(Hero h) {
        System.out.println("Welcome to the shop!");

        // Set shop to loop until user leaves
        boolean itemBought = false;
        boolean exitStore = false;
        do {
            if (itemBought) {
                System.out.println("Anything else?");
            } else {
                System.out.println("What would you like to buy?");
            }
            System.out.println("1. Health Potion - 25g\n2. Key - 50g");
            if (!itemBought) {
                System.out.println("3. Nothing, just browsing... (Leave)\n");
            } else {
                System.out.println("3. No, thank you! (Leave)\n");
            }

            // Prompt user to choose an item or leave
            System.out.println("(You have " + h.getGold() + "g.)");
            System.out.print("Choose item: ");
            int shopChoice = CheckInput.getIntRange(1, 3);

            // If user has enough gold, buy item
            if (shopChoice == 1) {
                if (h.getGold() >= 25) {
                    h.spendGold(25);
                    h.pickUpPotion();
                    itemBought = true;
                    System.out.println("\nYou bought a health potion for 25g.\n");
                } else {
                    System.out.println("\nYou don't have enough gold for a potion!\n");
                }
            } else if (shopChoice == 2) {
                if (h.getGold() >= 50) {
                    h.spendGold(50);
                    h.pickUpKey();
                    itemBought = true;
                    System.out.println("\nYou bought a key for 50g.\n");
                } else {
                    System.out.println("\nYou don't have enough gold for a key!\n");
                }
            } else {
                exitStore = true;
            }
        } while (!exitStore);
    }

    /*
    public static void goldKeyPotTest() {
        Hero hero = new Hero("Our Hero");
        System.out.println(hero);

        System.out.println(hero.getGold());
        hero.collectGold(10);
        System.out.println(hero);

        System.out.println("Spend gold: " + hero.spendGold(20));
        System.out.println(hero);

        System.out.println("Has key: " + hero.hasKey());
        hero.pickUpKey();
        System.out.println(hero);

        System.out.println("Has key: " + hero.hasKey());
        System.out.println("Use key: " + hero.useKey());
        System.out.println(hero);

        System.out.println("Has pot: " + hero.hasPotion());
        hero.pickUpPotion();
        System.out.println(hero);

        System.out.println("Use pot: " + hero.usePotion());
        System.out.println("Use pot: " + hero.usePotion());
        System.out.println("Has pot: " + hero.hasPotion());
        System.out.println(hero);
    }

    public static void attackMenuTest() {
        Hero hero = new Hero("Our Hero");
        Warrior warrior = new Warrior("Warrior", 25);
        Wizard wizard = new Wizard("Wizard", 25);
        Ranger ranger = new Ranger("Ranger", 25);


        System.out.println(hero.getAttackMenu());
        System.out.println(hero.getNumAttackMenuItems());
        System.out.println(hero.getSubAttackMenu(1));
        System.out.println(hero.getNumSubAttackMenuItems(1));
        System.out.println(hero.getSubAttackMenu(2));
        System.out.println(hero.getNumSubAttackMenuItems(2));
        System.out.println(hero.getSubAttackMenu(3));
        System.out.println(hero.getNumSubAttackMenuItems(3));

        System.out.println(hero.attack(warrior, 1, 1));
        System.out.println(hero.attack(warrior, 1, 2));
        System.out.println(hero.attack(wizard, 2, 1));
        System.out.println(hero.attack(wizard, 2, 2));
        System.out.println(hero.attack(ranger, 3, 1));
        System.out.println(hero.attack(ranger, 3, 2));
    }

    public static void movementTest() {
        Hero hero = new Hero("Our Hero");

        System.out.println(hero);
        hero.goSouth();
        System.out.println(hero);
        hero.goSouth();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.levelUp();
        System.out.println(hero);

        hero.goWest();
        System.out.println(hero);
        hero.goNorth();
        System.out.println(hero);
        hero.goNorth();
        System.out.println(hero);
        hero.goNorth();
        System.out.println(hero);
        hero.goNorth();
        System.out.println(hero);
        hero.levelUp();
        System.out.println(hero);

        hero.goSouth();
        System.out.println(hero);
        hero.goSouth();
        System.out.println(hero);
        hero.goWest();
        System.out.println(hero);
        hero.goWest();
        System.out.println(hero);
        hero.goWest();
        System.out.println(hero);
        hero.levelUp();
        System.out.println(hero);

        System.out.println(hero);
        hero.goSouth();
        System.out.println(hero);
        hero.goSouth();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.goEast();
        System.out.println(hero);
        hero.levelUp();
        System.out.println(hero);
    }

    public static void heroAttackTest() {
        Hero hero = new Hero("Our Hero");
        Warrior warrior = new Warrior("Warrior", 25);
        Wizard wizard = new Wizard("Wizard", 25);
        Ranger ranger = new Ranger("Ranger", 25);
        System.out.println(hero.sword(warrior));
        System.out.println(hero.axe(wizard));
        System.out.println(hero.magicMissile(ranger));
        System.out.println(hero.fireball(warrior));
        System.out.println(hero.arrow(wizard));
        System.out.println(hero.fireArrow(ranger));
    }

    public static void generatorTest() {
        EnemyGenerator testGen = new EnemyGenerator();
        testGen.generateEnemy(2);
    }

    public static void enemyTypeTest() {
        Warrior warrior = new Warrior("Warrior", 25);
        Wizard wizard = new Wizard("Wizard", 25);
        Ranger ranger = new Ranger("Ranger", 25);
        Hero hero = new Hero("Our Hero");
        System.out.println(warrior.attack(hero));
        System.out.println(wizard.attack(hero));
        System.out.println(ranger.attack(hero));
    }

    public static void mapTest() {

        // Set Map to public
        Map map = new Map();
        map.loadMap(1);
        Point point = new Point(3,3);
        System.out.println(map.getCharAtLoc(point));
        System.out.println(map.findStart());
        map.reveal(point);
        Point hero = new Point(3, 2);
        System.out.println(map.mapToString(hero));
        map.removeCharAtLoc(point);
        point = new Point(4, 3);
        map.reveal(point);
        System.out.println(map.mapToString(hero));
        System.out.println(map.getCharAtLoc(point));
        map.removeCharAtLoc(point);
        System.out.println(map.mapToString(hero));
    }
     */
}