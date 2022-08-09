package gui.project_menu;

import javax.swing.*;
import java.awt.*;
import gui.GUI;
import utils.JComponentLoader;

public class InsertProjectPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public InsertProjectPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New project");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(new JLabel("Ecological Association Management System - Inserimento donazione"), c);
        
        // First column
        
        var a1 = new JPanel();
        {
        	c.gridx = 0;
            c.gridy = 0;
            c.gridwidth = 1;
            var dayLabel = new JLabel("Giorno: ");
            a1.add(dayLabel, c);
        	
        	c.gridx = 1;
            c.gridy = 0;
            var dayTextArea = new JTextArea(1,2);
            dayTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
            a1.add(dayTextArea, c);
            
            c.gridx = 0;
            c.gridy = 1;
            c.gridwidth = 1;
            var monthLabel = new JLabel("Mese: ");
            a1.add(monthLabel, c);
            
            c.gridx = 1;
            c.gridy = 1;
            var monthTextArea = new JTextArea(1,2);
            monthTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
            a1.add(monthTextArea, c);
            
            c.gridx = 0;
            c.gridy = 2;
            c.gridwidth = 1;
            var yearLabel = new JLabel("Anno: ");
            a1.add(yearLabel, c);
            
            c.gridx = 1;
            c.gridy = 2;
            var yearTextArea = new JTextArea(1,4);
            yearTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
            a1.add(yearTextArea, c);
        }
        c.gridx = 0;
        c.gridy = 1;
        a1.setBorder(BorderFactory.createTitledBorder("Data inizio:"));
        this.add(a1, c);
        
        var a2 = new JPanel();
        {
        	c.gridx = 0;
            c.gridy = 2;
            var monthLabel = new JLabel("Durata mesi: ");
            a2.add(monthLabel, c);
            
            c.gridx = 1;
            c.gridy = 2;
            var monthTextArea = new JTextArea(1, 2);
            monthTextArea.setBorder(BorderFactory.createLineBorder(Color.black));
            a2.add(monthTextArea, c);
        }
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        a2.setBorder(BorderFactory.createTitledBorder(""));
        this.add(a2, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 2;
        c.fill = GridBagConstraints.NONE;
        var a3 = new JTextArea(5, 25);
        a3.setBorder(BorderFactory.createTitledBorder("Obiettivo progetto:"));
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.gridheight = 1;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Crea progetto");
        b0.addActionListener(e -> {
	        // TODO
        });
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        c.gridheight = 1;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectMenuPanel());
        });
        this.add(b1, c);
    }
}