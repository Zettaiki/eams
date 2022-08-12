package gui.agencies_menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    public final GridBagConstraints c;

	public ProductPanel() {
        this.c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		// First column 
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Operazioni disponibili:"));
		a0.setLayout(new GridLayout(1,2));
		{
			var registerButton = new JButton("Registrare prodotto");
			registerButton.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new ProductRegisterPanel());
		    });
			a0.add(registerButton);
		    
		    var buyButton = new JButton("Acquisto prodotto");
		    buyButton.addActionListener(e -> {
		    	//JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        //JComponentLoader.load(parentFrame, new InsertDonationPanel());
		    });
		    a0.add(buyButton);
		}
		this.add(a0, c);
		
		// Second column
		c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
		var a1 = new JPanel();
		a1.setBorder(BorderFactory.createTitledBorder("Lista prodotti e quantita vendute:"));
		a1.setLayout(new GridLayout(0,1));
		{
			var productTable = new JTable(TableExtractorUtils.saleTable());
			productTable.setEnabled(false);
			productTable.getTableHeader().setReorderingAllowed(false);
			productTable.getTableHeader().setEnabled(false);
			JScrollPane productListPanel = new JScrollPane(productTable);
		    a1.add(productListPanel);
		}
		this.add(a1, c);
	}
	
}
