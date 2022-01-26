package ee.mihkel.character;

import ee.mihkel.World;

import java.util.HashMap;
import java.util.Map;

// extends == pärilus ehk subclassi loomine
// child - parent
// child saab kõik omadused parent seest
public class Player extends Character {
    private Direction direction;
    private final Map<EnemyType, Integer> killedEnemies = new HashMap<>();

    public Player(World world) {
        super(world, 'X');
        this.direction = Direction.UP;
    }

    public void showKilledEnemies() {
        // kuva välja sout abil kõik tapetud vaenlased
        // kutsu see funktsioon välja kui mäng on läbi (catch GameOverException sees)
        killedEnemies.forEach((key,value) -> System.out.println(key + ": " + value));
    }

    public void addToKilledEnemies(EnemyType enemyType) {
        if (killedEnemies.containsKey(enemyType)) {
            killedEnemies.put(enemyType, killedEnemies.get(enemyType)+1);
        } else {
            killedEnemies.put(enemyType, 1);
        }
        // lisa ülemisele HashMapile uus tapetud EnemyType
        // KUI on olemas, siis suurenda Integeri ühe võrra
        // KUI ei ole olemas, siis lisa uus EnemyType ja talle tapetud numbri väärtus 1
        // kutsu välja kui Enemy elud on 0 (GameUtil sees)
    }

    public void move(String input, World world) {
        switch (input) {
            case "w":
                this.direction = Direction.UP;
                break;
            case "s":
                this.direction = Direction.DOWN;
                break;
            case "d":
                this.direction = Direction.RIGHT;
                break;
            case "a":
                this.direction = Direction.LEFT;
                break;
        }

        switch (direction) {
            case UP:
                if (yCoord > 1) {
                    yCoord--;
                }
                break;
            case DOWN:
                if (yCoord < world.getHeight()-2) {
                    yCoord++;
                }
                break;
            case RIGHT:
                if (xCoord < world.getWidth()-2) {
                    xCoord++;
                }
                break;
            case LEFT:
                if (xCoord > 1) {
                    xCoord--;
                }
                break;
        }
    }
}
