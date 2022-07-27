package gui;

import javax.swing.*;

import utils.JComponentLoader;

import java.awt.*;

public class MenuPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public MenuPanel() {
        super("EAMS - Main menu", new Dimension(GUI.getMinScreenSize()*12/16, GUI.getMinScreenSize()*9/16));
        
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
        
        JPanel menuPanel = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        var a0 = new JButton("Area progetti e donazioni");
        a0.addActionListener(e -> {
        	JFrame parent = JComponentLoader.getParentFrame(this);
            JComponentLoader.load(parent, new ProyectMenuPanel());
        });
        menuPanel.add(a0, c);
        
        c.gridx = 1;
        c.gridy = 0;
        var a1 = new JButton("Prodotti e vendite");
        a1.addActionListener(e -> {
        	// TODO
        });
        menuPanel.add(a1, c);
        
        c.gridx = 2;
        c.gridy = 0;
        var a2 = new JButton("Gestione rifiuti");
        a2.addActionListener(e -> {
        	// TODO
        });
        menuPanel.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 2;
        this.add(menuPanel, c);
        
        // Logout e bottoni finali
        
        c.gridx = 0;
        c.gridy = 3;
        var b0 = new JButton("Logout");
        b0.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
             JComponentLoader.load(parentFrame, new LoginPanel());
        });
        menuPanel.add(b0, c);
    }
}