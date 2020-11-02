package ui;

import javax.swing.*;
import java.awt.*;

public class BlackjackGUI {
    JPanel panel;
    JFrame frame;

    public BlackjackGUI() {
        initialize();

        ImageIcon cardTest = new ImageIcon("./data/cardImages/10_of_spades.png");
        JLabel label = new JLabel(cardTest);
        frame.add(label);
        frame.pack();

    }

    public void initialize() {
        panel = new JPanel();
        frame = new JFrame();
        panel.setBackground(new Color(29, 102, 29));
        panel.setBorder(BorderFactory.createBevelBorder(1));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(1000,650));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new BlackjackGUI();
    }
}
