package ee.mihkel.character;

// encapsulation - kapseldus
public abstract class Character {
    protected int xCoord;
    protected int yCoord;
    protected char symbol;
    private boolean isVisible = true;
    private int health = 3;

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

    public int takeHealth() {
        return health--;
        // return health -= 1;
        // return health = health-1;
    }

    public void resetHealth() {
        this.health = 3;
    }
}
