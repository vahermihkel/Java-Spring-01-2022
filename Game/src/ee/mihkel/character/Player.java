package ee.mihkel.character;

import ee.mihkel.World;

import java.util.HashMap;
import java.util.Map;

// extends == pärilus ehk subclassi loomine
// child - parent
// child saab kõik omadused parent seest
public class Player extends Character {
    private Direction direction;
    private Map<EnemyType, Integer> killedEnemies = new HashMap<>();

    public Player() {
        this.xCoord = 3;
        this.yCoord = 3;
        this.symbol = 'X';
        this.direction = Direction.UP;
    }

    public void showKilledEnemies() {
        // kuva välja sout abil kõik tapetud vaenlased
        // kutsu see funktsioon välja kui mäng on läbi (catch GameOverException sees)
    }

    public void addToKilledEnemies(EnemyType enemyType) {
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
