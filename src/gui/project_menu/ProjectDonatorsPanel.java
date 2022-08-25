package gui.project_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

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

import db.tables.ProjectTable;
import gui.GUI;
import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

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
        
        // Upper panel
        var b0 = new JPanel();
        b0.setLayout(new BorderLayout());
        
	     	// Upper buttons
	    	var b1 = new JPanel();
	        b1.setBorder(BorderFactory.createTitledBorder("Scelta progetto:"));
	        b1.setLayout(new GridLayout(1,0));
	        
	        	ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());
	        	List<String> projectFilterList = projectTable.findAll().stream().map((x) -> x.getObbiettivo()).collect(Collectors.toList());
	        	Object[] projectList = projectFilterList.toArray();
	        	
	            var projectPicker = new JComboBox<Object>(projectList);
	            projectPicker.setSelectedIndex(0);
	            b1.add(projectPicker);
	            
	            var projectSearch = new JButton("Cerca");
	            b1.add(projectSearch);
	        
	        b0.add(b1, BorderLayout.PAGE_START);
    	
        	// Table
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Donatori progetto scelto:"));
    		b2.setLayout(new GridLayout(0,1));
            	
            	var donatorsTable = new JTable(TableExtractorUtils.donationTable());
            	donatorsTable.setEnabled(false);
            	donatorsTable.getTableHeader().setReorderingAllowed(false);
            	donatorsTable.getTableHeader().setEnabled(false);
            	JScrollPane projectListPanel = new JScrollPane(donatorsTable);
                b2.add(projectListPanel);
    		
            b0.add(b2, BorderLayout.CENTER);
            
        this.add(b0, BorderLayout.CENTER);
        
        // End panel
        var c0 = new JButton("Ritorna all'area progetti e donazioni");
        this.add(c0, BorderLayout.PAGE_END);
        
        // Action listeners
        projectSearch.addActionListener(e -> {
        	donatorsTable.setModel(TableExtractorUtils.projectDonatorsQuery(projectPicker.getSelectedIndex()+1));
        });
        
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
    }
}
