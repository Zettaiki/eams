package gui.proyect_menu;

import javax.swing.*;
import java.awt.*;
import utils.JComponentLoader;

public class DonationPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;
    
    public DonationPanel() {
        this.c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
        // First column
        
        c.gridx = 0;
        c.gridy = 0;
        var a0 = new JLabel("Opzioni:");
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 1;
        var a1 = new JButton("Registrare donazione");
        a1.setLayout(null);
        a1.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new InsertDonationPanel());
        });
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a2 = new JButton("Rapporto donazione/progetto");
        a2.setLayout(null);
        a2.addActionListener(e -> {
        	// TODO
        });
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a3 = new JButton("Donatore maggior quantita");
        a3.setLayout(null);
        a3.addActionListener(e -> {
        	// TODO
        });
        this.add(a3, c);
        
        // Second column
        
        c.gridx = 1;
        c.gridy = 0;
        var b0 = new JLabel("Registro donazioni:");
        this.add(b0, c);
        
        c.gridx = 1;
        c.gridy = 1;
        c.gridheight = 5;
        var textArea = new JTextArea(10, 40);
        var b1 = new JScrollPane(textArea);
        textArea.setEditable(false);
        this.add(b1, c);
    }
}