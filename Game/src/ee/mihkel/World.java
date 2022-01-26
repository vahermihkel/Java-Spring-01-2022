package ee.mihkel;

import ee.mihkel.character.Character;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final int height;
    private final int width;
    // encapsulation == private muutujate ees
    private final List<Character> characters = new ArrayList<>();

    // constructor
    public World(int height, int width) {
        System.out.println("loodi uus maailm");
        this.height = height;
        this.width = width;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    //    public void setCharacters(List characters) {
//        this.characters = characters;
//    }

    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    public List<Character> getCharacters() {
        return characters;
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
                if (x == 0 || x == width - 1) {
                    symbol = '|';
                } else if (y == 0 || y == height - 1) {
                    symbol = '-';
                } else {
                    symbol = ' ';
                }
                for (Character c : characters) {
                    if (c.getyCoord() == y && c.getxCoord() == x && c.isVisible()) {
                        symbol = c.getSymbol();
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
