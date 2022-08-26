package gui.events_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

import db.tables.EventTable;
import gui.GUI;
import model.Event;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertEventPanel extends JPanel {
	private static final long serialVersionUID = -163393514887620459L;
	public final GridBagConstraints c;

    public InsertEventPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New event");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Inserimento evento"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("ID evento:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Nome evento"));
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
        a2.setBorder(BorderFactory.createTitledBorder("Data evento:"));
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a3 = new JTextArea(4, 20);
        a3.setBorder(BorderFactory.createTitledBorder("Descrizione:"));
        this.add(a3, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 5;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra evento");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listeners
        
        b0.addActionListener(e -> {
	        Event event = new Event(a0.getText(), a1.getText(), model.getValue(), Optional.of(a3.getText()));
	        EventTable table = new EventTable(ConnectionProvider.getMySQLConnection());
	        if(!table.save(event)) {
        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Project error", JOptionPane.ERROR_MESSAGE);
	        } else {
        		JOptionPane.showMessageDialog(getParent(), "Progetto registrato!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	        };
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
        });
    }
}
