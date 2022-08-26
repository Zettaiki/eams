package gui.volunteering_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Properties;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import db.tables.OfficeTable;
import db.tables.PersonTable;
import db.tables.VolunteerTable;
import gui.GUI;
import gui.InsertPersonPanel;
import model.Volunteer;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class VolunteerRegisterPanel extends JPanel {

	private static final long serialVersionUID = -8006458464106165342L;
    public final GridBagConstraints c;
	
	public VolunteerRegisterPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - Volunteer register");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Iscrizione volontario"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a1, c);
        
        OfficeTable officeTable = new OfficeTable(ConnectionProvider.getMySQLConnection());
        Object[] officeList = officeTable.findAll().stream().map((x) -> x.getCittà()).collect(Collectors.toList()).toArray();
        
        c.gridx = 0;
        c.gridy = 2;
        var a2 = new JComboBox<Object>(officeList);
        a2.setBorder(BorderFactory.createTitledBorder("Citta:"));
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a3 = new JPanel();
        
	        SqlDateModel model = new SqlDateModel();
			Properties p = new Properties();
			p.put("text.today", "Today");
			p.put("text.month", "Month");
			p.put("text.year", "Year");
			JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
			JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
	        a3.add(datePicker);
	        a3.setBorder(BorderFactory.createTitledBorder("Data inscrizione:"));
        
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 4;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra volontario");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 5;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listeners
        
        b0.addActionListener(e -> {
	        PersonTable personTable = new PersonTable(ConnectionProvider.getMySQLConnection());
	        if(personTable.findByPrimaryKey(a1.getText()).isEmpty()) {
	        	JOptionPane.showMessageDialog(getParent(), "Utente non registrato.\nPer favore registrare l'utente.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertPersonPanel());
	        } else {
	        	Volunteer volunteer = new Volunteer(a1.getText(), a2.getSelectedItem().toString(), model.getValue());
	        	VolunteerTable volunteerTable = new VolunteerTable(ConnectionProvider.getMySQLConnection());
	        	if(!volunteerTable.save(volunteer)) {
	        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Error", JOptionPane.ERROR_MESSAGE);
	        	} else {
	        		JOptionPane.showMessageDialog(getParent(), "Volontario registrato", "Registro volontario", JOptionPane.INFORMATION_MESSAGE);
	        	}
	        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new VolunteeringMenuPanel());
	        }
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteeringMenuPanel());
        });
        
    }
}
