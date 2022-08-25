package gui.project_menu;

import javax.swing.*;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

import java.awt.*;

public class ProjectPanel extends JPanel {
    private static final long serialVersionUID = 8475751505006519027L;
    
    public ProjectPanel() {
        this.setLayout(new BorderLayout());
        
        // Upper buttons
        
        var a0 = new JPanel();
        a0.setBorder(BorderFactory.createTitledBorder("Opzioni:"));
        a0.setLayout(new GridLayout(1,0));
        
        	var registerButton = new JButton("Registrare progetto");
        	a0.add(registerButton);
            
            var donatorlistButton = new JButton("Lista donatori progetto");
            a0.add(donatorlistButton);
            
            var filterActiveButton = new JCheckBox("Filtra progetti attivi");
            a0.add(filterActiveButton);
        
        this.add(a0, BorderLayout.PAGE_START);
        
        // Bottom panel
        
        var a1 = new JPanel();
        a1.setBorder(BorderFactory.createTitledBorder("Progetti registrati:"));
        a1.setLayout(new GridLayout(0,1));
        
        	var projectTable = new JTable(TableExtractorUtils.projectTable());
			projectTable.setEnabled(false);
        	projectTable.getTableHeader().setReorderingAllowed(false);
			projectTable.getTableHeader().setEnabled(false);
        	JScrollPane projectListPanel = new JScrollPane(projectTable);
            a1.add(projectListPanel);
        
        this.add(a1, BorderLayout.CENTER);
        
        // Action listeners
        
        registerButton.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new InsertProjectPanel());
        });
        
        donatorlistButton.addActionListener(e -> {
        	JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new ProjectDonatorsPanel());
        });
        
        filterActiveButton.addActionListener(e -> {
        	if(filterActiveButton.isSelected()) {
        		projectTable.setModel(TableExtractorUtils.activeProjectsQuery());
        	} else {
        		projectTable.setModel(TableExtractorUtils.projectTable());
        	}
        });
    }
}