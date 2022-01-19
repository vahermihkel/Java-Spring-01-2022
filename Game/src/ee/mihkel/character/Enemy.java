package ee.mihkel.character;

public class Enemy extends Character {
    private EnemyType enemyType;

    // List = [Ant, Horse]
    // HashMap = [{Ant: 2}, {Horse: 1}]

    public Enemy() {
        this.xCoord = 2;
        this.yCoord = 2;
        this.symbol = 'Z';
        this.enemyType = EnemyType.getRandomEnemyType();
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void randomiseEnemyType() {
        this.enemyType = EnemyType.getRandomEnemyType();
    }
}
