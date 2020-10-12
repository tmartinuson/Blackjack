package model;

public class Player extends Dealer {
    private int cash;

    public Player() {
        super();
        cash = 5000;
    }

    //MODIFIES: this
    public boolean makeBet(int bet) {
        if (bet < cash) {
            cash -= bet;
        } else {
            return false;
        }
        return true;
    }

    public void addCash(int newCash) {
        cash += newCash;
    }

    public int getCash() {
        return cash;
    }

    public void setCash(int newCash) {
        cash = newCash;
    }
}
