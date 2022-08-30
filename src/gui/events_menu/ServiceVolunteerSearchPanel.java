package gui.events_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
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

import db.tables.ServiceTable;
import gui.GUI;
import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class ServiceVolunteerSearchPanel extends JPanel {
	private static final long serialVersionUID = 6969078911943056979L;
	
	public ServiceVolunteerSearchPanel() {
		this.setName("EAMS - Service participation");
	    this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
	    this.setLayout(new BorderLayout());
	    
	    // Titolo schermata
	    var a0 = new JLabel("Ecological Association Management System - Partecipazione volontari");
	    a0.setHorizontalAlignment(SwingConstants.CENTER);
		a0.setFont(new Font("SansSerif", Font.BOLD, 20));
	    this.add(a0, BorderLayout.PAGE_START);
	    
	    // Centro
	    var a1 = new JPanel();
	    a1.setLayout(new BorderLayout());
	    	
		   	ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	        Object[] serviceList = serviceTable.findAll().stream().map((x) -> x.getIdServizio()).collect(Collectors.toList()).toArray();
        
		    var servicePicker = new JComboBox<Object>(serviceList);
			servicePicker.setBorder(BorderFactory.createTitledBorder("Select service:"));
		    a1.add(servicePicker, BorderLayout.PAGE_START);
		    
			var volunteeringTable = new JTable(TableExtractorUtils.volunteerTable());
			volunteeringTable.setEnabled(false);
			volunteeringTable.getTableHeader().setReorderingAllowed(false);
			volunteeringTable.getTableHeader().setEnabled(false);
			JScrollPane volunteeringPanel = new JScrollPane(volunteeringTable);
			a1.add(volunteeringPanel, BorderLayout.CENTER);
		
		this.add(a1, BorderLayout.CENTER);
	
	    // End panel
	    var c0 = new JButton("Ritorna al pannello eventi");
	    this.add(c0, BorderLayout.PAGE_END);
	    
	    // Action listeners
	    servicePicker.addActionListener(e -> {
	    	volunteeringTable.setModel(TableExtractorUtils.volunteerInService(servicePicker.getSelectedItem().toString()));
	    });
	    
	    c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
	    });
	}
}

