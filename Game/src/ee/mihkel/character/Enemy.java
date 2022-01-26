package ee.mihkel.character;

import ee.mihkel.World;

public class Enemy extends Character {
    private EnemyType enemyType;

    // List = [Ant, Horse]
    // HashMap = [{Ant: 2}, {Horse: 1}]

    public Enemy(World world) {
        super(world, 'Z');
        this.enemyType = EnemyType.getRandomEnemyType();
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public void randomiseEnemyType() {
        this.enemyType = EnemyType.getRandomEnemyType();
        // läheb annab elud erinevalt vastavalt EnemyType
        // getEnemyHealthByType()
    }

    // private void getEnemyHealthByType()
    // switch case
    // ANT: elud 1
    // ELEPHANT: elud 6

}
