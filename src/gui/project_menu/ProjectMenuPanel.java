package gui.project_menu;

import javax.swing.*;
import java.awt.*;
import gui.GUI;
import gui.MenuPanel;
import utils.JComponentLoader;

public class ProjectMenuPanel extends JPanel{
    private static final long serialVersionUID = 8475751505006519027L;

    public ProjectMenuPanel() {
        this.setName("EAMS - Projects and donations");
    	this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
    	this.setLayout(new BorderLayout());
        
        // Titolo schermata
    	var a0 = new JLabel("Ecological Association Management System - Area progetti e donazioni");
    	a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        JTabbedPane switchPane = new JTabbedPane();
        
        // Donation panel
        
        JComponent donationPanel = new DonationPanel();
        switchPane.addTab("Donazioni", donationPanel);
        
        // Proyect panel
        
        JComponent proyectPanel = new ProjectPanel();
        switchPane.addTab("Progetti", proyectPanel);
        
        this.add(switchPane, BorderLayout.CENTER);
        
        // End panel

        var a1 = new JButton("Ritorna al menu");
        a1.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new MenuPanel());
        });
        this.add(a1, BorderLayout.PAGE_END);
    }
}