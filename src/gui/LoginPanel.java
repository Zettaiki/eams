package gui;

import utils.ConnectionProvider;
import utils.JComponentLoader;
import javax.swing.*;

import db.tables.UserTable;
import model.User;

import java.awt.*;
import java.util.Optional;

public class LoginPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public LoginPanel() {
        this.c = new GridBagConstraints();
        this.setName("EAMS - Login");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());

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
        b1.addActionListener((e) -> {
        	String username = t1.getText();
        	String password = t2.getText();
        	
        	UserTable userTable = new UserTable(ConnectionProvider.getMySQLConnection());
        	Optional<User> userData = userTable.findByUsername(username);
        	
        	if(!userData.isEmpty() && username.equals(userData.get().getUsername()) && password.equals(userData.get().getPassword())) {
        		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        		MenuPanel.setLoggedUser(userData.get());
		        JComponentLoader.load(parentFrame, new MenuPanel());
		        
        	} else {
        		JOptionPane.showMessageDialog(getParent(), "Utente o password sbagliati.", "Login error", JOptionPane.ERROR_MESSAGE);
        	}	
        });
        p3.add(b1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        this.add(p3, c);
    }
}