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
        
        var b0 = new JPanel();
        b0.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        {
        	// First column
        	c.gridx = 0;
        	c.gridy = 0;
        	c.fill = GridBagConstraints.VERTICAL;
        	var b1 = new JPanel();
            b1.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
            b1.setLayout(new GridLayout(0,1));
            {
                var registerButton = new JButton("Registro volontario");
                registerButton.setLayout(null);
                registerButton.addActionListener(e -> {
                	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        	        JComponentLoader.load(parentFrame, new VolunteerRegisterPanel());
                });
                b1.add(registerButton);
                
                var officeFilter = new JButton("Controllo per sedi");
                officeFilter.setLayout(null);
                officeFilter.addActionListener(e -> {
                	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        	        JComponentLoader.load(parentFrame, new VolunteeringOfficePanel());
                });
                b1.add(officeFilter);

                var mostActiveButton = new JButton("Volontario piu attivo");
                mostActiveButton.setLayout(null);
                mostActiveButton.addActionListener(e -> {
                	// TODO
                });
                b1.add(mostActiveButton);
            }
            b0.add(b1, c);
            
            // Second column
            c.gridx = 1;
            c.gridy = 0;
            c.gridwidth = 4;
            c.fill = GridBagConstraints.VERTICAL;
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Volontari registrati:"));
    		b2.setLayout(new GridLayout(0,1));
    		{
            	var volunteeringTable = new JTable(TableExtractorUtils.volunteerTable());
            	volunteeringTable.setEnabled(false);
            	volunteeringTable.getTableHeader().setReorderingAllowed(false);
            	volunteeringTable.getTableHeader().setEnabled(false);
            	JScrollPane volunteeringListPanel = new JScrollPane(volunteeringTable);
                b2.add(volunteeringListPanel);
    		}
            b0.add(b2, c);
        }
        this.add(b0, BorderLayout.CENTER);
        
        // End panel
        var c0 = new JButton("Ritorna al menu");
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
        this.add(c0, BorderLayout.PAGE_END);
    }
}