package gui;

import utils.ConnectionProvider;
import utils.JComponentLoader;

import javax.swing.*;

import java.awt.*;

public class SettingsPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public SettingsPanel() {
        this.c = new GridBagConstraints();
        this.setName("EAMS - Connection");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());

        JPanel p0 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p0.add(new JLabel("Ecological Association Management System - Opzioni connessione database"), c);
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(p0, c);
        
        // Username panel
        
        JPanel p1 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p1.add(new JLabel("Username DB:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t1 = new JTextField(ConnectionProvider.getUSERNAME(), 10);
        
        p1.add(t1, c);
        
        c.gridx = 0;
        c.gridy = 1;
        this.add(p1, c);
        
        // Password panel
        
        JPanel p2 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p2.add(new JLabel("Password DB:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t2 =  new JTextField(ConnectionProvider.getPASSWORD(), 10);
        
        p2.add(t2, c);
        
        c.gridx = 0;
        c.gridy = 2;
        this.add(p2, c);
        
        // Port panel
        
        JPanel p3 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p3.add(new JLabel("Port DB:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t3 =  new JTextField(ConnectionProvider.getPORT(), 10);
        
        p3.add(t3, c);
        
        c.gridx = 0;
        c.gridy = 3;
        this.add(p3, c);
        
        // DBname panel
        
        JPanel p4 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        p4.add(new JLabel("Nome DB:"), c);
        
        c.gridx = 1;
        c.gridy = 0;
        JTextField t4 =  new JTextField(ConnectionProvider.getDBNAME(), 10);
        
        p4.add(t4, c);
        
        c.gridx = 0;
        c.gridy = 4;
        this.add(p4, c);
        
        // Final buttons
        
        JPanel p5 = new JPanel(new FlowLayout());
        
        c.gridx = 0;
        c.gridy = 0;
        var b1 = new JButton("Salva");
        b1.addActionListener((e) -> {
        	ConnectionProvider.setUSERNAME(t1.getText());
        	ConnectionProvider.setPASSWORD(t2.getText());
        	ConnectionProvider.setPORT(t3.getText());
        	ConnectionProvider.setDBNAME(t4.getText());

        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new LoginPanel());
        });
        p5.add(b1, c);
        
        c.gridx = 1;
        c.gridy = 0;
        var b2 = new JButton("Ritorna");
        b2.addActionListener((e) -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new LoginPanel());
        });
        p5.add(b2, c);
        
        c.gridx = 0;
        c.gridy = 5;
        this.add(p5, c);
    }
}