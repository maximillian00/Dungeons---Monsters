import java.awt.Point;

public class Hero extends Entity implements Fighter, Magical, Archer {
    private Point loc;
    private int level;
    private int gold;
    private int keys;
    private int potions;

    /**
     * Constructor that sets hero name and sets all other values to default
     * @param n name of hero
     */
    public Hero(String n) {
        super(n, 25);       // Default maxHp: 25
        level = 1;          // Default level:   1
        gold = 25;          // Default gold:   25
        keys = 0;           // Default keys:    0
        potions = 1;        // Default potions: 0

        // Default location: starting point of first map
        Map.getInstance().loadMap(1);
        loc = Map.getInstance().findStart();
        Map.getInstance().reveal(loc);
    }

    /**
     * Displays hero's health, gold, potions, and keys, and displays the current map
     * @return String as described above
     */
    @Override
    public String toString() {
        return (super.toString() +
                "\tLevel: " + level +
                "\nPotions: " + potions +
                "\tKeys: " + keys +
                "\nGold: " + gold +
                "\n\n" + Map.getInstance().mapToString(loc));
    }

    /**
     * Fetches current location of hero (as a point)
     * @return current location of hero (as a point)
     */
    public Point getLocation() {
        return loc;
    }

    /**
     * Increment hero's level and load in the next map (loop from Map3 back to Map1)
     */
    public void levelUp() {
        level++;
        int mapToLoad = level % 3;
        Map.getInstance().loadMap(mapToLoad);
        Map.getInstance().reveal(loc);
    }

    /**
     * Fetches current level of hero
     * @return current level of hero
     */
    public int getLevel() {
        return level;
    }

    /**
     * Move hero up one space (if in bounds of the 5x5 map), reveal that location, and grab the char at that point
     * @return the character at the point that the hero has moved to
     */
    public char goNorth() {
        if (loc.getX() == 0) {
            return 'x';
        } else {
            loc = new Point((int) loc.getX() - 1, (int) loc.getY());
            Map.getInstance().reveal(loc);
            return Map.getInstance().getCharAtLoc(loc);
        }
    }

    /**
     * Move hero down one space (if in bounds of the 5x5 map), reveal that location, and grab the char at that point
     * @return the character at the point that the hero has moved to
     */
    public char goSouth() {
        if (loc.getX() == 4) {
            return 'x';
        } else {
            loc = new Point((int) loc.getX() + 1, (int) loc.getY());
            Map.getInstance().reveal(loc);
            return Map.getInstance().getCharAtLoc(loc);
        }
    }

    /**
     * Move hero right one space (if in bounds of the 5x5 map), reveal that location, and grab the char at that point
     * @return the character at the point that the hero has moved to
     */
    public char goEast() {
        if (loc.getY() == 4) {
            return 'x';
        } else {
            loc = new Point((int) loc.getX(), (int) loc.getY() + 1);
            Map.getInstance().reveal(loc);
            return Map.getInstance().getCharAtLoc(loc);
        }
    }

    /**
     * Move hero left one space (if in bounds of the 5x5 map), reveal that location, and grab the char at that point
     * @return the character at the point that the hero has moved to
     */
    public char goWest() {
        if (loc.getY() == 0) {
            return 'x';
        } else {
            loc = new Point((int) loc.getX(), (int) loc.getY() - 1);
            Map.getInstance().reveal(loc);
            return Map.getInstance().getCharAtLoc(loc);
        }
    }

    /**
     * Return a menu of the different types of abilities the hero can use
     * @return string as described above
     */
    public String getAttackMenu() {
        return ("""
                 1. Physical
                 2. Magical
                 3. Ranged""");
    }

    /**
     * Return the number of items in the main attack menu
     * @return integer amount of items in attack menu (3)
     */
    public int getNumAttackMenuItems() {
        return 3;
    }

    /**
     * Return the submenu of the attacks of a certain type that may be used
     * @param choice integer corresponding to main attack menu options (1-3)
     * @return String as described above
     */
    public String getSubAttackMenu(int choice) {
        if (choice == 1) {
            return FIGHTER_MENU;
        } else if (choice == 2) {
            return MAGIC_MENU;
        } else {
            return ARCHER_MENU;
        }
    }

    /**
     * Return the number of items in the specified sub-attack menu
     * @param choice integer corresponding to main attack menu options (1-3)
     * @return integer amount of items in sub-attack menu
     */
    public int getNumSubAttackMenuItems(int choice) {
        if (choice == 1) {
            return NUM_FIGHTER_MENU_ITEMS;
        } else if (choice == 2) {
            return NUM_MAGIC_MENU_ITEMS;
        } else {
            return NUM_ARCHER_MENU_ITEMS;
        }
    }

    /**
     * Attack an enemy with the specified attack method
     * @param e enemy entity that will receive the damage
     * @param choice user choice between physical, magical, or ranged attack
     * @param subChoice user choice between the two options per attack type
     * @return string description of the attack method used
     */
    public String attack(Enemy e, int choice, int subChoice) {
        if (choice == 1) {
            if (subChoice == 1) {
                return sword(e);
            } else {
                return axe(e);
            }
        } else if (choice == 2) {
            if (subChoice == 1) {
                return magicMissile(e);
            } else {
                return fireball(e);
            }
        } else {
            if (subChoice == 1) {
                return arrow(e);
            } else {
                return fireArrow(e);
            }
        }
    }

    /**
     * Deal sword damage to entity within range: 4 - 6
     * @param e entity to take damage from sword attack
     * @return description of sword attack on entity
     */
    @Override
    public String sword(Entity e) {
        int swordDamage = (int) ((Math.random() * 3) + 4);
        e.takeDamage(swordDamage);
        return getName() + " slashes the " + e.getName() + " with a sword for " + swordDamage + " damage.";
    }

    /**
     * Deal axe damage to entity within range: 1 - 8
     * @param e entity to take damage from axe attack
     * @return description of axe attack on entity
     */
    @Override
    public String axe(Entity e) {
        int axeDamage = (int) ((Math.random() * 8) + 1);
        e.takeDamage(axeDamage);
        return getName() + " swings at the " + e.getName() + " with an axe for " + axeDamage + " damage.";
    }

    /**
     * Deal magic missile damage to entity within range: 3 - 5
     * @param e entity to take damage from magic missile attack
     * @return description of magic missile attack on entity
     */
    @Override
    public String magicMissile(Entity e) {
        int missileDamage = (int) ((Math.random() * 3) + 3);
        e.takeDamage(missileDamage);
        return getName() + " fires a magic missile at the " + e.getName() + " for " + missileDamage + " damage.";
    }

    /**
     * Deal fireball damage to entity within range: 1 - 7
     * @param e entity to take damage from fireball attack
     * @return description of fireball attack on entity
     */
    @Override
    public String fireball(Entity e) {
        int fireballDamage = (int) ((Math.random() * 7) + 1);
        e.takeDamage(fireballDamage);
        return getName() + " shoots a fireball at the " + e.getName() + " for " + fireballDamage + " damage.";
    }

    /**
     * Deal basic arrow damage to entity within range: 2 - 4
     * @param e entity to take damage from basic arrow attack
     * @return description of basic arrow attack on entity
     */
    @Override
    public String arrow(Entity e) {
        int arrowDamage = (int) ((Math.random() * 3) + 2);
        e.takeDamage(arrowDamage);
        return getName() + " fires an arrow at the " + e.getName() + " for " + arrowDamage + " damage.";
    }

    /**
     * Deal fire arrow damage to entity within range: 1 - 6
     * @param e entity to take damage from fire arrow attack
     * @return description of fire arrow attack on entity
     */
    @Override
    public String fireArrow(Entity e) {
        int fireArrowDamage = (int) ((Math.random() * 6) + 1);
        e.takeDamage(fireArrowDamage);
        return getName() + " shoots a flaming arrow at the " + e.getName() + " for " + fireArrowDamage + " damage.";
    }

    /**
     * Fetches current amount of gold in hero's inventory
     * @return current amount of gold in hero's inventory
     */
    public int getGold() {
        return gold;
    }

    /**
     * Add the found gold to the hero's inventory
     * @param g amount of gold to be collected
     */
    public void collectGold(int g) {
        gold += g;
    }

    /**
     * If hero has enough gold, subtract given amount from inventory and return true
     * @param g amount of gold to remove from hero's inventory
     * @return if gold is successfully spent, return true; if hero doesn't have enough, return false
     */
    public boolean spendGold(int g) {
        if (gold >= g) {
            gold -= g;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the hero has a key in their inventory
     * @return true or false depending on above statement
     */
    public boolean hasKey() {
        return keys > 0;
    }

    /**
     * Adds a key to the hero's inventory
     */
    public void pickUpKey() {
        keys += 1;
    }

    /**
     * If hero has a key in inventory, remove one and return true
     * @return if hero successfully uses a key, return true; if hero doesn't have one, return false
     */
    public boolean useKey() {
        if (keys >= 1) {
            keys -= 1;
            return true;
        }
        return false;
    }

    /**
     * Checks whether the hero has a potion in their inventory
     * @return true or false depending on above statement
     */
    public boolean hasPotion() {
        return potions > 0;
    }

    /**
     * Adds a potion to the hero's inventory
     */
    public void pickUpPotion() {
        potions += 1;
    }

    /**
     * If hero has a potion in inventory, remove one, heal to full health, and return true
     * @return if hero successfully uses a potion, return true; if hero doesn't have one, return false
     */
    public boolean usePotion() {
        if (potions >= 1) {
            potions -= 1;
            heal();
            return true;
        }
        return false;
    }
}
