package gui.commerce_menu;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import db.tables.CategoryTable;
import gui.GUI;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertProductPanel extends JPanel {
	private static final long serialVersionUID = -1162203922311957656L;
    public final GridBagConstraints c;
	
	public InsertProductPanel() {        
        this.c = new GridBagConstraints();
        this.setName("EAMS - New product");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        
        c.gridx = 0;
        c.gridy = 0;
        this.add(new JLabel("Ecological Association Management System - Registro nuovo prodotto"), c);
        
        // First column
        
        CategoryTable categoryTable = new CategoryTable(ConnectionProvider.getMySQLConnection());
        Object[] categoryList = categoryTable.findAll().stream().map((x) -> x.getNome()).collect(Collectors.toList()).toArray();
        
        c.gridx = 0;
        c.gridy = 1;
    	var a0 = new JComboBox<Object>(categoryList);
    	a0.setSelectedIndex(0);
        a0.setBorder(BorderFactory.createTitledBorder("Categoria prodotto:"));        
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextArea(1, 16);
        a1.setBorder(BorderFactory.createTitledBorder("Nome prodotto:"));
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a2 = new JTextArea(1, 16);
        a2.setBorder(BorderFactory.createTitledBorder("Prezzo:"));
        this.add(a2, c);
        
        /*
         * TODO: mancano molti campi da aggiungere.
         * > id prodotto (?)
         * > quantita immagazzinata
         * > provenienza
         * > descrizione
         */
        
        // End panel
        
        c.gridx = 0;
        c.gridy = 5;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registra prodotto");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listeners
        
    	a0.addActionListener(e -> {
	        // TODO
        });
    	
    	b0.addActionListener(e -> {
	        // TODO
        });
    	
    	b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new CommerceMenuPanel());
        });
    }
}
