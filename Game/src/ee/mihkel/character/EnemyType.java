package ee.mihkel.character;

public enum EnemyType {
    ANT, MOUSE, CAT, DOG, HORSE, ELEPHANT;

    protected static EnemyType getRandomEnemyType(){
        return values()[(int) (Math.random()*values().length)];
    }
}
