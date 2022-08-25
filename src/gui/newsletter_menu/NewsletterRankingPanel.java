package gui.newsletter_menu;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import gui.GUI;
import utils.JComponentLoader;
import utils.TableExtractorUtils;

public class NewsletterRankingPanel extends JPanel {
	private static final long serialVersionUID = -6403648875952568006L;

	public NewsletterRankingPanel() {
		this.setName("EAMS - Newsletter");
        this.setPreferredSize(new Dimension(GUI.getMinScreenSize(), GUI.getMinScreenSize()*3/4));
        this.setLayout(new BorderLayout());
        
        // Titolo schermata
        var a0 = new JLabel("Ecological Association Management System - Ranking newsletter più seguite");
        a0.setHorizontalAlignment(SwingConstants.CENTER);
    	a0.setFont(new Font("SansSerif", Font.BOLD, 20));
        this.add(a0, BorderLayout.PAGE_START);
        
        // Centro
    	var volunteeringTable = new JTable(TableExtractorUtils.rankNewsletterQuery());
    	volunteeringTable.setEnabled(false);
    	volunteeringTable.getTableHeader().setReorderingAllowed(false);
    	volunteeringTable.getTableHeader().setEnabled(false);
    	JScrollPane b0 = new JScrollPane(volunteeringTable);
        b0.setBorder(BorderFactory.createTitledBorder("Ranking:"));
    	this.add(b0, BorderLayout.CENTER);

        // End panel
        var c0 = new JButton("Ritorna al pannello newsletter");
        c0.addActionListener(e -> {
	        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
	        JComponentLoader.load(parentFrame, new NewsletterMenuPanel());
        });
        this.add(c0, BorderLayout.PAGE_END);
    }
}
