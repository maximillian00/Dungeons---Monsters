public interface Fighter {
    String FIGHTER_MENU = "1. Sword\n2. Axe";
    int NUM_FIGHTER_MENU_ITEMS = 2;

    /**
     * Defines the sword ability of a fighter entity
     * @param e entity to take damage from sword attack
     * @return description of sword ability/attack
     */
    String sword(Entity e);

    /**
     * Defines the axe ability of a fighter entity
     * @param e entity to take damage from axe attack
     * @return description of axe ability/attack
     */
    String axe(Entity e);
}
