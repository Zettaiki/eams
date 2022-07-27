package gui;

import javax.swing.*;
import utils.JComponentLoader;
import java.awt.*;

public class ProyectMenuPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public ProyectMenuPanel() {
        super("EAMS - Proyects and donations", new Dimension(GUI.getMinScreenSize()*14/16, GUI.getMinScreenSize()*12/16));
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Area progetti e donazioni"), c);
        
        JTabbedPane switchPane = new JTabbedPane();
        
        // Donation panel
        
        JComponent donationPanel = new DonationPanel();
        switchPane.addTab("Donazioni", donationPanel);
        
        // Proyect panel
        
        JComponent proyectPanel = new ProyectPanel();
        switchPane.addTab("Progetti", proyectPanel);
        
        c.gridx = 0;
        c.gridy = 1;
        this.add(switchPane,c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 2;
        var a0 = new JButton("Ritorna al menù");
        a0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
        this.add(a0, c);
    }
}