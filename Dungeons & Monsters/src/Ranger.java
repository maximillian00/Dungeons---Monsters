public class Ranger extends Enemy implements Archer {

    /**
     * Constructor that sets ranger name and max hp
     * @param n name of ranger
     * @param mHp max health of ranger
     */
    public Ranger(String n, int mHp) {
        super(n, mHp);
    }

    /**
     * Randomly choose to attack hero with either basic arrow or fire arrow
     * @param h hero object which enemy will attack
     * @return description of attack method chosen at random
     */
    @Override
    public String attack(Hero h) {
        int randomAttack = (int) ((Math.random() * 2) + 1);
        if (randomAttack == 1) {
            return arrow(h);
        } else {
            return fireArrow(h);
        }
    }

    /**
     * Deal basic arrow damage to entity within range: 1 - 3
     * @param e entity to take damage from basic arrow attack
     * @return description of basic arrow attack on entity
     */
    @Override
    public String arrow(Entity e) {
        int arrowDamage = (int) ((Math.random() * 3) + 1);
        e.takeDamage(arrowDamage);
        return "The " + getName() + " fires an arrow at " + e.getName() + " for " + arrowDamage + " damage.";
    }

    /**
     * Deal fire arrow damage to entity within range: 4 - 5
     * @param e entity to take damage from fire arrow attack
     * @return description of fire arrow attack on entity
     */
    @Override
    public String fireArrow(Entity e) {
        int fireArrowDamage = (int) ((Math.random() * 2) + 4);
        e.takeDamage(fireArrowDamage);
        return "The " + getName() + " shoots a flaming arrow at " + e.getName() + " for " + fireArrowDamage + " damage.";
    }
}
