package gui.commerce_menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import db.query.CommerceQuery;
import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class ProductPanel extends JPanel {
	private static final long serialVersionUID = 1848260251020024164L;

	public ProductPanel() {
        this.setLayout(new BorderLayout());
		
		// Upper panel
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Operazioni disponibili:"));
		a0.setLayout(new GridLayout(0,1));
		
			var upperButtons = new JPanel();
			upperButtons.setLayout(new GridLayout(1,0));
		    
			    var buyButton = new JButton("Vendita prodotto");
			    upperButtons.add(buyButton);
			    
			    var listButton = new JButton("Listino vendite");
			    upperButtons.add(listButton);
			    
			a0.add(upperButtons);
		    
		    var searchPanel = new JPanel();
		    searchPanel.setBorder(BorderFactory.createTitledBorder("Ricerca quantita venduta di un prodotto attraverso ID nell'ultimo mese:"));
		    searchPanel.setLayout(new GridLayout(1,0));
		    
			    var searchBar = new JTextField(16);
			    searchPanel.add(searchBar);
			    
			    var searchButton = new JButton("Cerca");
			    searchPanel.add(searchButton);
			
			a0.add(searchPanel);
		
		this.add(a0, BorderLayout.PAGE_START);
		
		// Bottom panel
		var a1 = new JPanel();
		a1.setBorder(BorderFactory.createTitledBorder("Listino prodotti:"));
		a1.setLayout(new GridLayout(0,1));
		
			var productTable = new JTable(TableExtractorUtils.productTable());
			productTable.setEnabled(false);
			productTable.getTableHeader().setReorderingAllowed(false);
			productTable.getTableHeader().setEnabled(false);
			JScrollPane productListPanel = new JScrollPane(productTable);
		    a1.add(productListPanel);
		
		this.add(a1, BorderLayout.CENTER);
		
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
	}
	
}
