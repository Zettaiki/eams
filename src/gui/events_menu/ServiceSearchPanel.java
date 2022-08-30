package gui.events_menu;

import java.awt.BorderLayout;
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
		
		public ServiceSearchPanel() {
			this.setLayout(new BorderLayout());
			
			// First panel
			
			var a0 = new JPanel();
			a0.setBorder(BorderFactory.createTitledBorder("Opzioni di ricerca servizi:"));
			a0.setLayout(new GridLayout(1,2));
			
				String[] searchOptions = {"Ricerca per ID evento", "Ricerca per tipo servizio"};
				
				var searchOptionsBox = new JComboBox<String>(searchOptions);
				searchOptionsBox.setBorder(BorderFactory.createTitledBorder("Tipo di ricerca:"));
				a0.add(searchOptionsBox);
			    
			    var searchField = new JTextField(16);
			    searchField.setBorder(BorderFactory.createTitledBorder("Dato da cercare:"));
			    a0.add(searchField);
			    
			    var searchButton = new JButton("Cercare servizio");
				a0.add(searchButton);
			    
			this.add(a0, BorderLayout.PAGE_START);
			
			// Mid panel
			
			var a1 = new JPanel();
			a1.setBorder(BorderFactory.createTitledBorder("Risultati servizi cercati:"));
			a1.setLayout(new GridLayout(0,1));
			
				var serviceTable = new JTable(TableExtractorUtils.serviceTable());
				serviceTable.setEnabled(false);
				serviceTable.getTableHeader().setReorderingAllowed(false);
				serviceTable.getTableHeader().setEnabled(false);
				JScrollPane projectListPanel = new JScrollPane(serviceTable);
			    a1.add(projectListPanel);
			
			this.add(a1, BorderLayout.CENTER);
			
			// Bottom panel
			
			var a2 = new JPanel();
			a2.setLayout(new GridLayout(1,2));
			
				var volunteerButton = new JButton("Iscrivere volontario ad un servizio");
				a2.add(volunteerButton);
				
				var serviceListButton = new JButton("Lista volontari per servizio");
				a2.add(serviceListButton);
				
			this.add(a2, BorderLayout.PAGE_END);
			
			// Action listeners
			
			searchButton.addActionListener(e -> {
				if(searchOptionsBox.getSelectedIndex() == 0) {
					serviceTable.setModel(TableExtractorUtils.serviceSearchByID(searchField.getText()));
				} else if(searchOptionsBox.getSelectedIndex() == 1) {
					serviceTable.setModel(TableExtractorUtils.serviceSearchByType(searchField.getText()));
				}
		    });
			
			volunteerButton.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new VolunteerRegisterServicePanel());
		    });
			
			serviceListButton.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new ServiceVolunteerSearchPanel());
			});
	    }
}
