package ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BlackjackGUI {
    private JButton buttonHit;
    private JPanel panelMain;
    private JButton buttonStay;
    private JLabel playerCard;
    private JFrame frame;

    public BlackjackGUI() {
        frame = new JFrame("Blackjack Game");
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeCards();
        frame.pack();
        frame.setVisible(true);

        buttonHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"It worked!");
            }
        });
    }

    private void initializeCards() {
        /*ImageIcon cardTest = new ImageIcon("./data/cardImages/10_of_spades.png");
        playerCard = new JLabel(cardTest);*/
    }

    public static void main(String[] args) {
        new BlackjackGUI();
    }

    private void createUIComponents() {
        playerCard = new JLabel(new ImageIcon("./data/cardImages/10_of_spades.png"));
    }
}
