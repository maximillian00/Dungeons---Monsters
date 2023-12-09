public interface Magical {
    String MAGIC_MENU = "1. Magic Missile\n2. Fireball";
    int NUM_MAGIC_MENU_ITEMS = 2;

    /**
     * Defines the magic missile ability of a magical entity
     * @param e entity to take damage from magic missile attack
     * @return description of magic missile ability/attack
     */
    String magicMissile(Entity e);

    /**
     * Defines the fireball ability of a magical entity
     * @param e entity to take damage from fireball attack
     * @return description of fireball ability/attack
     */
    String fireball(Entity e);
}
