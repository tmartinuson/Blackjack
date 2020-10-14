package ui;

import model.Dealer;
import model.Player;
import java.util.Scanner;

public class RunGame {
    private static Scanner keyboard = new Scanner(System.in);
    private static String response = "";
    private static int playerBet = 0;
    private static Player player = new Player();
    private static Dealer dealer = new Dealer();

    public static void main(String[] args) {
        System.out.println("Welcome to blackjack! Type answers: \"hit\", \"stay\".");
        while (true) {
            reset();
            if (!checkBlackjack()) {
                playGame();
            }
            System.out.println("Play again? y/n");
            response = keyboard.nextLine();
            if (response.equalsIgnoreCase("n")) {
                break;
            }
        }
    }

    private static void displayTable(boolean dealerTurn) {
        if (dealerTurn) {
            System.out.println(
                    "\n~~~~~~~~~~~~~~~\n"
                            + "   " + dealer + dealer.handTotal()
                            + "\n\n   "
                            + player + player.handTotal()
                            + "\n~~~~~~~~~~~~~~~\n"
                            + "Bank: " + player.getCash() + "      Bet: " + playerBet);
        } else {
            System.out.println(
                    "\n~~~~~~~~~~~~~~~\n"
                            + "   " + dealer.dealerHand()
                            + "\n\n   "
                            + player + player.handTotal()
                            + "\n~~~~~~~~~~~~~~~\n"
                            + "Bank: " + player.getCash() + "      Bet: " + playerBet);
        }
    }

    private static boolean checkBlackjack() {
        if (player.handTotal() == 21) {
            playerBet *= (1.5);
            System.out.println("Blackjack! You've won " + playerBet);
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
                }
                if (player.handTotal() > 21) {
                    displayTable(false);
                    System.out.println("Bust!");
                    player.subtractCash(playerBet);
                    player.setBust(true);
                    break;
                }
                displayTable(false);
            } else if (response.equalsIgnoreCase("stay")) {
                break;
            }
        }
        if (!player.getBust()) {
            playDealer();
            if (!dealer.getBust()) {
                checkWhoWon();
            }
        }
    }

    private static void playDealer() {
        while (dealer.handTotal() < 17) {
            dealer.hit();
            if (dealer.handTotal() > 21 && dealer.hasAce()) {
                dealer.swapAce();
            }
        }
        displayTable(true);
        if (dealer.handTotal() > 21) {
            System.out.println("Dealer Busts!\nPlayer wins!");
            player.addCash(playerBet);
            dealer.setBust(true);
        }
    }

    private static void checkWhoWon() {
        if (dealer.handTotal() > player.handTotal() && !(dealer.handTotal() > 21)) {
            System.out.println("Dealer wins!");
            player.subtractCash(playerBet);
        } else if (player.handTotal() > dealer.handTotal() && !(player.handTotal() > 21)) {
            System.out.println("Player wins!");
            player.addCash(playerBet);
        } else if (player.handTotal() == dealer.handTotal()) {
            System.out.println("Push!");
        }
    }

    public static void reset() {
        placeBets();
        player.deal();
        dealer.deal();
        player.setBust(false);
        dealer.setBust(false);
        displayTable(false);
    }

    public static void placeBets() {
        System.out.println("Place your bet!");
        playerBet = keyboard.nextInt();
        //Fix this
        /*while (playerBet < 1 || playerBet > player.getCash()) {
            try {
                playerBet = keyboard.nextInt();
            } catch (Exception e) {
                System.out.println("Invalid. Please place a valid bet: ");
                playerBet = 0;
            }
        }*/
        keyboard.nextLine();
    }
}
