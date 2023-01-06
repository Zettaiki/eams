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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.tables.ProductTable;
import db.tables.SaleTable;
import gui.GUI;
import model.Sale;
import utils.ConnectionProvider;
import utils.JComponentLoader;

public class InsertBuyPanel extends JPanel {
	private static final long serialVersionUID = 3807718932905995172L;
	private final GridBagConstraints c;
	
	public InsertBuyPanel() {
		this.c = new GridBagConstraints();
        this.setName("EAMS - Commerce");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new GridBagLayout());
        
        // Titolo schermata
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        this.add(new JLabel("Ecological Association Management System - Registrazione vendita"), c);
        
        // Upper panel
        ProductTable productTable = new ProductTable(ConnectionProvider.getMySQLConnection());
        Object[] productList = productTable.findAll().stream().map((x) -> x.getIdProdotto()).collect(Collectors.toList()).toArray();
        
        c.gridx = 0;
        c.gridy = 1;
        var a0 = new JComboBox<Object>(productList);
        a0.setBorder(BorderFactory.createTitledBorder("ID prodotto:"));
        this.add(a0, c);
        
        c.gridx = 0;
        c.gridy = 2;
        var a1 = new JTextField(16);
        a1.setBorder(BorderFactory.createTitledBorder("ID servizio:"));
        this.add(a1, c);
        
        c.gridx = 0;
        c.gridy = 3;
        var a2 = new JTextField(16);
        a2.setBorder(BorderFactory.createTitledBorder("Codice fiscale:"));
        this.add(a2, c);
        
        c.gridx = 0;
        c.gridy = 4;
        var a3 = new JTextField(16);
        a3.setBorder(BorderFactory.createTitledBorder("Quantità:"));
        this.add(a3, c);
        
        // Bottom panel
        c.gridx = 0;
        c.gridy = 5;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b0 = new JButton("Registro vendita");
        this.add(b0, c);
        
        c.gridx = 0;
        c.gridy = 6;
	    c.insets = new Insets(10, 0, 0, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        var b1 = new JButton("Ritorna");
        this.add(b1, c);
        
        // Action listener
        b0.addActionListener(e -> {
        	Sale sale = new Sale(a0.getSelectedItem().toString(), a1.getText(), a2.getText(), Integer.parseInt(a3.getText()), null);
        	SaleTable table = new SaleTable(ConnectionProvider.getMySQLConnection());
	        if(!table.save(sale)) {
        		JOptionPane.showMessageDialog(getParent(), "Dati sbagliati. Registrazione annullata.", "Error", JOptionPane.ERROR_MESSAGE);
	        } else {
        		JOptionPane.showMessageDialog(getParent(), "Vendita registrata!.", "Register result", JOptionPane.INFORMATION_MESSAGE);
	        };
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new CommerceMenuPanel());
        });
        
        b1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new CommerceMenuPanel());
        });
	}
	
}
