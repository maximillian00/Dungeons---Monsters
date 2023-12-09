public abstract class Enemy extends Entity {

    /**
     * Constructor that sets enemy name and max hp
     * @param n name of enemy
     * @param mHp max health of enemy
     */
    public Enemy(String n, int mHp){
        super(n, mHp);
    }

    /**
     * Abstract attack method to be overridden by specific enemy types
     * @param h hero object which enemy will attack
     * @return description of attack and damage dealt
     */
    public abstract String attack(Hero h);
}
