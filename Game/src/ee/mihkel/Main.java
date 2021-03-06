package ee.mihkel;

import ee.mihkel.character.Character;
import ee.mihkel.character.Enemy;
import ee.mihkel.character.Player;
import ee.mihkel.character.QuestMaster;

import java.util.Scanner;

public class Main {

    // throw new exception
    // custom exception
    // util class
    // hashmap
    // static
    // streamid
    // interfaces

    public static void main(String[] args) {
        World world = new World(5,10);
        Player player = new Player(world);
        world.addCharacter(player);
        Enemy enemy = new Enemy(world);
        world.addCharacter(enemy);
        QuestMaster questMaster = new QuestMaster(world);
        world.addCharacter(questMaster);
        world.printMap();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

//        enemy.getNUMBER();

        try {
            while (!input.equals("end")) {
                player.move(input, world);
                GameUtil.checkIfPlayerAndEnemyMet(player, enemy, scanner);
                GameUtil.checkIfPlayerAndQuestmasterMet(player, enemy, questMaster, world);
                world.printMap();
                input = scanner.nextLine();
            }
        } catch (GameOverException e) {
            System.out.println("Mäng läbi!");
        }
    }
}
