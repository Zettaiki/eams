package gui.project_menu;

import javax.swing.*;

import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import db.tables.ProjectTable;

import java.awt.*;
import java.util.Optional;
import java.util.Properties;

import gui.GUI;
import model.Project;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertProjectPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public InsertProjectPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New project");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel("Ecological Association Management System - Inserimento donazione"), c);
        
        // First column
        c.gridx = 0;
        c.gridy = 1;
        var a1 = new JTextArea(1, 20);
        c.fill = GridBagConstraints.BOTH;
        a1.setBorder(BorderFactory.createTitledBorder("Nome progetto:"));
        this.add(a1, c);
        
        
        var a2 = new JPanel();
        a2.setLayout(new GridLayout());
        
        var datePickerPanel = new JPanel();
        datePickerPanel.setLayout(new GridLayout(1,0));
	        
    	var textDatePicker = new JLabel("Data inizio:");
    	datePickerPanel.add(textDatePicker);
	    
    	SqlDateModel model = new SqlDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        datePickerPanel.add(datePicker);
        
        a2.add(datePickerPanel);

        var duringDays = new JPanel();
        duringDays.setLayout(new GridLayout());
	        
		var textDuringDays = new JLabel("Durata progetto:");
		duringDays.add(textDuringDays);
		
		var textAreaDuringDays = new JTextArea(1,3);
	    textAreaDuringDays.setBorder(BorderFactory.createTitledBorder(""));
		duringDays.add(textAreaDuringDays);
	        
        a2.add(duringDays);
        
        c.gridx = 0;
        c.gridy = 2;
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 3;
        c.gridheight = 2;
        var a3 = new JTextArea(2, 20);
        c.fill = GridBagConstraints.BOTH;
        a3.setBorder(BorderFactory.createTitledBorder("Obiettivo progetto:"));
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 5;
        c.gridheight = 1;
	    c.insets = new Insets(10, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Crea progetto");
        b0.addActionListener(e -> {
	        Project progetto = new Project(null, a1.getText(), model.getValue(), Integer.parseInt(textAreaDuringDays.getText()), Optional.of(a3.getText()));
	        ProjectTable table = new ProjectTable(ConnectionProvider.getMySQLConnection());
	        if(!table.save(progetto)) {
        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Project error", JOptionPane.ERROR_MESSAGE);
	        } else {
        		JOptionPane.showMessageDialog(getParent(), "Progetto registrato!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	        };
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonationMenuPanel());
        });
        this.add(b1, c);
    }
}