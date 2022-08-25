package gui.newsletter_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.GUI;
import gui.MenuPanel;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class NewsletterMenuPanel extends JPanel {
	private static final long serialVersionUID = -4421801245124223386L;
	
    public NewsletterMenuPanel() {
        this.setName("EAMS - Volunteering");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Newsletter");
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
            
                var registerButton = new JButton("Aggiungere newsletter");
                b1.add(registerButton);
                
                var newsletterRankingButton = new JButton("Classifica newsletter");
                b1.add(newsletterRankingButton);
            
            b0.add(b1, BorderLayout.PAGE_START);
            
            // Bottom panel
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Newsletter:"));
    		b2.setLayout(new GridLayout(0,1));
    		
            	var volunteeringTable = new JTable(TableExtractorUtils.newsletterTable());
            	volunteeringTable.setEnabled(false);
            	volunteeringTable.getTableHeader().setReorderingAllowed(false);
            	volunteeringTable.getTableHeader().setEnabled(false);
            	JScrollPane volunteeringListPanel = new JScrollPane(volunteeringTable);
                b2.add(volunteeringListPanel);
    		
            b0.add(b2, BorderLayout.CENTER);
        
        this.add(b0, BorderLayout.CENTER);
        
        // End panel
        var c0 = new JButton("Ritorna al menu");
        this.add(c0, BorderLayout.PAGE_END);
        
        // Action listener
        registerButton.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new InsertNewsletterPanel());
        });
        
        newsletterRankingButton.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new NewsletterRankingPanel());
        });
        
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
    }
}
