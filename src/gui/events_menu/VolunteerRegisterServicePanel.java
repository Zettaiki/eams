package gui.events_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import db.tables.ParticipationTable;
import db.tables.PersonTable;
import db.tables.ServiceTable;
import db.tables.VolunteerTable;
import gui.GUI;
import gui.InsertPersonPanel;
import model.Participation;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class VolunteerRegisterServicePanel extends JPanel {
	private static final long serialVersionUID = 7285731003631860138L;
	public final GridBagConstraints c;

    public VolunteerRegisterServicePanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - Volunteer service register");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Registro volontario a servizio"), c);
        
        // First panel
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("Codice fiscale volontario:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("ID servizio:"));
        this.add(a1, c);
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 3;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Iscrivere volontario a servizio");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 4;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listeners
        
        b0.addActionListener(e -> {
        	PersonTable personTable = new PersonTable(ConnectionProvider.getMySQLConnection());
	        if(personTable.findByPrimaryKey(a0.getText()).isEmpty()) {
	        	JOptionPane.showMessageDialog(getParent(), "Utente non registrato.\nPer favore registrare l'utente.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertPersonPanel());
	        } else {
	        	VolunteerTable volunteeringTable = new VolunteerTable(ConnectionProvider.getMySQLConnection());
	        	if(volunteeringTable.findByPrimaryKey(a0.getText()).isEmpty()) {
		        	JOptionPane.showMessageDialog(getParent(), "L'utente scritto non è un volontario.", "Errore", JOptionPane.ERROR_MESSAGE);
	        	} else {
	        		ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	        		if(serviceTable.findByPrimaryKey(a1.getText()).isEmpty()) {
			        	JOptionPane.showMessageDialog(getParent(), "Il servizio non esiste.", "Errore", JOptionPane.ERROR_MESSAGE);
	        		} else {
			        	Participation participation = new Participation(a0.getText(), a1.getText());
			        	ParticipationTable participationTable = new ParticipationTable(ConnectionProvider.getMySQLConnection());
			        	if(!participationTable.save(participation)) {
			        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Error", JOptionPane.ERROR_MESSAGE);
			        	} else {
			        		JOptionPane.showMessageDialog(getParent(), "Volontario registrato al servizio " + a1.getText(), "Registro volontario", JOptionPane.INFORMATION_MESSAGE);
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
