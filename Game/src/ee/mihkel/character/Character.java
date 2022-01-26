package ee.mihkel.character;

import ee.mihkel.World;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// encapsulation - kapseldus
public abstract class Character {
    protected int xCoord;
    protected int yCoord;
    private final char symbol;
    private boolean isVisible = true;
    private int health = 3;
    private static int NUMBER;
 // playeril oma xCoord
 // enemy oma xCoord
 // kui suurendan enemy xCoordinaati, siis teistel ei suurene

 // playeril ja enemyl ühine NUMBER
 // kui suurendan enemy NUMBER muutuja väärtust, siis suureneb kõigil


    public static void setNUMBER(int NUMBER) {
        Character.NUMBER = NUMBER;
    }

    public static int getNUMBER() {
        return NUMBER;
    }

    public Character(World world, char symbol) {
        this.symbol = symbol;
        randomiseCoordinates(world);
        System.out.println(this.xCoord);
        System.out.println(this.yCoord);
//        checkCoordinateesUniqueness(world);
    }

    public void randomiseCoordinates(World world) {
        this.xCoord = (int) (Math.random()*(world.getWidth()-2))+1;
        this.yCoord = (int) (Math.random()*(world.getHeight()-2))+1;
        checkCoordinateesUniqueness(world);
        // Player --- List on tühi
        // Enemy --- Listis on Player
        // QuestMaster --- Listis on Player, Enemy

        // Enemy --- Listis on Player, Enemy, QuestMaster
    }

    private void checkCoordinateesUniqueness(World world) {
        List<Character> charactersWithoutThis = world.getCharacters().stream()
                .filter(e -> e.symbol != this.symbol)
                .collect(Collectors.toList());

//        List<Character> charactersWithoutThis2 = new ArrayList<>();
//        for (Character c: world.getCharacters()) {
//            if (c.symbol != this.symbol) {
//                charactersWithoutThis2.add(c);
//            }
//        }

        // Enemy.yCoord == Enemy.yCoord
        for (Character c: charactersWithoutThis) {
            if (c.yCoord == this.yCoord && c.xCoord == this.xCoord) {
                randomiseCoordinates(world);
            }
        }
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getHealth() {
        return health;
    }

    public void takeHealth() {
        this.health--;
        // health -= 1;
        // health = health-1;
    }

    public void resetHealth() {
        this.health = 3;
    }
}
