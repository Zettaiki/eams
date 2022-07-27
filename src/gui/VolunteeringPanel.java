package gui;

import javax.swing.*;
import utils.JComponentLoader;
import java.awt.*;

public class VolunteeringPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public VolunteeringPanel() {
        super("EAMS - Volunteering", new Dimension(GUI.getMinScreenSize()*14/16, GUI.getMinScreenSize()*12/16));
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        this.add(new JLabel("Ecological Association Management System - Area volontariato"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        var a0 = new JLabel("Opzioni:");
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JButton("Registro volontario");
        a1.setLayout(null);
        a1.addActionListener(e -> {
        	// TODO
        });
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a2 = new JButton("Ricerca volontario");
        a2.setLayout(null);
        a2.addActionListener(e -> {
        	// TODO
        });
        this.add(a2, c);
        
        // Second column
        
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 2;
        var b0 = new JLabel("Lista volontari:");
        this.add(b0, c);
        
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 5;
        var textArea = new JTextArea(10, 40);
        var b1 = new JScrollPane(textArea);
        textArea.setEditable(false);
        this.add(b1, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 3;
        var c0 = new JButton("Ritorna al menu");
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
        this.add(c0, c);
    }
}