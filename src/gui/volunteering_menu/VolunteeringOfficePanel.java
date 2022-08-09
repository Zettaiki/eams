package gui.volunteering_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.GUI;
import utils.JComponentLoader;

public class VolunteeringOfficePanel extends JPanel {
	private static final long serialVersionUID = 1697847872341064830L;
	
	public VolunteeringOfficePanel() {
        this.setName("EAMS - Office volunteering lists");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Ricerca volontariato per sedi");
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
        	c.fill = GridBagConstraints.HORIZONTAL;
        	var b1 = new JPanel();
            b1.setBorder(BorderFactory.createTitledBorder("Scelta sede:"));
            b1.setLayout(new GridLayout(1,2));
            {
                String[] officeList = {"Forli", "Cesena", "Rimini"};
            	
                var officePicker = new JComboBox<String>(officeList);
                officePicker.setSelectedIndex(0);
                officePicker.addActionListener(e -> {
                	// TODO
                });
                b1.add(officePicker);
                
                var officeFilter = new JButton("Cerca");
                officeFilter.setLayout(null);
                officeFilter.addActionListener(e -> {
                	// TODO
                });
                b1.add(officeFilter);
                
                var mostVolunteersOffice = new JButton("Sede piu attiva");
                mostVolunteersOffice.setLayout(null);
                mostVolunteersOffice.addActionListener(e -> {
                	// TODO
                });
                b1.add(mostVolunteersOffice);
            }
            b0.add(b1, c);
            
            // Second column
            c.gridx = 0;
            c.gridy = 1;
            c.fill = GridBagConstraints.VERTICAL;
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Volontari registrati nella sede scelta:"));
    		b2.setLayout(new GridLayout(0,1));
    		{
    			String[] columnNames = {"Codice fiscale",
                        "Sede",
                        "Data inscrizione"};
            	
            	Object[][] data = {
            		    {"-", "-", "-"}
            		};
            	
            	var volunteeringTable = new JTable(data, columnNames);
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
        var c0 = new JButton("Ritorna all'area volontariato");
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteeringMenuPanel());
        });
        this.add(c0, BorderLayout.PAGE_END);
    }
}
