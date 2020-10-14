package model;

public class Player extends Dealer {
    private int cash;

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
