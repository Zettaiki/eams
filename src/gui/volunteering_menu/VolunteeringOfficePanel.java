package gui.volunteering_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import db.query.VolunteerQuery;
import db.tables.OfficeTable;
import gui.GUI;
import utils.ConnectionProvider;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class VolunteeringOfficePanel extends JPanel {
	private static final long serialVersionUID = 1697847872341064830L;
	
	public VolunteeringOfficePanel() {
        this.setName("EAMS - Office volunteering lists");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Ricerca volontariato per sedi");
        a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        // Upper panel
        var b0 = new JPanel();
        b0.setLayout(new BorderLayout());
        
        	// Upper buttons
        	var b1 = new JPanel();
            b1.setBorder(BorderFactory.createTitledBorder("Scelta sede:"));
            b1.setLayout(new GridLayout(1,0));
                            
                OfficeTable officeTable = new OfficeTable(ConnectionProvider.getMySQLConnection());
            	List<String> officeFilterList = officeTable.findAll().stream().map((x) -> x.getCittà()).collect(Collectors.toList());
                Object[] officeList = officeFilterList.toArray();
            	
                var officePicker = new JComboBox<Object>(officeList);
                officePicker.setSelectedIndex(0);
                b1.add(officePicker);
                
                var officeFilter = new JButton("Cerca");
                b1.add(officeFilter);
                
                var mostVolunteersOffice = new JButton("Sede piu attiva");
                b1.add(mostVolunteersOffice);
            
            b0.add(b1, BorderLayout.PAGE_START);
            
            // Bottom panel
            var b2 = new JPanel();
    		b2.setBorder(BorderFactory.createTitledBorder("Volontari registrati nella sede scelta:"));
    		b2.setLayout(new GridLayout(0,1));
    		
            	var volunteeringTable = new JTable(TableExtractorUtils.volunteerTable());
            	volunteeringTable.setEnabled(false);
            	volunteeringTable.getTableHeader().setReorderingAllowed(false);
            	volunteeringTable.getTableHeader().setEnabled(false);
            	JScrollPane volunteeringListPanel = new JScrollPane(volunteeringTable);
                b2.add(volunteeringListPanel);
    		
            b0.add(b2, BorderLayout.CENTER);
        
        this.add(b0, BorderLayout.CENTER);
        
        // End panel
        var c0 = new JButton("Ritorna all'area volontariato");
        this.add(c0, BorderLayout.PAGE_END);
        
        // Action listener
        officeFilter.addActionListener(e -> {
        	volunteeringTable.setModel(TableExtractorUtils.volunteersInOfficeQuery(officePicker.getSelectedItem().toString()));
        });
        
        mostVolunteersOffice.addActionListener(e -> {
        	VolunteerQuery volunteerQuery = new VolunteerQuery(ConnectionProvider.getMySQLConnection());
    		JOptionPane.showMessageDialog(getParent(), "Donatore con maggiore quantita:\n" +
	    	"Nome sede: " + volunteerQuery.mostActiveOffice().get().get(0)[0].toString() +
	    	"\nOre servizio: " + volunteerQuery.mostActiveOffice().get().get(0)[1].toString(),
    		"Informazione", JOptionPane.INFORMATION_MESSAGE);
        });
        
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new VolunteeringMenuPanel());
        });
    }
}
