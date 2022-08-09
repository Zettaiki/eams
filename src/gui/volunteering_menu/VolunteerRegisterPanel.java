package gui.volunteering_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import gui.GUI;
import utils.JComponentLoader;

public class VolunteerRegisterPanel extends JPanel {

	private static final long serialVersionUID = -8006458464106165342L;
    public final GridBagConstraints c;
	
	public VolunteerRegisterPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - Volunteer register");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Iscrizione volontario"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a2 = new JTextArea(1, 16);
        a2.setBorder(BorderFactory.createTitledBorder("Citta:"));
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a3 = new JTextArea(1, 16);
        a3.setBorder(BorderFactory.createTitledBorder("Data inscrizione:"));
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 4;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra volontario");
        b0.addActionListener(e -> {
	        // TODO
        });
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 5;
        var b1 = new JButton("Ritorna");
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteeringMenuPanel());
        });
        this.add(b1, c);
    }
}
