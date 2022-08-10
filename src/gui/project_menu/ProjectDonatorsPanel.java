package gui.project_menu;

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

public class ProjectDonatorsPanel extends JPanel {

	private static final long serialVersionUID = 6590467329734162171L;

	public ProjectDonatorsPanel() {
        this.setName("EAMS - Project donators lists");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Ricerca donatori progetto");
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
            b1.setBorder(BorderFactory.createTitledBorder("Scelta progetto:"));
            b1.setLayout(new GridLayout(1,2));
            {
                String[] projectList = {"Progetto1", "Progetto2", "Progetto3"};
            	
                var projectPicker = new JComboBox<String>(projectList);
                projectPicker.setSelectedIndex(0);
                projectPicker.addActionListener(e -> {
                	// TODO
                });
                b1.add(projectPicker);
                
                var projectSearch = new JButton("Cerca");
                projectSearch.setLayout(null);
                projectSearch.addActionListener(e -> {
                	// TODO
                });
                b1.add(projectSearch);
            }
            b0.add(b1, c);
            
            // Second column
            c.gridx = 0;
            c.gridy = 1;
            c.fill = GridBagConstraints.VERTICAL;
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Donatori progetto scelto:"));
    		b2.setLayout(new GridLayout(0,1));
    		{
    			String[] columnNames = {"ID",
    		            "Importo",
    		            "Codice fiscale",
    		            "Data donazione",
    		            "ID progetto"};
            	
            	Object[][] data = {
            		    {"-", "-", "-", "-", "-"}
            		};
            	
            	var donatorsTable = new JTable(data, columnNames);
            	donatorsTable.setEnabled(false);
            	donatorsTable.getTableHeader().setReorderingAllowed(false);
            	donatorsTable.getTableHeader().setEnabled(false);
            	JScrollPane projectListPanel = new JScrollPane(donatorsTable);
                b2.add(projectListPanel);
    		}
            b0.add(b2, c);
        }
        this.add(b0, BorderLayout.CENTER);
        
        // End panel
        var c0 = new JButton("Ritorna all'area progetti e donazioni");
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
        this.add(c0, BorderLayout.PAGE_END);
    }
}
