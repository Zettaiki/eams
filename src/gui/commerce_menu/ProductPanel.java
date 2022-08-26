package gui.commerce_menu;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class ProductPanel extends JPanel {
	private static final long serialVersionUID = 1848260251020024164L;

	public ProductPanel() {
        this.setLayout(new BorderLayout());
		
		// Upper panel
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Operazioni disponibili:"));
		a0.setLayout(new GridLayout(1,0));
		
			var registerButton = new JButton("Registrare prodotto");
			a0.add(registerButton);
		    
		    var buyButton = new JButton("Acquisto prodotto");
		    a0.add(buyButton);
		
		this.add(a0, BorderLayout.PAGE_START);
		
		// Bottom panel
		var a1 = new JPanel();
		a1.setBorder(BorderFactory.createTitledBorder("Lista prodotti e quantita vendute:"));
		a1.setLayout(new GridLayout(0,1));
		
			var productTable = new JTable(TableExtractorUtils.saleTable());
			productTable.setEnabled(false);
			productTable.getTableHeader().setReorderingAllowed(false);
			productTable.getTableHeader().setEnabled(false);
			JScrollPane productListPanel = new JScrollPane(productTable);
		    a1.add(productListPanel);
		
		this.add(a1, BorderLayout.CENTER);
		
		// Action listeners 
		
		registerButton.addActionListener(e -> {
			JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new InsertProductPanel());
	    });
		
		buyButton.addActionListener(e -> {
	    	// TODO
	    });
	}
	
}
