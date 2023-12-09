public abstract class Entity {
    private String name;
    private int hp;
    private int maxHp;

    /**
     * Constructor that sets entity name and max hp
     * @param n name of entity
     * @param mHp max health of entity
     */
    public Entity(String n, int mHp) {
        name = n;
        hp = mHp;
        maxHp = mHp;
    }

    /**
     * Fetches name of entity
     * @return name of entity
     */
    public String getName() {
        return name;
    }

    /**
     * Fetches current health of entity
     * @return current health of entity
     */
    public int getHp() {
        return hp;
    }

    /**
     * Restores entity's health to full
     */
    public void heal() {
        hp = maxHp;
    }

    /**
     * Drops entity's health by given amount
     * @param d damage done to entity's health
     */
    public void takeDamage(int d) {
        hp -= d;
        if (hp < 0) {
            hp = 0;
        }
    }

    /**
     * Displays entity's name and current health over max health
     * @return String as described above
     */
    public String toString() {
        return name + "\nHP: "+ hp + "/" + maxHp;
    }
}
