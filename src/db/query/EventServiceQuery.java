package db.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import db.tables.EventTable;
import db.tables.ServiceTable;
import model.Event;
import model.Service;

public class EventServiceQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public EventServiceQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
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
	
	public List<Service> eventServiceList() {
		ServiceTable queryResultServiceTable = new ServiceTable(connection);
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM servizio s" +
            		"WHERE s.idEvento = ?");
            	return queryResultServiceTable.readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
