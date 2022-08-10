package gui;

import javax.swing.*;

import gui.events_menu.EventsMenuPanel;
import gui.project_menu.ProjectDonationMenuPanel;
import gui.volunteering_menu.VolunteeringMenuPanel;
import utils.JComponentLoader;

import java.awt.*;

public class MenuPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    
    public MenuPanel() {
        this.setName("EAMS - Main menu");
    	this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
    	this.setLayout(new BorderLayout());
    	
        // Titolo schermata
    	var a0 = new JPanel();
    	a0.setLayout(new BorderLayout());
    	{
    		// Titolo pannello
	        var panelTitle = new JLabel("Ecological Association Management System - Menu principale");
	        panelTitle.setHorizontalAlignment(SwingConstants.CENTER);
	        panelTitle.setFont(new Font("SansSerif", Font.BOLD, 20));
	        a0.add(panelTitle, BorderLayout.PAGE_START);
	        
	        // Titolo utente
	        var userTitle = new JLabel("Utente connesso: <inserire nome qua>");
	        // TODO: Aggiungere il nome dell'utente connesso.
	        userTitle.setHorizontalAlignment(SwingConstants.CENTER);
	        userTitle.setFont(new Font("SansSerif", Font.BOLD, 15));
	        a0.add(userTitle, BorderLayout.PAGE_END);
    	}
    	this.add(a0, BorderLayout.PAGE_START);
        
        // Menu principale
    	var b0 = new JPanel();
    	b0.setLayout(new GridBagLayout());
    	{
    	    final GridBagConstraints c = new GridBagConstraints();
    	    c.insets = new Insets(20, 50, 50, 20);
            c.fill = GridBagConstraints.HORIZONTAL;
    	    
    	    c.gridx = 0;
    	    c.gridy = 0;
    		var projectButton = new JButton("Area progetti e donazioni");
    		projectButton.addActionListener(e -> {
            	JFrame parent = JComponentLoader.getParentFrame(this);
                JComponentLoader.load(parent, new ProjectDonationMenuPanel());
            });
            b0.add(projectButton, c);
            
            c.gridx = 0;
            c.gridy = 1;
            var volunteeringButton = new JButton("Volontariato");
            volunteeringButton.addActionListener(e -> {
            	JFrame parent = JComponentLoader.getParentFrame(this);
                JComponentLoader.load(parent, new VolunteeringMenuPanel());
            });
            b0.add(volunteeringButton, c);
            
            c.gridx = 0;
            c.gridy = 2;
            var eventButton = new JButton("Eventi e servizi");
            eventButton.addActionListener(e -> {
            	JFrame parent = JComponentLoader.getParentFrame(this);
                JComponentLoader.load(parent, new EventsMenuPanel());
            });
            b0.add(eventButton, c);
            
            c.gridx = 0;
            c.gridy = 3;
            var newsletterButton = new JButton("Newsletter");
            newsletterButton.addActionListener(e -> {
            	// TODO
            });
            b0.add(newsletterButton, c);
    	}
    	this.add(b0, BorderLayout.CENTER);
    	
        // Logout
        var c0 = new JButton("Logout");
        c0.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
             JComponentLoader.load(parentFrame, new LoginPanel());
        });
        this.add(c0, BorderLayout.PAGE_END);
    }
}