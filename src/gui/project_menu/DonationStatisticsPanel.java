package gui.project_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

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
import utils.TableExtractorUtils;

public class DonationStatisticsPanel extends JPanel {
	private static final long serialVersionUID = 5068702399412459352L;

	public DonationStatisticsPanel() {
		this.setName("EAMS - Statistics");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Rapporto progetti/donazioni");
        a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        // Centro
        
        var b0 = new JPanel();
        this.setLayout(new BorderLayout());
        
        	String[] options = {"Rapporto totale progetti", "Rapporto progetti attivi"};
        	var tablePicker = new JComboBox<String>(options);
            tablePicker.setBorder(BorderFactory.createTitledBorder("Selezionare tabella:"));
            b0.add(tablePicker, BorderLayout.PAGE_START);
            
	    	var volunteeringTable = new JTable(TableExtractorUtils.donationStatistics());
	    	volunteeringTable.setEnabled(false);
	    	volunteeringTable.getTableHeader().setReorderingAllowed(false);
	    	volunteeringTable.getTableHeader().setEnabled(false);
	    	JScrollPane volunteerScrollPane = new JScrollPane(volunteeringTable);
	    	volunteerScrollPane.setBorder(BorderFactory.createTitledBorder("Rapporto:"));
	    	b0.add(volunteerScrollPane, BorderLayout.CENTER);
	    	
    	this.add(b0, BorderLayout.CENTER);

        // End panel
        var c0 = new JButton("Ritorna");
        this.add(c0, BorderLayout.PAGE_END);
        
        // Action Listeners
        
        tablePicker.addActionListener(e -> {
        	if(tablePicker.getSelectedIndex() == 0) {
        		volunteeringTable.setModel(TableExtractorUtils.donationStatistics());
        	} else {
        		volunteeringTable.setModel(TableExtractorUtils.activeDonationStatistics());
        	}
        });
        
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
	}
}
