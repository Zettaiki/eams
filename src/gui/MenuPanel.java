package gui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public MenuPanel() {
        super("EAMS - Main menu", new Dimension(GUI.getMinScreenSize()*9/16, GUI.getMinScreenSize()*9/16));
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Menu principale"), c);
        
        c.gridx = 0;
        c.gridy = 1;
        /*
         * TODO: Aggiungere il nome dell'utente connesso.
         */
        this.add(new JLabel("Utente connesso: <inserire nome qua>"), c);
        
        // Menu principale
        
        JPanel menuPanel = new JPanel(new GridLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        var b0 = new JButton("Area donazioni");
        b0.addActionListener(e -> {
        	// TODO
        });
        menuPanel.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 1;
        var b1 = new JButton("Prodotti e vendite");
        b1.addActionListener(e -> {
        	// TODO
        });
        menuPanel.add(b1, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var b2 = new JButton("Gestione rifiuti");
        b2.addActionListener(e -> {
        	// TODO
        });
        menuPanel.add(b2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        this.add(menuPanel, c);
    }
}