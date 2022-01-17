package ee.mihkel;

import ee.mihkel.character.Character;
import ee.mihkel.character.Enemy;
import ee.mihkel.character.Player;
import ee.mihkel.character.QuestMaster;

public class Main {

    // streamid
    // interfaces
    // throw new exception
    // custom exception
    // hashmap
    // util class

    public static void main(String[] args) {
        System.out.println("töötab");
        World world = new World(5,10);
        //World world = new World --- luuakse mälukoht
        Player player = new Player();
        world.addCharacter(player);
        Enemy enemy = new Enemy();
        world.addCharacter(enemy);
        QuestMaster questMaster = new QuestMaster();
        world.addCharacter(questMaster);

//        Character character = new Character();
//        world.addCharacter(character);

//        world.setCharacters(Arrays.asList(player,player2));
        world.printMap();
    }
}
