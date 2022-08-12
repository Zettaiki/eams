package utils;

import java.util.List;

import javax.swing.table.DefaultTableModel;

import db.tables.DonationTable;
import db.tables.EventTable;
import db.tables.NewsletterTable;
import db.tables.ProjectTable;
import db.tables.ServiceTable;
import db.tables.VolunteerTable;
import model.Donation;
import model.Event;
import model.Newsletter;
import model.Project;
import model.Service;
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

	public static DefaultTableModel eventTable() {
		// Requesting conection
		EventTable eventTable = new EventTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Nome", "Data", "Descrizione"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Event> eventList = eventTable.findAll();
	    for( Event x : eventList ) {
	    	Object[] temp = {x.getIdEvento(), x.getNome(), x.getData(), x.getDescrizione()};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel serviceTable() {
		// Requesting conection
		ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Ora inizio", "Ora fine", "Tipo", "ID progetto"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Service> serviceList = serviceTable.findAll();
	    for( Service x : serviceList ) {
	    	Object[] temp = {x.getIdServizio(), x.getOraInizio(), x.getOraInizio(), x.getTipo(), x.getIdProgetto().toString()};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel newsletterTable() {
		// Requesting conection
		NewsletterTable newsletterTable = new NewsletterTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Argomento", "Descrizione"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Newsletter> serviceList = newsletterTable.findAll();
	    for( Newsletter x : serviceList ) {
	    	Object[] temp = {x.getIdNewsletter(), x.getArgomento(), x.getDescrizione().get()};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	
}