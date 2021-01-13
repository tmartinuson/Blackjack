package ui;

import javax.swing.*;
import java.awt.*;

// Represents a background panel object for storing the wallpaper in the frame.
public class BackgroundPanel extends JPanel {
    private ImageIcon background;               // Stores the image for the wallpaper

    //EFFECTS: Creates a JPanel object for the specific image to be
    // presented in the background of the game table.
    public BackgroundPanel() {
        super();
        background = new ImageIcon("./data/wallpaper/blue_felt.jpg");
    }

    //MODIFIES: this
    //EFFECTS: Draws the specific image given the size of the component as a wallpaper.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background.getImage(),0,0, getWidth(), getHeight(), this);
    }

    //EFFECTS: Returns the dimension for the wallpaper size.
    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int)screenSize.getWidth();
        int height = (int)screenSize.getHeight();
        return new Dimension(width,height);
    }
}
