public interface Archer {
    String ARCHER_MENU = "1. Arrow\n2. Fire arrow";
    int NUM_ARCHER_MENU_ITEMS = 2;

    /**
     * Defines the basic arrow attack of an archer entity
     * @param e entity to take damage from basic arrow attack
     * @return description of basic arrow ability/attack
     */
    String arrow(Entity e);

    /**
     * Defines the fire arrow attack of an archer entity
     * @param e entity to take damage from fire arrow attack
     * @return description of fire arrow ability/attack
     */
    String fireArrow(Entity e);
}
