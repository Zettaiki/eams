package gui.commerce_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import db.query.CommerceQuery;
import gui.GUI;
import gui.MenuPanel;
import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class CommerceMenuPanel extends JPanel {
	private static final long serialVersionUID = 6028181739855329436L;

	public CommerceMenuPanel() {
		this.setName("EAMS - Commerce");
		this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
		this.setLayout(new BorderLayout());
		
		// Titolo schermata
		var a0 = new JLabel("Ecological Association Management System - Area commercio");
		a0.setHorizontalAlignment(SwingConstants.CENTER);
		a0.setFont(new Font("SansSerif", Font.BOLD, 20));
		this.add(a0, BorderLayout.PAGE_START);
		
		// Middle panel
		var a1 = new JPanel();
		a1.setLayout(new BorderLayout());
			
			var upperButtons = new JPanel();
			upperButtons.setBorder(BorderFactory.createTitledBorder("Operazioni disponibili:"));
			upperButtons.setLayout(new GridLayout(0,1));
			
				var options = new JPanel();
				options.setLayout(new GridLayout(1,0));
			    
				    var buyButton = new JButton("Vendita prodotto");
				    options.add(buyButton);
				    
				    var listButton = new JButton("Listino vendite");
				    options.add(listButton);
				    
				upperButtons.add(options);
			    
			    var searchPanel = new JPanel();
			    searchPanel.setBorder(BorderFactory.createTitledBorder("Ricerca quantita venduta di un prodotto attraverso ID nell'ultimo mese:"));
			    searchPanel.setLayout(new GridLayout(1,0));
			    
				    var searchBar = new JTextField(16);
				    searchPanel.add(searchBar);
				    
				    var searchButton = new JButton("Cerca");
				    searchPanel.add(searchButton);
				
				upperButtons.add(searchPanel);
			
			a1.add(upperButtons, BorderLayout.PAGE_START);
			
			var productTable = new JTable(TableExtractorUtils.productTable());
			productTable.setEnabled(false);
			productTable.getTableHeader().setReorderingAllowed(false);
			productTable.getTableHeader().setEnabled(false);
			JScrollPane productListPanel = new JScrollPane(productTable);
			productListPanel.setBorder(BorderFactory.createTitledBorder("Prodotti immagazzinati:"));
		    a1.add(productListPanel, BorderLayout.CENTER);
		
		this.add(a1, BorderLayout.CENTER);
				
		// End panel
		
		var c0 = new JButton("Ritorna al menu");
		this.add(c0, BorderLayout.PAGE_END);
		
		// Action listeners
		buyButton.addActionListener(e -> {
			JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new InsertBuyPanel());
	    });
		
		listButton.addActionListener(e -> {
			JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new SalesPanel());
		});
		
		searchButton.addActionListener(e -> {
			CommerceQuery commerceQuery = new CommerceQuery(ConnectionProvider.getMySQLConnection());
    		JOptionPane.showMessageDialog(getParent(),
    		"ID prodotto: " + commerceQuery.amountProductSoldLastMonth(searchBar.getText()).get().get(0)[0] +
	    	"\nQuantità: " + commerceQuery.amountProductSoldLastMonth(searchBar.getText()).get().get(0)[1],
    		"Informazione", JOptionPane.INFORMATION_MESSAGE);
		});
		
		c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
		});
	}
}
