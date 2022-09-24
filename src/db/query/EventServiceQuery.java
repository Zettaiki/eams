package db.query;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.tables.EventTable;
import db.tables.ServiceTable;
import model.Event;
import model.Service;

public class EventServiceQuery {
	private final Connection connection;
	private List<Object[]> queryResultTable = new ArrayList<>();
	
	public EventServiceQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	// 2
	public List<Event> activeEvents() {
		EventTable queryResultEventTable = new EventTable(connection);
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM evento e" +
            		"WHERE e.data >= current_date()");
            return queryResultEventTable.readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 3
	public Optional<List<Service>> eventServiceList(String idServizio) {
		ServiceTable queryResultServiceTable = new ServiceTable(connection);
		final String query = "SELECT * FROM servizio s WHERE s.idEvento = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, idServizio);
        	final ResultSet resultSet = statement.executeQuery();
            return Optional.ofNullable(queryResultServiceTable.readFromResultSet(resultSet));
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 4
	public Optional<List<Object[]>> activeEventsType(String tipo) {
		final String query = "SELECT s.idServizio, s.oraInizio, s.oraFine, s.idProgetto, s.idEvento, e.data " +
				"FROM servizio s, evento e " +
				"WHERE e.idEvento = s.idEvento " +
				"AND s.tipo = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	statement.setString(1, tipo);
        	final ResultSet resultSet = statement.executeQuery();
        	try {
    			while (resultSet.next()) {
    				final String idServizio = resultSet.getString("idServizio");
    				final Time oraInizio = resultSet.getTime("oraInizio");
    				final Time oraFine = resultSet.getTime("oraFine");
    				final Integer idProgetto = resultSet.getInt("idProgetto");
    				final String idEvento = resultSet.getString("idEvento");
    				
    				Object[] temp = {idEvento, idServizio, oraInizio, oraFine, tipo, idProgetto};
    				queryResultTable.add(temp);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	// 14
	public Optional<List<Object[]>> volunteerPerService(String idServizio) {
		final String query = "SELECT p.codiceFiscaleVolontario, pe.nome, pe.cognome "
				+ "FROM partecipazione p, persona pe "
				+ "WHERE p.idServizio = ? "
				+ "AND p.codiceFiscaleVolontario = pe.codiceFiscale";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, idServizio);
			final ResultSet resultSet = statement.executeQuery();
			try {
				while (resultSet.next()) {
					final String codiceFiscaleVolontario = resultSet.getString("codiceFiscaleVolontario");
					final String nome = resultSet.getString("nome");
					final String cognome = resultSet.getString("cognome");

					Object[] data = { codiceFiscaleVolontario, nome, cognome };

					queryResultTable.add(data);
				}
			} catch (final SQLException e) {
			}
			return Optional.ofNullable(queryResultTable);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}
	
	// 17
	public Optional<List<Object[]>> avgCollectedGarbagePerYear() {
		final String query = "SELECT r.materiale, FORMAT(AVG(r.kg), 2) AS mediaKgRaccolti "
				+ "FROM raccolta r, servizio s, evento e "
				+ "WHERE r.idServizio = s.idServizio "
				+ "AND s.idEvento = e.idEvento "
				+ "AND e.data BETWEEN DATE_SUB(NOW(),INTERVAL 1 YEAR) AND CURRENT_DATE() "
				+ "GROUP BY r.materiale";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			final ResultSet resultSet = statement.executeQuery();
			try {
				while (resultSet.next()) {
					final String materiale = resultSet.getString("materiale");
					final BigDecimal mediaKgRaccolti = resultSet.getBigDecimal("mediaKgRaccolti");

					Object[] data = { materiale, mediaKgRaccolti };

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
