package utils;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import db.tables.DonationTable;
import db.tables.ProjectTable;
import db.tables.VolunteerTable;
import model.Donation;
import model.Project;
import model.Volunteer;

public class TableExtractorUtils {
	
	public static DefaultTableModel volunteerTable() {
		// Requesting conection
	    VolunteerTable volunteerTable = new VolunteerTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"Codice fiscale", "Sede", "Data inscrizione"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Volunteer> volunteerList = volunteerTable.findAll();
	    for( Volunteer x : volunteerList ) {
	    	Object[] temp = {x.getCodiceFiscale(), x.getSedeCittà(), x.getDataIscrizione()};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel projectTable() {
		// Requesting conection
		ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
	    String[] columnNames = {"ID", "Obbiettivo", "Data inizio", "Durata mesi"};	    
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Project> projectList = projectTable.findAll();
	    for( Project x : projectList ) {
	    	Object[] temp = {x.getIdProgetto(), x.getObbiettivo(), x.getDataInizio(), x.getDurataMesi()};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel donationTable() {
		// Requesting conection
		DonationTable donationTable = new DonationTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Importo", "Codice fiscale", "Data donazione", "ID progetto"};  
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Donation> donationList = donationTable.findAll();
	    for( Donation x : donationList ) {
	    	Object[] temp = {x.getIdDonazione(), x.getImporto(), x.getCodiceFiscale(), x.getDataDonazione(), x.getIdProgetto()};
	    	data.addRow(temp);
	    }
	    return data;

	}
	
}
