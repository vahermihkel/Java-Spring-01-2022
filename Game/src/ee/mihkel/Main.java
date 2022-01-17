package ee.mihkel;

import ee.mihkel.character.Player;

public class Main {

    public static void main(String[] args) {
        System.out.println("töötab");
        World world = new World(5,10);
        //World world = new World --- luuakse mälukoht
        Player player = new Player();
        world.addCharacter(player);

        System.out.println(player.xCoord);


//        world.setCharacters(Arrays.asList(player,player2));
        world.printMap();
    }
}
