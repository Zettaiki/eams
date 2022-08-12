package gui.agencies_menu;

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

public class CommerceMenuPanel extends JPanel {
	private static final long serialVersionUID = 6028181739855329436L;

	public CommerceMenuPanel() {
		this.setName("EAMS - Commerce");
		this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
		this.setLayout(new BorderLayout());
		
		// Titolo schermata
		var a0 = new JLabel("Ecological Association Management System - Area commercio");
		a0.setHorizontalAlignment(SwingConstants.CENTER);
		a0.setFont(new Font("SansSerif", Font.BOLD, 20));
		this.add(a0, BorderLayout.PAGE_START);
		
		JTabbedPane switchPane = new JTabbedPane();
		
		// Product panel
		
		JComponent productPanel = new ProductPanel();
		switchPane.addTab("Area prodotti", productPanel);
		
		// Proyect panel
		
		JComponent garbageDeliverPanel = new GarbageDeliverPanel();
		switchPane.addTab("Area rifiuti", garbageDeliverPanel);
		
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
