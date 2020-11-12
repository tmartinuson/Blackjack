package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BlackjackGUI {
    private JButton buttonHit;
    private JPanel panelMain;
    private JButton buttonStay;
    private JLabel playerCard;
    private ArrayList<JLabel> playerHand;
    private JPanel cards;
    private JFrame frame;

    public BlackjackGUI() {
        frame = new JFrame("Blackjack Game");
        playerHand = new ArrayList<>();
        frame.setContentPane(panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeCards();
        frame.add(cards);
        frame.pack();
        frame.setVisible(true);

        buttonHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showConfirmDialog(null,"It worked!");
            }
        });
        panelMain.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = panelMain.getWidth();
                int height = panelMain.getHeight();
                setIconImageSize(width,height);
            }
        });
    }

    private void initializeCards() {
        /*ImageIcon cardTest = new ImageIcon("./data/cardImages/10_of_spades.png");
        playerCard = new JLabel(cardTest);*/
        playerHand.add(new JLabel(new ImageIcon("./data/cardImages/10_of_spades.png")));
        playerHand.add(new JLabel(new ImageIcon("./data/cardImages/9_of_spades.png")));
        playerHand.add(new JLabel(new ImageIcon("./data/cardImages/8_of_spades.png")));
        for (JLabel i: playerHand) {
            cards.add(i);
        }
    }

    public static void main(String[] args) {
        new BlackjackGUI();
    }

    private void createUIComponents() {
        /*ImageIcon card = new ImageIcon("./data/cardImages/10_of_spades.png");
        Image cardTest = card.getImage();
        Image cardResize = getScaledImage(cardTest,150,250);
        card = new ImageIcon(cardResize);
        playerCard = new JLabel(card);*/
    }

    private void setIconImageSize(int width,int height) {
        //set player cards and dealer cards to the screen size
    }

    /*
    This is modeled off of multiple stack overflow posts for scaled images
     */
    private Image getScaledImage(Image image, int width, int height) {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D resize = newImage.createGraphics();
        resize.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        resize.drawImage(image,0,0,width,height,null);
        resize.dispose();
        return newImage;
    }
}
