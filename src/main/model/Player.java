package model;

// Represents the player/user with all the same methods as the Dealer
// in addition to storing the user's cash data for betting.
public class Player extends Dealer {
    private int cash;           // the player's cash stored in their bank

    //EFFECTS: super creates a new empty hand for the player,
    // sets the players bust to false,
    // deals the player 2 new cards,
    // and swaps the ace out in case of 2 aces appearing in a hand.
    //Sets the player's cash bank to 5000 as a starting balance.
    public Player() {
        super();
        cash = 5000;
    }

    //REQUIRES: bet to be no greater than cash and must be positive
    //MODIFIES: this
    //EFFECTS: Subtracts the given bet from the player's cash
    public void subtractCash(int bet) {
        cash -= bet;
    }

    //REQUIRES: bet must be positive
    //MODIFIES: this
    //EFFECTS: Adds the given bet to the player's cash
    public void addCash(int bet) {
        cash += bet;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int newCash) {
        cash = newCash;
    }
}
