package ee.mihkel;

import ee.mihkel.character.Enemy;
import ee.mihkel.character.Player;
import ee.mihkel.character.QuestMaster;

import java.util.Scanner;

public class GameUtil {
    protected static void checkIfPlayerAndQuestmasterMet(Player player, Enemy enemy, QuestMaster questMaster) {
        if (player.getxCoord() == questMaster.getxCoord() &&
                player.getyCoord() == questMaster.getyCoord()) {
            enemy.setVisible(true);
            enemy.randomiseEnemyType();
        }
    }

    protected static void checkIfPlayerAndEnemyMet(Player player, Enemy enemy, Scanner scanner) throws GameOverException {
        if (player.getxCoord() == enemy.getxCoord() &&
                player.getyCoord() == enemy.getyCoord() &&
                    enemy.isVisible()) {
            System.out.println("Kohtusid vaenlasega!");
            System.out.println("Võitlemiseks ütle üks number 1-3");
            enemy.setVisible(false);
            while (player.getHealth() > 0 && enemy.getHealth() > 0) {
                fightWithEnemy(player, enemy, scanner);
            }
            if (enemy.getHealth() == 0) {

            } else if (player.getHealth() == 0) {
                throw new GameOverException(); // kui on throw new, siis peab olema kuskil ka catch
            }
        }
    }

    private static void fightWithEnemy(Player player, Enemy enemy, Scanner scanner) {
        int randomNumber = (int) (Math.random()*3)+1; // double muutmine int (cast)
        int playerNumber = 0; // String muutmine int (parse)
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
            } catch (NumberTooSmallOrBigException e) {
                System.out.println("Sisestasid liiga suure või väikse numbri, sisesta uuesti!");
            }
        }
    }
}
