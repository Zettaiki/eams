package gui.project_menu;

import javax.swing.*;
import java.awt.*;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class DonationPanel extends JPanel {
	private static final long serialVersionUID = 7637300465466931816L;
    public final GridBagConstraints c;

	public DonationPanel() {
        this.c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		// First column 
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.VERTICAL;
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
		a0.setLayout(new GridLayout(0,1));
		{
			var registerButton = new JButton("Registrare donazione");
			registerButton.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertDonationPanel());
		    });
			a0.add(registerButton);
		    
		    var reportButton = new JButton("Rapporto donazione/progetto");
		    reportButton.addActionListener(e -> {
		    	// TODO
		    });
		    a0.add(reportButton);
		    
		    var maxDonatorButton = new JButton("Donatore maggior quantita");
		    maxDonatorButton.addActionListener(e -> {
		    	// TODO
		    });
		    a0.add(maxDonatorButton);
		}
		this.add(a0, c);
		
		// Second column
		c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.VERTICAL;
		var a1 = new JPanel();
		a1.setBorder(BorderFactory.createTitledBorder("Donazioni registrate:"));
		a1.setLayout(new GridLayout(0,1));
		{
			var donationTable = new JTable(TableExtractorUtils.donationTable());
			donationTable.setEnabled(false);
			donationTable.getTableHeader().setReorderingAllowed(false);
			donationTable.getTableHeader().setEnabled(false);
			JScrollPane donationListPanel = new JScrollPane(donationTable);
		    a1.add(donationListPanel);
		}
		this.add(a1, c);
	}
}