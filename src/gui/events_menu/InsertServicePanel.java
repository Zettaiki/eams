package gui.events_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.sql.Time;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import db.tables.EventTable;
import db.tables.ProjectTable;
import db.tables.ServiceTable;
import gui.GUI;
import model.Service;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertServicePanel extends JPanel {
	private static final long serialVersionUID = 6041435361378107113L;
	public final GridBagConstraints c;

	public InsertServicePanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New service");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Inserimento servizio"), c);
        
        // First column
        
        Object[] hourArray = IntStream.range(0, 24).boxed().collect(Collectors.toList()).toArray();
        Object[] minuteArray = IntStream.range(0, 60).boxed().collect(Collectors.toList()).toArray();
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JPanel();
        a0.setLayout(new GridLayout(1,0));
        		
        	var eventIdArea = new JTextArea(1, 10);
        	eventIdArea.setBorder(BorderFactory.createTitledBorder("ID evento:"));
	        a0.add(eventIdArea);
	        
	        var serviceIdArea = new JTextArea(1, 10);
        	serviceIdArea.setBorder(BorderFactory.createTitledBorder("ID servizio:"));
        	a0.add(serviceIdArea);
        	
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JPanel();
        a1.setBorder(BorderFactory.createTitledBorder("Ora inizio:"));
        a1.setLayout(new GridLayout(1,0));
        
        	var hourStartBox = new JComboBox<Object>(hourArray);
        	a1.add(hourStartBox);
        	
        	var spacingStartHour = new JLabel(":");
        	a1.add(spacingStartHour);
        	
        	var minuteStartBox = new JComboBox<Object>(minuteArray);
        	a1.add(minuteStartBox);
        
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a2 = new JPanel();
        a2.setBorder(BorderFactory.createTitledBorder("Ora fine:"));
        a2.setLayout(new GridLayout(1,0));
        
        	var hourFinishBox = new JComboBox<Object>(hourArray);
        	a2.add(hourFinishBox);
        	
        	var spacingFinishHour = new JLabel(":");
        	a2.add(spacingFinishHour);
        	
        	var minuteFinishBox = new JComboBox<Object>(minuteArray);
        	a2.add(minuteFinishBox);
        
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a3 = new JTextArea(1, 16);
        a3.setBorder(BorderFactory.createTitledBorder("Tipo servizio:"));
        this.add(a3, c);
        
        c.gridx = 0;
        c.gridy = 5;
        var a4 = new JPanel();
        a4.setLayout(new GridLayout(0,1));
        
        	var projectCheck = new JCheckBox("Servizio rivolto a progetto");
        	projectCheck.setSelected(false);
        	a4.add(projectCheck);
        	
        	var projectArea = new JTextArea(1,16);
        	projectArea.setBorder(BorderFactory.createTitledBorder("ID progetto:"));
        	projectArea.setEnabled(false);
            a4.add(projectArea);
        
        this.add(a4, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra servizio");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 7;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listeners
        
        projectCheck.addActionListener(e -> {
        	if(projectCheck.isSelected()) {
        		projectArea.setEnabled(true);
        	} else {
        		projectArea.setEnabled(false);
        	}
        });
        
        b0.addActionListener(e -> {
        	// Check hour and minutes
        	if((int) hourStartBox.getSelectedItem() >= (int) hourFinishBox.getSelectedItem()) {
        		JOptionPane.showMessageDialog(getParent(), "Errore: Ora inizio - fine scorretta.", "Hour error", JOptionPane.ERROR_MESSAGE);
        	} else {
	        	// Check ID event
		        EventTable eventTable = new EventTable(ConnectionProvider.getMySQLConnection());
		        if(eventTable.findByPrimaryKey(eventIdArea.getText()).isEmpty()) {
	        		JOptionPane.showMessageDialog(getParent(), "Errore: ID evento non esiste.", "Event ID error", JOptionPane.ERROR_MESSAGE);
		        } else {
		        	// Check ID project
		        	if(projectCheck.isSelected()) {
		        		ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());
		        		if(projectTable.findByPrimaryKey(Integer.parseInt(projectArea.getText())).isEmpty()) {
		            		JOptionPane.showMessageDialog(getParent(), "Errore: ID progetto non esiste.", "Project ID error", JOptionPane.ERROR_MESSAGE);
		        		} else {
		        			@SuppressWarnings("deprecation")
							Service service = new Service(
				        			eventIdArea.getText(), 
				        			serviceIdArea.getText(), 
				        			new Time((int) hourStartBox.getSelectedItem(), (int) minuteStartBox.getSelectedItem(), 0),
				        			new Time((int) hourFinishBox.getSelectedItem(), (int) minuteFinishBox.getSelectedItem(), 0),
				        			a3.getText(),
				        			Optional.of(Integer.parseInt(projectArea.getText()))
				        			);
		        			ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
		        			if(!serviceTable.save(service)) {
			            		JOptionPane.showMessageDialog(getParent(), "Errore: Dati sbagliati.", "Error", JOptionPane.ERROR_MESSAGE);
		        			} else {
		                		JOptionPane.showMessageDialog(getParent(), "Servizio registrato!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
		                		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		            	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
		        			}
		        		}
		        		
		        	} else {
		        		@SuppressWarnings("deprecation")
						Service service = new Service(
			        			eventIdArea.getText(), 
			        			serviceIdArea.getText(), 
			        			new Time((int) hourStartBox.getSelectedItem(), (int) minuteStartBox.getSelectedItem(), 0),
			        			new Time((int) hourFinishBox.getSelectedItem(), (int) minuteFinishBox.getSelectedItem(), 0),
			        			a3.getText()
			        			);
		        		ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	        			if(!serviceTable.save(service)) {
		            		JOptionPane.showMessageDialog(getParent(), "Errore: Dati sbagliati.", "Error", JOptionPane.ERROR_MESSAGE);
	        			} else {
	                		JOptionPane.showMessageDialog(getParent(), "Servizio registrato!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	                		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	            	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
	        			}
		        	}
		        }
        	}
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
        });
    }
}
