package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI {
    private JFrame frame;
    private JPanel cards;
    private JPanel window;
    private JButton changeCards;
    private ArrayList<JLabel> cardList;


    public GameGUI() {
        frame = new JFrame("Test");
        cardList = new ArrayList<>();
        for (int i = 2; i < 6; i++) {
            //cardList.add(new JLabel(new ImageIcon("./data/cardImages/" + i + "_of_spades.png")));
            cardList.add(new JLabel(new ImageIcon(new ImageIcon("./data/cardImages/"
                    + i + "_of_spades.png").getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH))));
        }
        for (JLabel i: cardList) {
            cards.add(i);
        }
        packThat();
        changeCards.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardList = new ArrayList<>();
                for (int i = 2; i < 6; i++) {
                    //cardList.add(new JLabel(new ImageIcon("./data/cardImages/" + i + "_of_spades.png")));
                    cardList.add(new JLabel(new ImageIcon(new ImageIcon("./data/cardImages/"
                            + i + "_of_hearts.png").getImage().getScaledInstance(250, -1, Image.SCALE_SMOOTH))));
                }
                cards.removeAll();
                for (JLabel i: cardList) {
                    cards.add(i);
                }
                packThat();
            }
        });
    }
    public static void main(String[] args) {
        new GameGUI();
    }
    private void packThat() {
        frame.setContentPane(window);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
