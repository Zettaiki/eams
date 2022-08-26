package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import db.tables.PersonTable;
import model.Person;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertPersonPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;

    public InsertPersonPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New person");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        this.add(new JLabel("Ecological Association Management System - Registro persona"), c);
        
        // First column
        
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("Nome:"));
        this.add(a0, c);
        
        c.gridx = 1;
        c.gridy = 1;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Cognome:"));
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a2 = new JTextArea(1, 16);
        a2.setBorder(BorderFactory.createTitledBorder("Mail:"));
        this.add(a2, c);
        
        c.gridx = 1;
        c.gridy = 2;
        var a3 = new JTextArea(1, 16);
        a3.setBorder(BorderFactory.createTitledBorder("Telefono:"));
        this.add(a3, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a4 = new JTextArea(1, 16);
        a4.setBorder(BorderFactory.createTitledBorder("Indirizzo:"));
        this.add(a4, c);
        
        c.gridx = 1;
        c.gridy = 3;
        var a5 = new JTextArea(1, 16);
        a5.setBorder(BorderFactory.createTitledBorder("Città:"));
        this.add(a5, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a6 = new JTextArea(1, 16);
        a6.setBorder(BorderFactory.createTitledBorder("Regione:"));
        this.add(a6, c);
        
        c.gridx = 1;
        c.gridy = 4;
        var a7 = new JTextArea(1, 16);
        a7.setBorder(BorderFactory.createTitledBorder("Codice postale:"));
        this.add(a7, c);
        
        c.gridx = 0;
        c.gridy = 5;
        c.gridwidth = 2;
        var a8 = new JTextArea(1, 16);
        a8.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a8, c);
        
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
        var b1 = new JButton("Ritorna al menu principale");
        this.add(b1, c);
        
        // Action listeners
        
        b0.addActionListener(e -> {
	        Person person = new Person(a8.getText(), a0.getText(), a1.getText(), a2.getText(), Optional.of(a3.getText()), a4.getText(), a5.getText(), a6.getText(), a7.getText(), Optional.empty());
	        PersonTable personTable = new PersonTable(ConnectionProvider.getMySQLConnection());
	        if(!personTable.save(person)) {
        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registro annullato.", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
        		JOptionPane.showMessageDialog(getParent(), "Persona registrata!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	        }
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
    }
}