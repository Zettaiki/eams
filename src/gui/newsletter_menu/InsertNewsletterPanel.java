package gui.newsletter_menu;

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

import db.tables.NewsletterTable;
import gui.GUI;
import model.Newsletter;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertNewsletterPanel extends JPanel {
	private static final long serialVersionUID = 5519718898198572867L;
	public final GridBagConstraints c;

    public InsertNewsletterPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - Newsletter registration");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Aggiunta newsletter"), c);
        
        // First panel
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JTextArea(1, 16);
        a0.setBorder(BorderFactory.createTitledBorder("Argomento:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(4, 20);
        a1.setBorder(BorderFactory.createTitledBorder("Descrizione:"));
        this.add(a1, c);
        
        // End panel
        c.gridx = 0;
        c.gridy = 3;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registro newsletter");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 4;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listener
        b0.addActionListener(e -> {
	        Newsletter newsletter = new Newsletter(null, a0.getText(), Optional.of(a1.getText()));
	        NewsletterTable table = new NewsletterTable(ConnectionProvider.getMySQLConnection());
	        if(!table.save(newsletter)) {
        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registrazione annullata.", "Newsletter error", JOptionPane.ERROR_MESSAGE);
	        } else {
        		JOptionPane.showMessageDialog(getParent(), "Newsletter registrata!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	        };
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new NewsletterMenuPanel());
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new NewsletterMenuPanel());
        });
    }
}
