package ui;

import model.Dealer;
import model.Player;
import persistence.DataReader;
import persistence.DataWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.management.GarbageCollectorMXBean;
import java.util.Scanner;

// Blackjack game application
public class Blackjack {
    private Scanner keyboard = new Scanner(System.in);  // keyboard scanner for input
    private String response = "";                       // storage for input
    private int playerBet = 0;                          // the player's bet made
    private Player player;                              // the player
    private Dealer dealer;                              // the dealer
    private DataWriter writer;                          // data writer to save game
    private DataReader reader;                          // data reader to load game
    private static final String GAME_STORE = "./data/player.json";  // save/load game file path

    //EFFECTS: Runs the Blackjack game
    public Blackjack() {
        runBlackjack();
    }

    //MODIFIES: this
    //EFFECTS: runs the game based off user decisions and data
    private void runBlackjack() {
        System.out.println("Welcome to blackjack! Your starting cash is $5000. Type answers: \"hit\", \"stay\".\n");
        player = new Player();
        dealer = new Dealer();
        writer = new DataWriter(GAME_STORE);
        reader = new DataReader(GAME_STORE);
        loadPrompt();
        while (true) {
            reset();
            if (!checkBlackjack()) {
                playTurn();
            }
            System.out.println("Play again? y/n");
            response = keyboard.nextLine();
            if (response.equalsIgnoreCase("n")) {
                System.out.println("Would you like to save? y/n");
                response = keyboard.nextLine();
                if (response.equalsIgnoreCase("y")) {
                    saveGame();
                }
                break;
            }
        }
    }

    //EFFECTS: displays the blackjack table for the user in console depending on if its the user's turn or the dealer's
    private void displayTable(boolean dealerTurn) {
        if (dealerTurn) {
            System.out.println(
                    "\nDealer's hand"
                    + "\n~~~~~~~~~~~~~~~\n"
                    + "   " + dealer + "    Hand Total: " + dealer.handTotal()
                    + "\n\n   "
                    + player + "    Hand Total: " + player.handTotal()
                    + "\n~~~~~~~~~~~~~~~\n"
                    + player.getName() + "'s hand\n"
                    + "Bank: " + player.getCash() + "      Bet: " + playerBet
                    + "\n");
        } else {
            System.out.println(
                    "\nDealer's hand"
                    + "\n~~~~~~~~~~~~~~~\n"
                    + "   " + dealer.dealerHand()
                    + "\n\n   "
                    + player + "    Hand Total: " + player.handTotal()
                    + "\n~~~~~~~~~~~~~~~\n"
                    + player.getName() + "'s hand\n"
                    + "Bank: " + player.getCash() + "      Bet: " + playerBet
                    + "\n");
        }
    }

    //MODIFIES: this
    //EFFECTS: checks to see if the user won a blackjack. Blackjack pays out a 3:2 ratio
    private boolean checkBlackjack() {
        if (player.handTotal() == 21) {
            playerBet *= (1.5);
            System.out.println("Blackjack (Payout 3/2)! You've won " + playerBet);
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: plays the turn using the user's responses. The goal is to get 21 or as close as possible
    // and if the user doesnt bust then the dealer will play to compete.
    //If the dealer gets a higher hand without going over 21 then the user loses the bet.
    private void playTurn() {
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
        endOfTurn();
    }

    //MODIFIES: this
    //EFFECTS: checks and makes the appropriate end game method calls depending if the player or dealer busts.
    private void endOfTurn() {
        if (!player.getBust()) {
            playDealer();
            if (!dealer.getBust()) {
                checkWhoWon();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: plays out the dealer's turn following the same rules as the player. If the dealer busts then the player
    // wins the bet.
    private void playDealer() {
        while (dealer.handTotal() < 17) {
            dealer.hit();
            if (dealer.handTotal() > 21 && dealer.hasAce()) {
                dealer.swapAce();
            }
        }
        displayTable(true);
        if (dealer.handTotal() > 21) {
            System.out.println("Dealer Busts!\n" + player.getName() + " wins!");
            player.addCash(playerBet);
            dealer.setBust(true);
        }
    }

    //MODIFIES: this
    //EFFECTS: checks to see who won the game by having the higher hand without going over 21.
    //If both the user and dealer have the same total amount in their hands then its called a push and the bet
    // stays with the player.
    private void checkWhoWon() {
        if (dealer.handTotal() > player.handTotal() && !(dealer.handTotal() > 21)) {
            System.out.println("Dealer wins!");
            player.subtractCash(playerBet);
        } else if (player.handTotal() > dealer.handTotal() && !(player.handTotal() > 21)) {
            System.out.println(player.getName() + " wins!");
            player.addCash(playerBet);
        } else if (player.handTotal() == dealer.handTotal()) {
            System.out.println("Push!");
        }
    }

    //MODIFIES: this
    //EFFECTS: resets the game for the next round by placing bets
    // and dealing new cards and clearing previous conditions.
    private void reset() {
        placeBets();
        player.deal();
        dealer.deal();
        player.setBust(false);
        dealer.setBust(false);
        displayTable(false);
    }

    //REQUIRES: the user must enter an integer (user cannot enter a String).
    // Bet cannot be negative or more than what the user has in their cash bank.
    // User must also have cash in their bank in order to play.
    //MODIFIES: this
    //EFFECTS: asks the user what they would like to bet and places that bet.
    private void placeBets() {
        System.out.println("\nPlace your bet!");
        while (true) {
            try {
                playerBet = keyboard.nextInt();
                if (playerBet < 1 || playerBet > player.getCash()) {
                    throw new RuntimeException();
                }
                break;
            } catch (RuntimeException e) {
                System.out.println("Invalid. Please place a valid bet: ");
                playerBet = 0;
                keyboard.nextLine();
            }
        }
        keyboard.nextLine();
    }

    //MODIFIES: this
    //EFFECTS: asks the player if they want to load their previous game from file
    // and will load that game if chosen. Otherwise the player will enter their name for a new game.
    private void loadPrompt() {
        System.out.println("Would you like to load from your previous game? y/n");
        String response = keyboard.nextLine();
        if (response.equalsIgnoreCase("y")) {
            loadGame();
        } else {
            System.out.println("Enter your name: ");
            player.setName(keyboard.nextLine());
        }
    }

    //EFFECTS: saves the player's data to a file
    private void saveGame() {
        try {
            writer.open();
            writer.write(player);
            writer.close();
            System.out.println("Successfully saved " + player.getName() + "'s game to " + GAME_STORE);
            System.out.println("Player name: " + player.getName() + "\nBank: " + player.getCash());
        } catch (FileNotFoundException e) {
            System.out.println("Save unsuccessful at: " + GAME_STORE);
        }
    }

    //MODIFIES: this
    //EFFECTS: loads the player's data from a file
    private void loadGame() {
        try {
            player = reader.read();
            System.out.println("Successfully loaded " + player.getName() + "'s game from " + GAME_STORE);
            System.out.println("Player name: " + player.getName() + "\nBank: " + player.getCash());
        } catch (IOException e) {
            System.out.println("Load unsuccessful.");
            System.out.println("Enter your name: ");
            player.setName(keyboard.nextLine());
        }
    }
}
