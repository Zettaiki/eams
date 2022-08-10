package gui.events_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.GUI;
import gui.MenuPanel;
import utils.JComponentLoader;

public class EventsMenuPanel extends JPanel {
	private static final long serialVersionUID = 8776421810569801974L;

	public EventsMenuPanel() {
        this.setName("EAMS - Events and services");
    	this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
    	this.setLayout(new BorderLayout());
        
        // Titolo schermata
    	var a0 = new JLabel("Ecological Association Management System - Area eventi e servizi");
    	a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        JTabbedPane switchPane = new JTabbedPane();
        
        // Events list panel
        
        JComponent eventsListPanel = new EventsListPanel();
        switchPane.addTab("Gestione eventi e servizi", eventsListPanel);
        
        // Service search panel
        
        JComponent searchServicePanel = new ServiceSearchPanel();
        switchPane.addTab("Ricerca servizi", searchServicePanel);
        
        // Service search panel
        
        JComponent garbagePanel = new EventsListPanel();
        switchPane.addTab("Gestione rifiuti", garbagePanel);
        
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
