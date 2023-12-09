import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.io.File;

public class EnemyGenerator {
    private HashMap<String, Integer> enemies = new HashMap<>();

    /**
     * Reads entries from Enemies.txt file and adds key-value pairs into hash map
     */
    public EnemyGenerator() {
        try {
            Scanner read = new Scanner(new File("Enemies.txt"));
            while (read.hasNext()) {
                String nextLine = read.nextLine();
                String[] enemyEntry = nextLine.split(",");
                enemies.put(enemyEntry[0], Integer.parseInt(enemyEntry[1]));
            }
            read.close();
        } catch (FileNotFoundException fnf) {
            System.out.println("File was not found.");
        }
    }

    /**
     * Chooses random hashmap entry and creates an enemy of random type with health scaled to current level
     * @param level level that the player is currently on
     * @return enemy of randomly chosen type, with health scaled to current player level
     */
    public Enemy generateEnemy(int level) {

        // Copy enemy names to an array list and randomly select one
        ArrayList<String> enemyList = new ArrayList<>(enemies.keySet());
        int randomEnemyNum = (int) (Math.random() * enemies.keySet().size());
        String randomEnemy = enemyList.get(randomEnemyNum);

        // For every additional level, base health of enemy += 10
        int scaledHealth = enemies.get(randomEnemy) + (10 * (level - 1));

        // Randomly choose an enemy type to assign (Warrior, Wizard, Ranger)
        int randomTypeNum = (int) ((Math.random() * 3) + 1);
        if (randomTypeNum == 1) {
            return new Warrior(randomEnemy + " Warrior", scaledHealth);
        } else if (randomTypeNum == 2) {
            return new Wizard(randomEnemy + " Wizard", scaledHealth);
        } else {
            return new Ranger(randomEnemy + " Ranger", scaledHealth);
        }
    }
}
