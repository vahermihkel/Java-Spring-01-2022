package ee.mihkel;

import ee.mihkel.character.Player;

import java.util.ArrayList;
import java.util.List;

public class World {
    private int height;
    private int width;
    // encapsulation == private muutujate ees
    private List<Player> characters = new ArrayList();

    // constructor
    public World(int height, int width) {
        System.out.println("loodi uus maailm");
        this.height = height;
        this.width = width;
    }

//    public void setCharacters(List characters) {
//        this.characters = characters;
//    }

    public void addCharacter(Player player) {
        this.characters.add(player);
    }

    //    // constructor overloading
//    public World() {
//
//    }

//    if (kasutaja_acceptis && ei_ole_null && andmbaasipäring())
//    if (kasutaja_acceptis || ei_ole_null || andmbaasipäring())
// && puhul kõige tõenäolisemalt false kõige vasakule
// || puhul kõige tõenäolisemalt true kõige vasakule

    public void printMap() {
        System.out.println("kutsuti välja maailma kaudu printMap funktsioon");
        char symbol; // declaration 1x . siin luuakse mälukoht sellele muutujale
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x == 0 || x == width-1) {
                    symbol = '|';
                } else if (y == 0 || y == height-1) {
                    symbol = '-';
                } else {
                    symbol = ' ';
                }
                for (Player character: characters) {
                    if (character.getyCoord() == y && character.getxCoord() == x) {
                        symbol = character.getSymbol();
                    }
                }
//                for (int i = 0; i < characters.size(); i++) {
//                    if (characters.get(i).getyCoord() == y && characters.get(i).getxCoord() == x) {
//                        symbol = characters.get(i).getSymbol();
//                    }
//                }
                System.out.print(symbol);
            }
            System.out.println();
        }
    }
}
