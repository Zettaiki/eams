package gui;

import javax.swing.*;
import java.awt.*;

public class ProjectPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;
    
    public ProjectPanel() {
        this.c = new GridBagConstraints();
        this.setLayout(new GridBagLayout());
        
        // First column
        
        c.gridx = 0;
        c.gridy = 0;
        var a0 = new JLabel("Opzioni:");
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 1;
        var a1 = new JButton("Aggiungere progetto");
        a1.setLayout(null);
        a1.addActionListener(e -> {
        	// TODO
        });
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a2 = new JButton("Modificare stato progetto");
        a2.setLayout(null);
        a2.addActionListener(e -> {
        	// TODO
        });
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a3 = new JButton("Filtra progetti attivi");
        a3.setLayout(null);
        a3.addActionListener(e -> {
        	// TODO
        });
        this.add(a3, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a4 = new JButton("Cercare progetti");
        a4.setLayout(null);
        a4.addActionListener(e -> {
        	// TODO
        });
        this.add(a4, c);
        
        // Second column
        
        c.gridx = 1;
        c.gridy = 0;
        var b0 = new JLabel("Progetti registrati:");
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