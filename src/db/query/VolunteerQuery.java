package db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.tables.VolunteerTable;
import model.Volunteer;

public class VolunteerQuery {
	private final Connection connection;
	private List<Object[]> queryResultTable = new ArrayList<>();

	public VolunteerQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	// 9
	public List<Volunteer> officeVolunteer(String sedeCittà) {
		VolunteerTable queryResultVolunteerTable = new VolunteerTable(connection);
		final String query = "SELECT * FROM volontario v WHERE v.sedeCittà = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, sedeCittà);
        	final ResultSet resultSet = statement.executeQuery();
            return queryResultVolunteerTable.readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 7
	public Optional<List<Object[]>> mostActiveVolunteer() {
		final String query = "SELECT pa.codiceFiscaleVolontario, pe.nome, pe.cognome, MAX(classificaVolontari.oreServizio) AS oreServizio "
				+ "FROM partecipazione pa, persona pe, (SELECT p.codiceFiscaleVolontario, CAST(SUM(TIMEDIFF(s.oraFine,s.oraInizio)) AS TIME) "
				+ "AS oreServizio FROM partecipazione p, servizio WHERE p.idServizio = s.idServizio "
				+ "GROUP BY p.codiceFiscaleVolontario) as classificaVolontari "
				+ "WHERE pa.codiceFiscaleVolontario = classificaVolontari.codiceFiscaleVolontario "
				+ "AND  pa.codiceFiscaleVolontario = pe.codiceFiscale";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	final ResultSet resultSet = statement.executeQuery();
        	try {
    			while (resultSet.next()) {
    				final String codiceFiscaleVolontario = resultSet.getString("codiceFiscaleVolontario");
    				final String nome = resultSet.getString("nome");
    				final String cognome = resultSet.getString("cognome");
    				final Time oreServizio = resultSet.getTime("oreServizio");
    				
    				Object[] data = { codiceFiscaleVolontario, nome, cognome, oreServizio };
    				
    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 18
	public Optional<List<Object[]>> mostActiveOffice() {
		final String query = "SELECT v.sedeCittà, CAST(SUM(TIMEDIFF(s.oraFine,s.oraInizio)) AS TIME) AS oreServizio "
				+ "FROM partecipazione p, servizio s, volontario v "
				+ "WHERE p.idServizio = s.idServizio AND p.codiceFiscaleVolontario = v.codiceFiscale "
				+ "GROUP BY v.sedeCittà "
				+ "ORDER BY oreServizio DESC";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			try {
				while (resultSet.next()) {
					final String sedeCittà = resultSet.getString("sedeCittà");
					final Time oreServizio = resultSet.getTime("oreServizio");

					Object[] data = { sedeCittà, oreServizio };

					queryResultTable.add(data);
				}
			} catch (final SQLException e) {
			}
			return Optional.ofNullable(queryResultTable);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
