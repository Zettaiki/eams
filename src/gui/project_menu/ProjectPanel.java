package gui.project_menu;

import javax.swing.*;
import utils.JComponentLoader;
import java.awt.*;

public class ProjectPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    public final GridBagConstraints c;
    
    public ProjectPanel() {
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
        	var registerButton = new JButton("Registrare progetto");
        	registerButton.setLayout(null);
        	registerButton.addActionListener(e -> {
            	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	        JComponentLoader.load(parentFrame, new InsertProjectPanel());
            });
        	a0.add(registerButton);
            
            var donatorlistButton = new JButton("Lista donatori progetto");
            donatorlistButton.setLayout(null);
            donatorlistButton.addActionListener(e -> {
            	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
    	        JComponentLoader.load(parentFrame, new ProjectDonatorsPanel());
            });
            a0.add(donatorlistButton);
            
            var filterActiveButton = new JButton("Filtra progetti attivi");
            filterActiveButton.setLayout(null);
            filterActiveButton.addActionListener(e -> {
            	// TODO
            });
            a0.add(filterActiveButton);
        }
        this.add(a0, c);
        
        // Second column
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 4;
        c.fill = GridBagConstraints.VERTICAL;
        var a1 = new JPanel();
        a1.setBorder(BorderFactory.createTitledBorder("Progetti registrati:"));
        a1.setLayout(new GridLayout(0,1));
        {
        	String[] columnNames = {"ID",
                    "Obbiettivo",
                    "Data inizio",
                    "Durata mesi"};
        	
        	Object[][] data = {
        		    {"-", "-", "-", "-"}
        		};
        	
        	var projectTable = new JTable(data, columnNames);
			projectTable.setEnabled(false);
        	projectTable.getTableHeader().setReorderingAllowed(false);
			projectTable.getTableHeader().setEnabled(false);
        	JScrollPane projectListPanel = new JScrollPane(projectTable);
            a1.add(projectListPanel);
        }
        this.add(a1, c);
    }
}