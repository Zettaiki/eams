package gui.project_menu;

import javax.swing.*;
import java.awt.*;
import gui.GUI;
import utils.JComponentLoader;

public class InsertDonationPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public InsertDonationPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New donation");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Inserimento donazione"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Importo:"));
        this.add(a1, c);
        
        String[] proyectList = {"Progetto1", "Progetto2", "Progetto3"};
        
        c.gridx = 0;
        c.gridy = 3;
	    c.insets = new Insets(10, 0, 0, 10);
        var a2 = new JComboBox<String>(proyectList);
        a2.setSelectedIndex(0);
        a2.addActionListener(e -> {
	        // TODO
        });
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 4;
	    c.insets = new Insets(10, 0, 0, 10);
        var a3 = new JCheckBox("Donazione periodica");
        a3.setSelected(false);
        a3.addActionListener(e -> {
        	// TODO
        });
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 5;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra donazione");
        b0.addActionListener(e -> {
	        // TODO
        });
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
        this.add(b1, c);
    }
}