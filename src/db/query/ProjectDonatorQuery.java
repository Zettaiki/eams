package db.query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.tables.ProjectTable;
import model.Project;

public class ProjectDonatorQuery {
	private final Connection connection;
	private List<Object[]> queryResultTable = new ArrayList<>();

	public ProjectDonatorQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	// 1
	public List<Project> activeProjects() {
		ProjectTable queryResultProjectTable = new ProjectTable(connection);
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM progetto p " +
            		"WHERE current_date() BETWEEN p.dataInizio AND p.dataFine;");
            return queryResultProjectTable.readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 10
	public Optional<List<Object[]>> donationPerProject() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT p.idProgetto, SUM(d.importo) AS donazioniProgetto, "
            		+ "concat(round((SUM(d.importo)/(SELECT SUM(d.importo) as totDonazioni "
            		+ "		FROM donazione d "
            		+ "	    WHERE d.idProgetto IS NOT NULL "
            		+ "	) * 100 ),2),'%') AS percentuale "
            		+ "	FROM donazione AS d JOIN progetto AS p ON d.idProgetto = p.idProgetto "
            		+ "	GROUP BY p.idProgetto "
            		+ "	ORDER BY percentuale DESC"
            		);
            try {
    			while (resultSet.next()) {
    				final Integer idProgetto = resultSet.getInt("idProgetto");
    				final BigDecimal donazioniProgetto = resultSet.getBigDecimal("donazioniProgetto");
    				final String percentuale = resultSet.getString("percentuale");
    				
    				Object[] data = { idProgetto, donazioniProgetto, percentuale };

    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 10 bis
	public Optional<List<Object[]>> donationPerActiveProject() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT p.idProgetto, SUM(d.importo) AS donazioniProgetto, "
            		+ "concat(round((SUM(d.importo)/(SELECT SUM(d.importo) as totDonazioni "
            		+ "		FROM donazione d "
            		+ "	    WHERE d.idProgetto IS NOT NULL "
            		+ "	) * 100 ),2),'%') AS percentuale "
            		+ "	FROM donazione AS d JOIN progetto AS p ON d.idProgetto = p.idProgetto "
            		+ "	WHERE p.dataInizio >= current_date() "
            		+ "	GROUP BY p.idProgetto "
            		+ "	ORDER BY percentuale DESC"
            		);
            try {
    			while (resultSet.next()) {
    				final Integer idProgetto = resultSet.getInt("idProgetto");
    				final Integer donazioniProgetto = resultSet.getInt("donazioniProgetto");
    				final String percentuale = resultSet.getString("percentuale");

					Object[] data = { idProgetto, donazioniProgetto, percentuale };

    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 15
	public Optional<List<Object[]>> projectDonators(Integer idProgetto) {
		final String query = "SELECT DISTINCT d.codiceFiscale, p.nome, p.cognome "
				+ "FROM donazione d JOIN persona AS p ON d.codiceFiscale = p.codiceFiscale "
				+ "WHERE d.idProgetto = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setInt(1, idProgetto);
        	final ResultSet resultSet = statement.executeQuery();
        	try {
    			while (resultSet.next()) {
    				final String codiceFiscale = resultSet.getString("codiceFiscale");
    				final String nome = resultSet.getString("nome");
    				final String cognome = resultSet.getString("cognome");
    				
    				Object[] data = {codiceFiscale, nome, cognome};
    				
    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 8
	public Optional<List<Object[]>> bestDonator() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT maxDonazione.codiceFiscale, p.nome, p.cognome, "
            		+ "MAX(maxDonazione.maxDonato) AS importoMaxDonato "
            		+ "FROM (SELECT *, SUM(d.importo) AS maxDonato FROM donazione d "
            		+ "WHERE d.dataDonazione BETWEEN DATE_SUB(NOW(),INTERVAL 1 YEAR) AND CURRENT_DATE() "
            		+ "GROUP BY d.codiceFiscale) as maxDonazione, persona p "
            		+ "WHERE p.codiceFiscale = maxDonazione.codiceFiscale"
            		);
            try {
    			while (resultSet.next()) {
    				final String codiceFiscale = resultSet.getString("codiceFiscale");
    				final String nome = resultSet.getString("nome");
    				final String cognome = resultSet.getString("cognome");
    				final BigDecimal importoMaxDonato = resultSet.getBigDecimal("importoMaxDonato");

					Object[] data = { codiceFiscale, nome, cognome, importoMaxDonato };

    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	

	
	
}
