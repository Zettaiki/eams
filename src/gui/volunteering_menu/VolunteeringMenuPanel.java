package gui.volunteering_menu;

import javax.swing.*;
import java.awt.*;

import gui.GUI;
import gui.MenuPanel;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class VolunteeringMenuPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    
    public VolunteeringMenuPanel() {
        this.setName("EAMS - Volunteering");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Area volontariato");
        a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        // Upper panel
        var b0 = new JPanel();
        b0.setLayout(new BorderLayout());
        
        	// Upper buttons
        	var b1 = new JPanel();
	        b1.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
	        b1.setLayout(new GridLayout(1,0));
	        
		        var registerButton = new JButton("Registro volontario");
		        b1.add(registerButton);
		        
		        var officeFilter = new JButton("Controllo per sedi");
		        b1.add(officeFilter);
		
		        var mostActiveButton = new JButton("Volontario piu attivo");
		        b1.add(mostActiveButton);
	            
		b0.add(b1, BorderLayout.PAGE_START);
	        
        // Bottom panel
        var b2 = new JPanel();
		b2.setBorder(BorderFactory.createTitledBorder("Volontari registrati:"));
		b2.setLayout(new GridLayout(0,1));
		
        	var volunteeringTable = new JTable(TableExtractorUtils.volunteerTable());
        	volunteeringTable.setEnabled(false);
        	volunteeringTable.getTableHeader().setReorderingAllowed(false);
        	volunteeringTable.getTableHeader().setEnabled(false);
        	JScrollPane volunteeringListPanel = new JScrollPane(volunteeringTable);
        
        b0.add(volunteeringListPanel, BorderLayout.CENTER);
			
	    this.add(b0, BorderLayout.CENTER);
                
        // End panel
        var c0 = new JButton("Ritorna al menu");
        this.add(c0, BorderLayout.PAGE_END);
        
        // Action listener
        
        registerButton.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteerRegisterPanel());
        });
        
        officeFilter.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteeringOfficePanel());
        });
        
        mostActiveButton.addActionListener(e -> {
        	// TODO
        });
        
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
    }
}