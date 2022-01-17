package ee.mihkel.character;

// extends == pärilus ehk subclassi loomine
// child - parent
// child saab kõik omadused parent seest
public class Player extends Character {

    public Player() {
        this.xCoord = 3;
        this.yCoord = 3;
        this.symbol = 'X';
    }
}
