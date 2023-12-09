public class Warrior extends Enemy implements Fighter {

    /**
     * Constructor that sets warrior name and max hp
     * @param n name of warrior
     * @param mHp max health of warrior
     */
    public Warrior(String n, int mHp) {
        super(n, mHp);
    }

    /**
     * Randomly choose to attack hero with either sword or axe
     * @param h hero object which enemy will attack
     * @return description of attack method chosen at random
     */
    @Override
    public String attack(Hero h) {
        int randomAttack = (int) ((Math.random() * 2) + 1);
        if (randomAttack == 1) {
            return sword(h);
        } else {
            return axe(h);
        }
    }

    /**
     * Deal sword damage to entity within range: 3 - 5
     * @param e entity to take damage from sword attack
     * @return description of sword attack on entity
     */
    @Override
    public String sword(Entity e) {
        int swordDamage = (int) ((Math.random() * 3) + 3);
        e.takeDamage(swordDamage);
        return "The " + getName() + " slashes " + e.getName() + " with a sword for " + swordDamage + " damage.";
    }

    /**
     * Deal axe damage to entity within range: 6 - 7
     * @param e entity to take damage from axe attack
     * @return description of axe attack on entity
     */
    @Override
    public String axe(Entity e) {
        int axeDamage = (int) ((Math.random() * 2) + 6);
        e.takeDamage(axeDamage);
        return "The " + getName() + " swings at " + e.getName() + " with an axe for " + axeDamage + " damage.";
    }

}
