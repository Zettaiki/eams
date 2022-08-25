package utils;

import java.util.List;
import java.util.Optional;

import javax.swing.table.DefaultTableModel;

import db.query.NewsletterQuery;
import db.query.ProjectDonatorQuery;
import db.query.VolunteerQuery;
import db.tables.DonationTable;
import db.tables.EventTable;
import db.tables.NewsletterTable;
import db.tables.ProjectTable;
import db.tables.SaleTable;
import db.tables.ServiceTable;
import db.tables.VolunteerTable;
import model.Donation;
import model.Event;
import model.Newsletter;
import model.Project;
import model.Sale;
import model.Service;
import model.Volunteer;

public class TableExtractorUtils {
	
	private static <T> String checkIfEmpty(Optional<T> o) {
		if(o.isEmpty()) return "";
		return o.get().toString();
	}
	
	public static DefaultTableModel activeProjectsQuery() {
		// Requesting connection
		ProjectDonatorQuery projectDonatorQuery = new ProjectDonatorQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID", "Obbiettivo", "Data inizio", "Data fine", "Durata mesi", "Descrizione"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		List<Project> projectList = projectDonatorQuery.activeProjects();
		for( Project x : projectList ) {
			Object[] temp = {x.getIdProgetto(), x.getObbiettivo(), x.getDataInizio(), x.getDataFine(), x.getDurataMesi(), checkIfEmpty(x.getDescrizione())};
			data.addRow(temp);
		}
		return data;
	}
	
	public static DefaultTableModel projectDonatorsQuery(int index) {
		// Requesting connection
		ProjectDonatorQuery projectDonatorQuery = new ProjectDonatorQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"Codice fiscale", "Nome", "Cognome"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> projectDonatorsList = projectDonatorQuery.projectDonators(index);
		if(projectDonatorsList.isEmpty()) return data;
		for( Object[] x : projectDonatorsList.get()) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel volunteersInOfficeQuery(String sedeCittà) {
		// Requesting connection
		VolunteerQuery volunteerQuery = new VolunteerQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"Codice fiscale", "Sede", "Data inscrizione"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		List<Volunteer> officeVolunteerList = volunteerQuery.officeVolunteer(sedeCittà);
		for( Volunteer x : officeVolunteerList ) {
			Object[] temp = {x.getCodiceFiscale(), x.getSedeCittà(), x.getDataIscrizione()};
			data.addRow(temp);
		}
		return data;
	}
	
	public static DefaultTableModel rankNewsletterQuery() {
		// Requesting connection
		NewsletterQuery newsletterQuery = new NewsletterQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID", "Numero iscritti"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		List<Object[]> rankNewsletterList = newsletterQuery.rank();
		for( Object[] x : rankNewsletterList ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel volunteerTable() {
		// Requesting connection
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
		// Requesting connection
		ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
	    String[] columnNames = {"ID", "Obbiettivo", "Data inizio", "Data fine", "Durata mesi", "Descrizione"};	    
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Project> projectList = projectTable.findAll();
	    for( Project x : projectList ) {
	    	Object[] temp = {x.getIdProgetto(), x.getObbiettivo(), x.getDataInizio(), x.getDataFine(), x.getDurataMesi(), checkIfEmpty(x.getDescrizione())};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel donationTable() {
		// Requesting connection
		DonationTable donationTable = new DonationTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Importo", "Codice fiscale", "Data donazione", "ID progetto"};  
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Donation> donationList = donationTable.findAll();
	    for( Donation x : donationList ) {
	    	Object[] temp = {x.getIdDonazione(), x.getImporto(), x.getCodiceFiscale(), x.getDataDonazione(), checkIfEmpty(x.getIdProgetto())};
	    	data.addRow(temp);
	    }
	    return data;
	}

	public static DefaultTableModel eventTable() {
		// Requesting connection
		EventTable eventTable = new EventTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Nome", "Data", "Descrizione"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Event> eventList = eventTable.findAll();
	    for( Event x : eventList ) {
	    	Object[] temp = {x.getIdEvento(), x.getNome(), x.getData(), checkIfEmpty(x.getDescrizione())};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel serviceTable() {
		// Requesting connection
		ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Ora inizio", "Ora fine", "Tipo", "ID progetto"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Service> serviceList = serviceTable.findAll();
	    for( Service x : serviceList ) {
	    	Object[] temp = {x.getIdServizio(), x.getOraInizio(), x.getOraInizio(), x.getTipo(), checkIfEmpty(x.getIdProgetto())};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel newsletterTable() {
		// Requesting connection
		NewsletterTable newsletterTable = new NewsletterTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Argomento", "Descrizione"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Newsletter> serviceList = newsletterTable.findAll();
	    for( Newsletter x : serviceList ) {
	    	Object[] temp = {x.getIdNewsletter(), x.getArgomento(), checkIfEmpty(x.getDescrizione())};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel	saleTable() {
		// Requesting connection
		SaleTable saleTable = new SaleTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "ID servizio", "Codice Fiscale Cliente", "Quantità"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Sale> saleList = saleTable.findAll();
	    for( Sale x : saleList ) {
	    	Object[] temp = {x.getIdProdotto(), x.getIdServizio(), x.getCodiceFiscaleCliente(), x.getQuantità()};
	    	data.addRow(temp);
	    }
	    return data;
	}
}