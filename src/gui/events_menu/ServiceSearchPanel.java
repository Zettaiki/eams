package gui.events_menu;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class ServiceSearchPanel extends JPanel {
	private static final long serialVersionUID = 543534484180509595L;

	 public final GridBagConstraints c;
		
		public ServiceSearchPanel() {
			this.c = new GridBagConstraints();
			this.setLayout(new GridBagLayout());
			
			// First column 
	        c.gridx = 0;
	        c.gridy = 0;
	        c.fill = GridBagConstraints.HORIZONTAL;
			var a0 = new JPanel();
			a0.setBorder(BorderFactory.createTitledBorder("Opzioni di ricerca servizi:"));
			a0.setLayout(new GridLayout(1,2));
			{
				String[] searchOptions = {"Ricerca per ID evento", "Ricerca per tipo servizio"};
				
				var searchOptionsBox = new JComboBox<String>(searchOptions);
				searchOptionsBox.setBorder(BorderFactory.createTitledBorder("Tipo di ricerca:"));
				searchOptionsBox.addActionListener(e -> {
					// TODO
			    });
				a0.add(searchOptionsBox);
			    
			    var searchField = new JTextField(16);
			    searchField.setBorder(BorderFactory.createTitledBorder("Dato da cercare:"));
			    searchField.addActionListener(e -> {
			    	// TODO
			    });
			    a0.add(searchField);
			    
			    var searchButton = new JButton("Cercare servizio");
			    searchButton.addActionListener(e -> {
					// TODO
			    });
				a0.add(searchButton);
			    
			}
			this.add(a0, c);
			
			// Second column
			c.gridx = 0;
	        c.gridy = 1;
	        c.gridheight = 4;
	        c.fill = GridBagConstraints.HORIZONTAL;
			var a1 = new JPanel();
			a1.setBorder(BorderFactory.createTitledBorder("Risultati servizi cercati:"));
			a1.setLayout(new GridLayout(0,1));
			{
				var donationTable = new JTable(TableExtractorUtils.serviceTable());
				donationTable.setEnabled(false);
				donationTable.getTableHeader().setReorderingAllowed(false);
				donationTable.getTableHeader().setEnabled(false);
				JScrollPane projectListPanel = new JScrollPane(donationTable);
			    a1.add(projectListPanel);
			}
			this.add(a1, c);
			
			// Third column
			c.gridx = 0;
	        c.gridy = 6;
	        c.gridheight = 1;
	        c.fill = GridBagConstraints.HORIZONTAL;
			var a2 = new JButton("Iscrivere volontario ad un servizio");
			a2.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new VolunteerRegisterServicePanel());
		    });
			this.add(a2, c);
	    }
}
