package utils;

import java.util.List;
import java.util.Optional;

import javax.swing.table.DefaultTableModel;

import db.query.CommerceQuery;
import db.query.EventServiceQuery;
import db.query.NewsletterQuery;
import db.query.ProjectDonatorQuery;
import db.query.VolunteerQuery;
import db.tables.DonationTable;
import db.tables.EventTable;
import db.tables.NewsletterTable;
import db.tables.ProductTable;
import db.tables.ProjectTable;
import db.tables.ServiceTable;
import db.tables.VolunteerTable;
import model.Donation;
import model.Event;
import model.Newsletter;
import model.Product;
import model.Project;
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
		for (Project x : projectList) {
			Object[] temp = { x.getIdProgetto(), x.getObbiettivo(), checkIfEmpty(Utils.buildDate(x.getDataInizio())),
					checkIfEmpty(Utils.buildDate(x.getDataFine())), x.getDurataMesi(),
					checkIfEmpty(x.getDescrizione()) };
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
			Object[] temp = {x.getCodiceFiscale(), x.getSedeCittà(), checkIfEmpty(Utils.buildDate(x.getDataIscrizione()))};
			data.addRow(temp);
		}
		return data;
	}
	
	public static DefaultTableModel rankNewsletterQuery() {
		// Requesting connection
		NewsletterQuery newsletterQuery = new NewsletterQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID", "Argomento", "Numero iscritti"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		List<Object[]> rankNewsletterList = newsletterQuery.rank();
		for( Object[] x : rankNewsletterList ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel serviceSearchByID(String ID) {
		// Requesting connection
		EventServiceQuery eventServiceQuery = new EventServiceQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID evento", "ID servizio", "Ora inizio", "Ora fine", "Tipo", "ID progetto"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Service>> serviceList = eventServiceQuery.eventServiceList(ID);
		if(serviceList.isEmpty()) return data;
		for( Service x : serviceList.get() ) {
			Object[] temp = {x.getIdEvento(), x.getIdServizio(), x.getOraInizio(), x.getOraFine(), x.getTipo(), checkIfEmpty(x.getIdProgetto())};
			data.addRow(temp);
		}
		return data;
	}
	
	public static DefaultTableModel serviceSearchByType(String type) {
		// Requesting connection
		EventServiceQuery eventServiceQuery = new EventServiceQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID evento", "ID servizio", "Ora inizio", "Ora fine", "Tipo", "ID progetto"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> serviceList = eventServiceQuery.activeEventsType(type);
		if(serviceList.isEmpty()) return data;
		for( Object[] x : serviceList.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel donationStatistics() {
		// Requesting connection
		ProjectDonatorQuery projectDonatorQuery = new ProjectDonatorQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID progetto", "Donazioni progetto", "Percentuale"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> donationStatisticList = projectDonatorQuery.donationPerProject();
		if(donationStatisticList.isEmpty()) return data;
		for( Object[] x : donationStatisticList.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel activeDonationStatistics() {
		// Requesting connection
		ProjectDonatorQuery projectDonatorQuery = new ProjectDonatorQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID progetto", "Donazioni progetto", "Percentuale"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> donationStatisticList = projectDonatorQuery.donationPerActiveProject();
		if(donationStatisticList.isEmpty()) return data;
		for( Object[] x : donationStatisticList.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel productSaleList() {
		// Requesting connection
		CommerceQuery commerceQuery = new CommerceQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting the data
		String[] columnNames = {"ID prodotto", "Prezzo", "Sconto", "Prezzo scontato"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> productList = commerceQuery.showProductSalePrice();
		if(productList.isEmpty()) return data;
		for( Object[] x : productList.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel garbageYearStatistics() {
		// Requesting connection
		EventServiceQuery eventServiceQuery = new EventServiceQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collecting
		String[] columnNames = {"Materiale", "Media Kg raccolti"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> statistics = eventServiceQuery.avgCollectedGarbagePerYear();
		if(statistics.isEmpty()) return data;
		for( Object[] x : statistics.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	public static DefaultTableModel volunteerInService(String idService) {
		// Requesting connection
		EventServiceQuery eventServiceQuery = new EventServiceQuery(ConnectionProvider.getMySQLConnection());
		
		// Ordering and collection
		String[] columnNames = {"Codice fiscale", "Nome", "Cognome"};
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
		Optional<List<Object[]>> volunteers = eventServiceQuery.volunteerPerService(idService);
		if(volunteers.isEmpty()) return data;
		for( Object[] x : volunteers.get() ) {
			data.addRow(x);
		}
		return data;
	}
	
	// STANDARD TABLES
	
	public static DefaultTableModel volunteerTable() {
		// Requesting connection
	    VolunteerTable volunteerTable = new VolunteerTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"Codice fiscale", "Sede", "Data inscrizione"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Volunteer> volunteerList = volunteerTable.findAll();
	    for( Volunteer x : volunteerList ) {
	    	Object[] temp = {x.getCodiceFiscale(), x.getSedeCittà(), checkIfEmpty(Utils.buildDate(x.getDataIscrizione()))};
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
		for (Project x : projectList) {
			Object[] temp = { x.getIdProgetto(), x.getObbiettivo(), checkIfEmpty(Utils.buildDate(x.getDataInizio())),
					checkIfEmpty(Utils.buildDate(x.getDataFine())), x.getDurataMesi(),
					checkIfEmpty(x.getDescrizione()) };
			data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel donationTable() {
		// Requesting connection
		DonationTable donationTable = new DonationTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Importo", "Codice fiscale", "Data donazione", "Tipo", "ID progetto"};  
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Donation> donationList = donationTable.findAll();
	    for( Donation x : donationList ) {
			Object[] temp = { x.getIdDonazione(), x.getImporto(), x.getCodiceFiscale(),
					checkIfEmpty(Utils.buildDate(x.getDataDonazione())), x.getTipo(), checkIfEmpty(x.getIdProgetto()) };
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
	    	Object[] temp = {x.getIdEvento(), x.getNome(), checkIfEmpty(Utils.buildDate(x.getData())), checkIfEmpty(x.getDescrizione())};
	    	data.addRow(temp);
	    }
	    return data;
	}
	
	public static DefaultTableModel serviceTable() {
		// Requesting connection
		ServiceTable serviceTable = new ServiceTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID servizio", "ID evento", "Ora inizio", "Ora fine", "Tipo", "ID progetto"};
	    DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Service> serviceList = serviceTable.findAll();
	    for( Service x : serviceList ) {
	    	Object[] temp = {x.getIdServizio(), x.getIdEvento(), x.getOraInizio(), x.getOraFine(), x.getTipo(), checkIfEmpty(x.getIdProgetto())};
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
	
	public static DefaultTableModel	productTable() {
		// Requesting connection
		ProductTable productTable = new ProductTable(ConnectionProvider.getMySQLConnection());
	    
	    // Ordering and collecting the data
		String[] columnNames = {"ID", "Categoria", "Nome", "Prezzo", "Quantita", "Provenienza", "Descrizione"};	    
		DefaultTableModel data = new DefaultTableModel(columnNames, 0);
	    List<Product> saleList = productTable.findAll();
	    for( Product x : saleList ) {
	    	Object[] temp = {x.getIdProdotto(), x.getCategoria(), x.getNome(), x.getPrezzo(), x.getQuantitàImmagazzinata(), x.getProvenienza(), checkIfEmpty(x.getDescrizione())};
	    	data.addRow(temp);
	    }
	    return data;
	}
}