package gui;

import utils.JComponentLoader;
import javax.swing.*;
import java.awt.*;

public class LoginPanel extends AbstractGridBagLayoutJPanel {
    private static final long serialVersionUID = 8475751505006519027L;

    public LoginPanel() {
        super("EAMS - Login", new Dimension(GUI.getMinScreenSize()*9/16, GUI.getMinScreenSize()*9/16));
        
        JPanel p0 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p0.add(new JLabel("Ecological Association Management System - Accesso utente"), c);
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(p0, c);
        
        // User name panel
        
        JPanel p1 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p1.add(new JLabel("Username:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t1 = new JTextField("", 10);
        
        p1.add(t1, c);
        
        c.gridx = 0;
        c.gridy = 1;
        this.add(p1, c);
        
        // Password panel
        
        JPanel p2 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p2.add(new JLabel("Password:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t2 = new JPasswordField(10);
        
        p2.add(t2, c);
        
        c.gridx = 0;
        c.gridy = 2;
        this.add(p2, c);
        
        // Login buttons
        
        JPanel p3 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        var b1 = new JButton("Login");
        b1.addActionListener(e -> {
        	JFrame parent = JComponentLoader.getParentFrame(this);
            JComponentLoader.load(parent, new MenuPanel());
        });
        p3.add(b1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        this.add(p3, c);
    }
}