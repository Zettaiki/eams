package gui;

import utils.JComponentLoader;
import javax.swing.*;
import java.awt.*;

/**
 * Main class to start the GUI process.
 */
public class GUI extends JFrame {
	
    private static final long serialVersionUID = 8605107955425262259L;
	
    /**
     * Initializes and shows the main menu JPanel.
     */
    public GUI() {
    	JComponentLoader.load(this, new LoginPanel());
        this.setLocationRelativeTo(null); // Center the window on the screen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
    }

    /**
     * Method used to get the screen dimensions.
     * @return The size of the screen in pixels.
     */
    public static Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Method used to get minimum safe screen dimension that's visible on the screen.
     * @return The minimum safe screen dimension.
     */
    public static int getMinScreenSize() {
        final Dimension screenSize = GUI.getScreenSize();
        return Math.min(screenSize.width, screenSize.height) * 8/10;
    }

}
