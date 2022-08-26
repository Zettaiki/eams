package gui.project_menu;

import javax.swing.*;

import db.query.ProjectDonatorQuery;

import java.awt.*;

import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class DonationPanel extends JPanel {
	private static final long serialVersionUID = 7637300465466931816L;

	public DonationPanel() {
		this.setLayout(new BorderLayout());
		
		// First column 
		
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
		a0.setLayout(new GridLayout(1,0));
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
		    	ProjectDonatorQuery projectDonatorQuery = new ProjectDonatorQuery(ConnectionProvider.getMySQLConnection());
		    	projectDonatorQuery.bestDonator().get().toString();
        		JOptionPane.showMessageDialog(getParent(), "Donatore con maggiore quantita:\n" + projectDonatorQuery.bestDonator().get().get(0).toString(), "Informazione", JOptionPane.INFORMATION_MESSAGE);
		    });
		    a0.add(maxDonatorButton);
		}
		this.add(a0, BorderLayout.PAGE_START);
		
		// Second column
		
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
		this.add(a1, BorderLayout.CENTER);
	}
}