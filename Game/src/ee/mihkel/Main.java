package ee.mihkel;

import ee.mihkel.character.Character;
import ee.mihkel.character.Enemy;
import ee.mihkel.character.Player;
import ee.mihkel.character.QuestMaster;

import java.util.Scanner;

public class Main {

    // throw new exception
    // custom exception
    // hashmap
    // util class
    // static
    // streamid
    // interfaces

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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        // ctrl + alt + t
        while (!input.equals("end")) {
            player.move(input, world);
            if (player.getxCoord() == enemy.getxCoord() &&
                    player.getyCoord() == enemy.getyCoord()) {
                System.out.println("Kohtusid vaenlasega!");
                System.out.println("Võitlemiseks ütle üks number 1-3");
                enemy.setVisible(false);
                while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                    int randomNumber = (int) (Math.random()*3)+1; // double muutmine int (cast)
                    int playerNumber = 0; // String muutmine int (parse)
                    // ctrl + alt + t -- try-catch
                    while (playerNumber < 1 || playerNumber > 3) {
                        try {
                            playerNumber = Integer.parseInt(scanner.nextLine());
                            if (randomNumber == playerNumber) {
                                player.takeHealth();
                            } else if (playerNumber >= 1 && playerNumber <= 3) {
                                enemy.takeHealth();
                            } else {
                                throw new NumberTooSmallOrBigException();
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Sisestasid numbri asemel muu sümboli, sisesta uuesti!");
    //                        e.printStackTrace();
                        } catch (NumberTooSmallOrBigException e) {
                            System.out.println("Sisestasid liiga suure või väikse numbri, sisesta uuesti!");
                        }
                    }
                }
            }
            if (player.getxCoord() == questMaster.getxCoord() &&
                    player.getyCoord() == questMaster.getyCoord()) {
                enemy.setVisible(true);
            }
            world.printMap();
            input = scanner.nextLine();
        }
    }
}
