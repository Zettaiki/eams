package gui.project_menu;

import javax.swing.*;
import java.awt.*;
import gui.AbstractGridBagLayoutJPanel;
import gui.GUI;
import utils.JComponentLoader;

public class InsertDonationPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public InsertDonationPanel() {
        super("EAMS - New donation", new Dimension(GUI.getMinScreenSize()*14/16, GUI.getMinScreenSize()*12/16));
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(new JLabel("Ecological Association Management System - Inserimento donazione"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Nome:"));
        this.add(a1, c);
        
        c.gridx = 1;
        c.gridy = 1;
        var a2 = new JTextArea(1, 16);
        a2.setBorder(BorderFactory.createTitledBorder("Cognome:"));
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a3 = new JTextArea(1, 16);
        a3.setBorder(BorderFactory.createTitledBorder("Telefono:"));
        this.add(a3, c);
        
        c.gridx = 1;
        c.gridy = 2;
        var a4 = new JTextArea(1, 16);
        a4.setBorder(BorderFactory.createTitledBorder("Indirizzo:"));
        this.add(a4, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a5 = new JTextArea(1, 16);
        a5.setBorder(BorderFactory.createTitledBorder("Citta:"));
        this.add(a5, c);
        
        c.gridx = 1;
        c.gridy = 3;
        var a6 = new JTextArea(1, 16);
        a6.setBorder(BorderFactory.createTitledBorder("Regione:"));
        this.add(a6, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a7 = new JTextArea(1, 16);
        a7.setBorder(BorderFactory.createTitledBorder("Codice postale:"));
        this.add(a7, c);
        
        c.gridx = 1;
        c.gridy = 4;
        var a8 = new JTextArea(1, 16);
        a8.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a8, c);
        
        String[] proyectList = {"Progetto1", "Progetto2", "Progetto3"};
        
        c.gridx = 1;
        c.gridy = 5;
        var a9 = new JComboBox<String>(proyectList);
        a9.setSelectedIndex(0);
        a9.addActionListener(e -> {
	        // TODO
        });
        this.add(a9, c);
        
        c.gridx = 0;
        c.gridy = 5;
        var a10 = new JCheckBox("Donazione periodica");
        a10.setSelected(false);
        a10.addActionListener(e -> {
        	// TODO
        });
        this.add(a10, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        var b0 = new JButton("Registra utente");
        b0.addActionListener(e -> {
	        // TODO
        });
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 7;
        var b1 = new JButton("Ritorna");
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectMenuPanel());
        });
        this.add(b1, c);
    }
}