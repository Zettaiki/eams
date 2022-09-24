package gui.project_menu;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import db.tables.DonationTable;
import db.tables.MemberCardTable;
import db.tables.PersonTable;
import db.tables.ProjectTable;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Collectors;

import gui.GUI;
import gui.InsertPersonPanel;
import model.Donation;
import model.MemberCard;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertDonationPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public InsertDonationPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New donation");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Inserimento donazione"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Importo:"));
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a2 = new JPanel();
        
	        SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	        a2.add(datePicker);
	        a2.setBorder(BorderFactory.createTitledBorder("Data donazione:"));
	        
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a3 = new JPanel();
        a3.setLayout(new GridLayout(1,0));
        
        	var projectCheck = new JCheckBox("Donazione rivolta a progetto");
        	projectCheck.setSelected(false);
        	a3.add(projectCheck);
        
	        ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());
	        Object[] projectList = projectTable.findAll().stream().map((x) -> x.getIdProgetto()).collect(Collectors.toList()).toArray();
	        
	        var projectPicker = new JComboBox<Object>(projectList);
	        projectPicker.setBorder(BorderFactory.createTitledBorder("ID progetto:"));
	        projectPicker.setEnabled(false);
	        projectPicker.setSelectedIndex(0);
	        a3.add(projectPicker);
	        
	    c.insets = new Insets(10, 0, 0, 10);
        this.add(a3, c);
        
        c.gridx = 0;
        c.gridy = 5;
	    c.insets = new Insets(10, 0, 0, 10);
        var a4 = new JCheckBox("Donazione periodica");
        a4.setSelected(false);
        this.add(a4, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra donazione");
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
    			projectPicker.setEnabled(true);
    		} else {
    			projectPicker.setEnabled(false);
    		}
    	});
        
        b0.addActionListener(e -> {
	        PersonTable personTable = new PersonTable(ConnectionProvider.getMySQLConnection());
	        // Check if user is registered
	        if(personTable.findByPrimaryKey(a0.getText()).isEmpty()) {
	        	JOptionPane.showMessageDialog(getParent(), "Utente non registrato.\nPer favore registrare l'utente.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertPersonPanel());
	        } else {
	        	// Check if the donation is for a project
	        	Donation donation;
	        	String memberShipInfo = "";
        		MemberCardTable memberCardTable = new MemberCardTable(ConnectionProvider.getMySQLConnection());
				MemberCard memberCard = new MemberCard(null, a0.getText(), model.getValue());
	        	
	        	if(projectCheck.isSelected()) {
	        		// Check if the donation is periodic
	        		if(a4.isSelected()) {
	        			// Check if the donator is a member
	        			if(memberCardTable.findByCodiceFiscale(a0.getText()).isEmpty()) {
	        				memberCardTable.save(memberCard);
	        				memberShipInfo = "\nUtente registrato come socio.";
	        			}    			
    	        		donation = new Donation(null, BigDecimal.valueOf(Double.parseDouble(a1.getText())), a0.getText(), model.getValue(), "periodica", Optional.of(Integer.parseInt(projectPicker.getSelectedItem().toString())));
	        		} else {
		        		donation = new Donation(null, BigDecimal.valueOf(Double.parseDouble(a1.getText())), a0.getText(), model.getValue(), "singola", Optional.of(Integer.parseInt(projectPicker.getSelectedItem().toString())));
	        		}
	        	} else {
	        		// Check if the donation is periodic
	        		if(a4.isSelected()) {
	        			// Check if the donator is a member
	        			if(memberCardTable.findByCodiceFiscale(a0.getText()).isEmpty()) {
	        				memberCardTable.save(memberCard);
	        				memberShipInfo = "\nUtente registrato come socio.";
	        			}    			
    	        		donation = new Donation(null, BigDecimal.valueOf(Double.parseDouble(a1.getText())), a0.getText(), model.getValue(), "periodica", Optional.empty());
	        		} else {
		        		donation = new Donation(null, BigDecimal.valueOf(Double.parseDouble(a1.getText())), a0.getText(), model.getValue(), "singola", Optional.empty());
	        		}
	        	}
	        	
	        	DonationTable donationTable = new DonationTable(ConnectionProvider.getMySQLConnection());
	        	if(!donationTable.save(donation)) {
	        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Error", JOptionPane.ERROR_MESSAGE);
	        	} else {
	        		JOptionPane.showMessageDialog(getParent(), "Donazione registrata." + memberShipInfo, "Risultato donazione", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        	
        		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
	        };
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
    }
}