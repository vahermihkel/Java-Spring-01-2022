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
        World world = new World(5,10);
        Player player = new Player();
        world.addCharacter(player);
        Enemy enemy = new Enemy();
        world.addCharacter(enemy);
        QuestMaster questMaster = new QuestMaster();
        world.addCharacter(questMaster);
        world.printMap();
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

        while (!input.equals("end")) {
            player.move(input, world);
            GameUtil.checkIfPlayerAndEnemyMet(player, enemy, scanner);
            GameUtil.checkIfPlayerAndQuestmasterMet(player, enemy, questMaster);
            world.printMap();
            input = scanner.nextLine();
        }
    }
}
