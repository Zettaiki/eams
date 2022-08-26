package db.query;

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
}
