package gui.events_menu;

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

public class EventsListPanel extends JPanel {
	private static final long serialVersionUID = 8776421810569801974L;
    public final GridBagConstraints c;
	
	public EventsListPanel() {
		this.c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		
		// First column 
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
		var a0 = new JPanel();
		a0.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
		a0.setLayout(new GridLayout(1,2));
		{
			var registerEventButton = new JButton("Registrare evento");
			registerEventButton.addActionListener(e -> {
				JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertEventPanel());
		    });
			a0.add(registerEventButton);
		    
		    var registerServiceButton = new JButton("Registrare servizio");
		    registerServiceButton.addActionListener(e -> {
		    	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		        JComponentLoader.load(parentFrame, new InsertServicePanel());
		    });
		    a0.add(registerServiceButton);
		}
		this.add(a0, c);
		
		// Second column
		c.gridx = 0;
        c.gridy = 1;
        c.gridheight = 4;
        c.fill = GridBagConstraints.HORIZONTAL;
		var a1 = new JPanel();
		a1.setBorder(BorderFactory.createTitledBorder("Eventi attivi:"));
		a1.setLayout(new GridLayout(0,1));
		{
			var donationTable = new JTable(TableExtractorUtils.eventTable());
			donationTable.setEnabled(false);
			donationTable.getTableHeader().setReorderingAllowed(false);
			donationTable.getTableHeader().setEnabled(false);
			JScrollPane projectListPanel = new JScrollPane(donationTable);
		    a1.add(projectListPanel);
		}
		this.add(a1, c);
    }
}
