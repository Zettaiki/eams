package gui.events_menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GarbageMenuPanel extends JPanel {
	private static final long serialVersionUID = -7665078034792655476L;
	public final GridBagConstraints c;
	
	public GarbageMenuPanel() {
		this.c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		// Titolo schermata      
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(new JLabel("Ecological Association Management System - Sezione raccolta rifiuti"), c);
        
        // First column
        
        var a0 = new JPanel();
        a0.setLayout(new GridBagLayout());
        a0.setBorder(BorderFactory.createTitledBorder("Registro raccolta rifiuti:"));
        c.gridwidth = 1;
        {
        	c.gridx = 0;
            c.gridy = 0;
            
            var serviceTextbox = new JTextArea(1, 16);
            serviceTextbox.setBorder(BorderFactory.createTitledBorder("ID servizio:"));
            a0.add(serviceTextbox, c);
            
            c.gridx = 0;
            c.gridy = 1;
            var eventTextbox = new JTextArea(1, 16);
            eventTextbox.setBorder(BorderFactory.createTitledBorder("ID evento:"));
            a0.add(eventTextbox, c);
            
            c.gridx = 0;
            c.gridy = 2;
            var materialTextbox = new JTextArea(1, 16);
            materialTextbox.setBorder(BorderFactory.createTitledBorder("Materiale:"));
            a0.add(materialTextbox, c);
            
            c.gridx = 0;
            c.gridy = 3;
            var massTextbox = new JTextArea(1, 16);
            massTextbox.setBorder(BorderFactory.createTitledBorder("Kg raccolti:"));
            a0.add(massTextbox, c);
            
            c.gridx = 0;
            c.gridy = 4;
    	    c.insets = new Insets(10, 0, 10, 0);
            c.fill = GridBagConstraints.HORIZONTAL;
            var registerButton = new JButton("Registra evento");
            registerButton.addActionListener(e -> {
    	        // TODO
            });
            a0.add(registerButton, c);
        }
        c.gridx = 0;
        c.gridy = 1;
	    c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        this.add(a0, c);

        // Second column
        
        var a1 = new JPanel();
        a1.setLayout(new GridBagLayout());
        a1.setBorder(BorderFactory.createTitledBorder("Altre operazioni:"));
        {
        	c.gridx = 0;
            c.gridy = 0;
        	var garbageStatisticsButton = new JButton("Media annuale rifiuti \nraccolti");
        	garbageStatisticsButton.addActionListener(e -> {
    	        // TODO
            });
            a1.add(garbageStatisticsButton, c);
        }
        c.gridx = 1;
        c.gridy = 1;
        c.fill = GridBagConstraints.BOTH;
        this.add(a1, c);
    }
}
