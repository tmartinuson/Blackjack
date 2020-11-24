package ui;

import model.Dealer;
import model.Player;
import persistence.DataReader;
import persistence.DataWriter;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents a PlayableGame of Blackjack to be implemented depending on the user interface.
public abstract class PlayableGame {
    protected static final String GAME_STORE = "./data/player.json";  // save/load game file path
    protected int playerBet = 0;                          // the player's bet made
    protected Player player;                              // the player
    protected Dealer dealer;                              // the dealer
    protected DataWriter writer;                          // data writer to save game
    protected DataReader reader;                          // data reader to load game

    //EFFECTS: creates a playable game object and initializes the data writing and reading streams.
    public PlayableGame() {
        writer = new DataWriter(GAME_STORE);
        reader = new DataReader(GAME_STORE);
    }

    //MODIFIES: this
    //EFFECTS: runs the game based off user decisions and data
    protected abstract void runGame();

    //EFFECTS: displays the blackjack table and card hands for the user
    protected abstract void displayHands(boolean dealerTurn);

    //MODIFIES: this
    //EFFECTS: asks the player if they want to load their previous game from file
    // and will load that game if chosen.
    protected abstract void loadPrompt();

    //MODIFIES: this
    //EFFECTS: checks to see who won the game by having the higher hand without going over 21.
    //If both the user and dealer have the same total amount in their hands then its called a push and the bet
    // stays with the player.
    protected abstract void checkWhoWon();

    //REQUIRES: the user must enter a valid bet. (within their bank and an integer)
    //MODIFIES: this
    //EFFECTS: asks the user what they would like to bet and places that bet.
    protected abstract void placeBets();

    //MODIFIES: this
    //EFFECTS: resets the game for another play through of Blackjack.
    protected abstract void reset();

    //MODIFIES: this
    //EFFECTS: checks to see if the player won a blackjack. Blackjack pays out a 3:2 ratio
    protected boolean checkBlackjack() {
        if (player.handTotal() == 21) {
            playerBet *= (1.5);
            System.out.println("Blackjack (Payout 3/2)! You've won " + playerBet);
            return true;
        } else {
            return false;
        }
    }

    //MODIFIES: this
    //EFFECTS: plays out the dealer's turn following the same rules as the player. If the dealer busts then the player
    // wins the bet. Dealer plays to a 'soft 17' which means that they will stop hitting if their hand total is
    // equal to or greater than 17.
    protected void playDealer() {
        //For 2 aces in original hand
        if (dealer.handTotal() > 21 && dealer.hasAce()) {
            dealer.swapAce();
        }
        while (dealer.handTotal() < 17) {
            dealer.hit();
            if (dealer.handTotal() > 21 && dealer.hasAce()) {
                dealer.swapAce();
            }
        }
        displayHands(true);
        if (dealer.handTotal() > 21) {
            System.out.println("Dealer Busts!\n" + player.getName() + " wins!");
            player.addCash(playerBet);
            dealer.setBust(true);
        }
    }

    //EFFECTS: saves the player's data to a file
    protected void saveGame() {
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
    protected void loadGame() {
        try {
            player = reader.read();
            System.out.println("Successfully loaded " + player.getName() + "'s game from " + GAME_STORE);
            System.out.println("Player name: " + player.getName() + "\nBank: " + player.getCash());
        } catch (IOException e) {
            System.out.println("Load unsuccessful.");
        }
    }
}
