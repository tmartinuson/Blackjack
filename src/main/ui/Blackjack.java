package ui;

import java.util.Scanner;

// Represents a game of Blackjack as a console version of the game.
public class Blackjack extends PlayableGame {
    private Scanner keyboard = new Scanner(System.in);  // keyboard scanner for input
    private String response = "";                       // storage for input

    //EFFECTS: Runs the console version of the Blackjack game
    public Blackjack() {
        super();
        runGame();
    }

    //MODIFIES: this
    //EFFECTS: runs the game based off user decisions and data
    @Override
    protected void runGame() {
        System.out.println("Welcome to blackjack! Your starting cash is $5000. Type answers: \"hit\", \"stay\".\n");
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
    @Override
    protected void displayHands(boolean dealerTurn) {
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
    //EFFECTS: plays the turn using the user's responses. The goal is to get 21 or as close as possible
    // and if the user doesnt bust then the dealer will play to compete.
    //If the dealer gets a higher hand without going over 21 then the user loses the bet.
    protected void playTurn() {
        while (player.handTotal() < 21) {
            System.out.println("Hit or stay?");
            response = keyboard.nextLine();
            if (response.equalsIgnoreCase("hit")) {
                player.hit();
                if (player.handTotal() > 21 && player.hasAce()) {
                    player.swapAce();
                }
                if (player.handTotal() > 21) {
                    displayHands(false);
                    System.out.println("Bust!");
                    player.subtractCash(playerBet);
                    player.setBust(true);
                    break;
                }
                displayHands(false);
            } else if (response.equalsIgnoreCase("stay")) {
                break;
            }
        }
        endOfTurn();
    }

    //MODIFIES: this
    //EFFECTS: checks and makes the appropriate end game method calls depending if the player or dealer busts.
    protected void endOfTurn() {
        if (!player.getBust()) {
            playDealer();
            if (!dealer.getBust()) {
                checkWhoWon();
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: checks to see who won the game by having the higher hand without going over 21.
    //If both the user and dealer have the same total amount in their hands then its called a push and the bet
    // stays with the player.
    @Override
    protected void checkWhoWon() {
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
    @Override
    protected void reset() {
        placeBets();
        player.deal();
        dealer.deal();
        player.setBust(false);
        dealer.setBust(false);
        displayHands(false);
    }

    //REQUIRES: the user must enter an integer (user cannot enter a String).
    // Bet cannot be negative or more than what the user has in their cash bank.
    // User must also have cash in their bank in order to play.
    //MODIFIES: this
    //EFFECTS: asks the user what they would like to bet and places that bet.
    @Override
    protected void placeBets() {
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
    @Override
    protected void loadPrompt() {
        System.out.println("Would you like to load from your previous game? y/n");
        String response = keyboard.nextLine();
        if (response.equalsIgnoreCase("y")) {
            loadGame();
        } else {
            System.out.println("Enter your name: ");
            player.setName(keyboard.nextLine());
        }
    }

    @Override
    protected void loadGame() {
        super.loadGame();
        //Default build if the load was unsuccessful
        if (player.getName().equals("Player") && player.getCash() == 5000) {
            System.out.println("Enter your name: ");
            player.setName(keyboard.nextLine());
        }
    }
}
