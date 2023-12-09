public class Wizard extends Enemy implements Magical {

    /**
     * Constructor that sets wizard name and max hp
     * @param n name of wizard
     * @param mHp max health of wizard
     */
    public Wizard(String n, int mHp) {
        super(n, mHp);
    }

    /**
     * Randomly choose to attack hero with either magic missile or fireball
     * @param h hero object which enemy will attack
     * @return description of attack method chosen at random
     */
    @Override
    public String attack(Hero h) {
        int randomAttack = (int) ((Math.random() * 2) + 1);
        if (randomAttack == 1) {
            return magicMissile(h);
        } else {
            return fireball(h);
        }
    }

    /**
     * Deal magic missile damage to entity within range: 2 - 4
     * @param e entity to take damage from magic missile attack
     * @return description of magic missile attack on entity
     */
    @Override
    public String magicMissile(Entity e) {
        int missileDamage = (int) ((Math.random() * 3) + 2);
        e.takeDamage(missileDamage);
        return "The " + getName() + " fires a magic missile at " + e.getName() + " for " + missileDamage + " damage.";
    }

    /**
     * Deal fireball damage to entity within range: 5 - 6
     * @param e entity to take damage from fireball attack
     * @return description of fireball attack on entity
     */
    @Override
    public String fireball(Entity e) {
        int fireballDamage = (int) ((Math.random() * 2) + 5);
        e.takeDamage(fireballDamage);
        return "The " + getName() + " shoots a fireball at " + e.getName() + " for " + fireballDamage + " damage.";
    }
}
