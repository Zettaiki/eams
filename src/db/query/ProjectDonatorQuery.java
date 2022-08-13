package db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.tables.ProjectTable;
import model.Project;
import utils.Utils;
public class ProjectDonatorQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public ProjectDonatorQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	public List<Project> activeProjects() {
		ProjectTable queryResultProjectTable = new ProjectTable(connection);
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM progetto p " +
            		"WHERE p.dataInizio >= current_date()");
            return queryResultProjectTable.readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<String> donationPerProject() {
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
    				final Integer donazioniProgetto = resultSet.getInt("donazioniProgetto");
    				final Float percentuale = resultSet.getFloat("percentuale");
    				
    				queryResultTable.add(new StringBuilder().append(idProgetto).append(" ")
    						.append(donazioniProgetto).append(" ")
    						.append(percentuale).toString());
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public List<String> donationPerActiveProject() {
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
    				final Float percentuale = resultSet.getFloat("percentuale");
    				
    				queryResultTable.add(new StringBuilder().append(idProgetto).append(" ")
    						.append(donazioniProgetto).append(" ")
    						.append(percentuale).toString());
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	public Optional<List<String>> projectDonators(Integer idProgetto) {
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
    				
    				queryResultTable.add(new StringBuilder().append(codiceFiscale).append(" ")
    						.append(nome).append(" ")
    						.append(cognome).toString());
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	
	
	
	
	
	
}
