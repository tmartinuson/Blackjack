package ui;

import model.Dealer;
import model.Player;
import java.util.Scanner;

public class RunGame {
    private static Scanner keyboard = new Scanner(System.in);
    private static String response = "";
    private static Player player = new Player();
    private static Dealer dealer = new Dealer();

    public static void main(String[] args) {
        System.out.println("Welcome to blackjack! Type answers: \"hit\", \"stay\".");
        while (true) {
            player.deal();
            dealer.deal();
            displayTable(false);
            if (checkBlackjack()) {
                System.out.println("Play again? y/n");
                response = keyboard.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    continue;
                } else {
                    break;
                }
            }
            playGame();
        }
    }

    private static void displayTable(boolean dealerTurn) {
        if (dealerTurn) {
            System.out.println(
                    "~~~~~~~~~~~~~~~\n"
                            + "   " + dealer
                            + "\n\n   "
                            + player
                            + "\n~~~~~~~~~~~~~~~");
        } else {
            System.out.println(
                    "~~~~~~~~~~~~~~~\n"
                            + "   " + dealer.dealerHand()
                            + "\n\n   "
                            + player
                            + "\n~~~~~~~~~~~~~~~");
        }
    }

    private static boolean checkBlackjack() {
        if (player.handTotal() == 21) {
            System.out.println("Blackjack! You've won!");
            return true;
        } else {
            return false;
        }
    }

    private static void playGame() {
        while (player.handTotal() < 21) {
            System.out.println("Hit or stay?");
            response = keyboard.nextLine();
            if (response.equalsIgnoreCase("hit")) {
                player.hit();

                if (player.handTotal() > 21 && player.hasAce()) {
                    player.swapAce();
                } else if (player.handTotal() > 21) {
                    System.out.println("Bust!");
                }
                displayTable(false);
            } else if (response.equalsIgnoreCase("stay")) {
                playDealer();
            }
        }
    }

    private static void playDealer() {
        while (dealer.handTotal() < 17) {
            dealer.hit();
            if (dealer.handTotal() > 21 && dealer.hasAce()) {
                dealer.swapAce();
            } else if (dealer.handTotal() > 21) {
                System.out.println("Dealer Busts!");
            }
            displayTable(true);
        }
    }
}
