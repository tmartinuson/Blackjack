package ui;

import model.Card;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

// Represents a Blackjack GUI version of the game with a full table graphically displayed with the functionality
//of a standard game of blackjack.
public class BlackjackGUI extends PlayableGame {
    private JFrame window;                      // stores the frame for the window
    private JPanel table;                       // stores the table for the game components
    private JPanel playerCardPanel;             // stores the player's card images
    private JPanel dealerCardPanel;             // stores the dealer's card images
    private JButton hitButton;                  // button used for player's turn
    private JButton stayButton;                 // button used for ending player's turn
    private JLabel playerBetLabel;              // displays the player's bet
    private JLabel playerBankLabel;             // displays the player's bank
    private JLabel playerHandTotal;             // displays the player's hand total
    private JLabel dealerHandTotal;             // displays the dealer's hand total
    private BackgroundPanel backgroundPanel;    // creates the background wallpaper

    //EFFECTS: Runs the BlackjackGUI version of the game.
    public BlackjackGUI() {
        super();
        setup();
        runGame();
    }

    //MODIFIES: this
    //EFFECTS: sets up the everything for the game's panels, labels and frames initially
    // and initializes the player and dealer objects. Prompts the user if they would like to load a
    // previous game.
    private void setup() {
        window = new JFrame("Blackjack");
        backgroundPanel = new BackgroundPanel();
        window.setContentPane(backgroundPanel);
        table.setOpaque(false);
        playerCardPanel.setOpaque(false);
        dealerCardPanel.setOpaque(false);
        hitButton.setVisible(false);
        stayButton.setVisible(false);
        window.getContentPane().add(table);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.pack();
        loadPrompt();
    }

    //MODIFIES: this
    //EFFECTS: runs the game based off user decisions and data input
    @Override
    protected void runGame() {
        reset();
        hitButton.setVisible(true);
        stayButton.setVisible(true);
        hit();
        stay();
    }

    //REQUIRES: stay button to be clicked
    //MODIFIES: this
    //EFFECTS: ends the user's turn when the button is clicked. Plays out the rest of the game involving the dealer.
    private void stay() {
        stayButton.addActionListener(e -> {
            playDealer();
            checkWhoWon();
            gameOverPrompt();
        });
    }

    //REQUIRES: hit button to be clicked
    //MODIFIES: this
    //EFFECTS: plays the user's turn when the button is clicked. The goal is to get 21 or as close as possible
    // and if the user doesnt bust they can continue to play.
    private void hit() {
        hitButton.addActionListener(e -> {
            player.hit();
            if (player.handTotal() > 21 && player.hasAce()) {
                player.swapAce();
            }
            playerHandTotal.setText(player.getName() + "'s Hand total: " + player.handTotal());
            if (player.handTotal() > 21) {
                displayHands(false);
                playSound("lose");
                JOptionPane.showMessageDialog(null, "Bust!");
                player.subtractCash(playerBet);
                player.setBust(true);
                gameOverPrompt();
            }
            displayHands(false);
        });
    }

    //MODIFIES: this
    //EFFECTS: plays out the dealer's turn following the same rules as the player. If the dealer busts then the player
    // wins the bet.
    @Override
    protected void playDealer() {
        super.playDealer();
        dealerHandTotal.setText("Dealer Hand total: " + dealer.handTotal());
    }

    //MODIFIES: this
    //EFFECTS: asks if the user wants to continue playing another game or not. If not the user is requested if they
    // want to save or not and then the game closes. If the user is out of cash the game closes.
    private void gameOverPrompt() {
        if (player.getCash() <= 0) {
            playSound("push");
            JOptionPane.showMessageDialog(window,"Game over! Out of cash!");
            System.exit(0);
        }
        int playAgain;
        playAgain = JOptionPane.showConfirmDialog(window, "Did you want to play again?");
        if (playAgain == JOptionPane.NO_OPTION) {
            playAgain = JOptionPane.showConfirmDialog(window, "Would you like to save?");
            if (playAgain == JOptionPane.YES_OPTION) {
                saveGame();
            }
            System.exit(0);
        } else if (playAgain == JOptionPane.YES_OPTION) {
            reset();
        } else if (playAgain == JOptionPane.CANCEL_OPTION) {
            System.exit(0);
        }
    }

    //MODIFIES: this
    //EFFECTS: checks to see who won the game by having the higher hand without going over 21.
    //If both the user and dealer have the same total amount in their hands then its called a push and the bet
    // stays with the player. If the dealer busts then the player wins.
    @Override
    protected void checkWhoWon() {
        if (dealer.handTotal() > 21) {
            playSound("win");
            JOptionPane.showMessageDialog(null, "Dealer Busts!");
        } else if (dealer.handTotal() > player.handTotal()) {
            playSound("lose");
            JOptionPane.showMessageDialog(null, "Dealer wins!");
            player.subtractCash(playerBet);
        } else if (player.handTotal() > dealer.handTotal() && !(player.handTotal() > 21)) {
            playSound("win");
            JOptionPane.showMessageDialog(null, player.getName() + " wins!");
            player.addCash(playerBet);
        } else if (player.handTotal() == dealer.handTotal()) {
            playSound("push");
            JOptionPane.showMessageDialog(null, "Push!");
        }
    }

    //MODIFIES: this
    //EFFECTS: resets the game for another play through of Blackjack.
    @Override
    protected void reset() {
        placeBets();
        playerCardPanel.removeAll();
        dealerCardPanel.removeAll();
        dealerHandTotal.setText("");
        player.deal();
        dealer.deal();
        displayHands(false);
        playerBankLabel.setText("Bank: " + player.getCash());
        player.setBust(false);
        dealer.setBust(false);
        playerHandTotal.setText(player.getName() + "'s Hand total: " + player.handTotal());
        if (checkBlackjack()) {
            playSound("win");
            JOptionPane.showMessageDialog(window,"Blackjack! " + player.getName() + " wins " + playerBet + "!");
            player.addCash(playerBet);
            gameOverPrompt();
        }
    }

    //REQUIRES: the user must enter a valid bet. (within their bank and an integer)
    //MODIFIES: this
    //EFFECTS: asks the user what they would like to bet and places that bet.
    @Override
    protected void placeBets() {
        while (true) {
            try {
                playerBet = Integer.parseInt(JOptionPane.showInputDialog("Place your bet!"));
                if (playerBet < 1 || playerBet > player.getCash()) {
                    throw new RuntimeException();
                }
                playerBetLabel.setText("Player bet: " + playerBet);
                break;
            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(window, "Please place a valid bet.",
                        "Invalid bet!", JOptionPane.WARNING_MESSAGE);
                playerBet = 0;
            }
        }
    }

    //EFFECTS: displays the GUI version of the blackjack table for the
    // user depending on if its the user's turn or the dealer's.
    @Override
    protected void displayHands(boolean dealerTurn) {
        if (dealerTurn) {
            dealerCardPanel.remove(1);
            ArrayList<Card> dealerHand = dealer.getHand();
            for (int i = 1; i < dealerHand.size(); i++) {
                dealerCardPanel.add(createCard(dealerHand.get(i).toString()));
            }
        } else {
            //Initial dealer card panel, dont need to reload this everytime
            if (dealerCardPanel.getComponentCount() != 2) {
                ArrayList<Card> dealerHand = dealer.getHand();
                dealerCardPanel.add(createCard(dealerHand.get(0).toString()));
                dealerCardPanel.add(createCard("cardback"));
            }

            ArrayList<Card> playerHand = player.getHand();
            for (int i = playerCardPanel.getComponentCount(); i < playerHand.size(); i++) {
                playerCardPanel.add(createCard(playerHand.get(i).toString()));
            }
            playSound("card");
        }
        window.pack();
    }

    //EFFECTS: plays a sound clip given the parameter.
    private void playSound(String clip) {
        try {
            Clip sound = AudioSystem.getClip();
            sound.open(AudioSystem.getAudioInputStream(
                            new File("./data/soundClips/" + clip + ".wav").getAbsoluteFile()));
            sound.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            System.out.println(e.getMessage());
        }
    }

    //MODIFIES: this
    //EFFECTS: creates a JLabel from the given card value. If the value is 'cardback' then it creates the back-facing
    //card used in the dealer hand.
    private JLabel createCard(String value) {
        if (value.equals("cardback")) {
            return new JLabel(new ImageIcon(new ImageIcon("./data/cardImages/cardback.png")
                    .getImage().getScaledInstance(215, -1, Image.SCALE_SMOOTH)));
        } else {
            return new JLabel(new ImageIcon(new ImageIcon(getCardFileString(value))
                    .getImage().getScaledInstance(200, -1, Image.SCALE_SMOOTH)));
        }
    }

    //MODIFIES: this
    //EFFECTS: asks the user what they would like to load from a previous game.
    @Override
    protected void loadPrompt() {
        int load;
        load = JOptionPane.showConfirmDialog(window, "Would you like to load from your previous game?");
        if (load == JOptionPane.YES_OPTION) {
            loadGame();
        } else {
            player.setName(JOptionPane.showInputDialog("Enter your name!"));
        }
    }

    //EFFECTS: returns the file string of the passed card value.
    private String getCardFileString(String value) {
        return "./data/cardImages/" + value + "_of_" + Suit.getRandomSuit() + ".png";
    }

    // Represents a suit in a deck of cards.
    private enum Suit {
        SPADES, HEARTS, CLUBS, DIAMONDS;        // Different types of suits

        //EFFECTS: returns a random suit selection as a String.
        static String getRandomSuit() {
            Random random = new Random();
            Suit[] suits = Suit.values();
            return suits[random.nextInt(suits.length)].toString().toLowerCase();
        }
    }
}