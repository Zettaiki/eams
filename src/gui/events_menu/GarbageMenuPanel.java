package gui.events_menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
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

import db.tables.GarbageCollectionTable;
import db.tables.GarbageTable;
import db.tables.ServiceTable;
import model.GarbageCollection;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class GarbageMenuPanel extends JPanel {
	private static final long serialVersionUID = -7665078034792655476L;
	public final GridBagConstraints c;
	
	public GarbageMenuPanel() {
		this.c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		// Titolo schermata      
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Sezione raccolta rifiuti"), c);
        
        // First column
        
        var a0 = new JPanel();
        a0.setLayout(new GridBagLayout());
        a0.setBorder(BorderFactory.createTitledBorder("Registro raccolta rifiuti:"));
        
        	c.gridx = 0;
            c.gridy = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            var serviceTextbox = new JTextArea(1, 16);
            serviceTextbox.setBorder(BorderFactory.createTitledBorder("ID servizio:"));
            a0.add(serviceTextbox, c);
            
            GarbageTable garbageTable = new GarbageTable(ConnectionProvider.getMySQLConnection());
	        Object[] garbageList = garbageTable.findAll().stream().map((x) -> x.getMateriale()).collect(Collectors.toList()).toArray();
	        
            c.gridx = 0;
            c.gridy = 1;
            var materialPicker = new JComboBox<Object>(garbageList);
            materialPicker.setBorder(BorderFactory.createTitledBorder("Materiale:"));
            a0.add(materialPicker, c);
            
            c.gridx = 0;
            c.gridy = 2;
            var massTextbox = new JTextArea(1, 16);
            massTextbox.setBorder(BorderFactory.createTitledBorder("Kg raccolti:"));
            a0.add(massTextbox, c);
            
            c.gridx = 0;
            c.gridy = 3;
    	    c.insets = new Insets(10, 0, 10, 0);
            var registerButton = new JButton("Registra evento");
            a0.add(registerButton, c);
        
        c.gridx = 0;
        c.gridy = 1;
	    c.insets = new Insets(0, 0, 0, 0);
        c.fill = GridBagConstraints.BOTH;
        this.add(a0, c);

        // Second column
        
        var a1 = new JPanel();
        a1.setLayout(new GridBagLayout());
        a1.setBorder(BorderFactory.createTitledBorder("Altre operazioni:"));
        
        	c.gridx = 0;
            c.gridy = 0;
        	var garbageStatisticsButton = new JButton("Media annuale rifiuti \nraccolti");
            a1.add(garbageStatisticsButton, c);
        
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(a1, c);
        
        // Action Listeners
        
        registerButton.addActionListener(e -> {
	        ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	        if(serviceTable.findByPrimaryKey(serviceTextbox.getText()).isEmpty()) {
	        	JOptionPane.showMessageDialog(getParent(), "Servizio non esistente.", "Errore", JOptionPane.ERROR_MESSAGE);
	        } else {
	        	GarbageCollection garbageCollection = new GarbageCollection(serviceTextbox.getText(), materialPicker.getSelectedItem().toString(), BigDecimal.valueOf(Double.parseDouble(massTextbox.getText())));
	        	GarbageCollectionTable garbageCollectionTable = new GarbageCollectionTable(ConnectionProvider.getMySQLConnection());
	        	if(!garbageCollectionTable.save(garbageCollection)) {
		        	JOptionPane.showMessageDialog(getParent(), "Errore registro: dati erronei.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	} else {
	        		JOptionPane.showMessageDialog(getParent(), "Raccolta rifiuti registrata", "Risultato registro rifiuti", JOptionPane.INFORMATION_MESSAGE);
	        		JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	    	        JComponentLoader.load(parentFrame, new EventsMenuPanel());
	        	}
	        }
        });
        
        garbageStatisticsButton.addActionListener(e -> {
	        // TODO
        });
    }
}
