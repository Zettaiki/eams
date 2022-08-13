package db.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import utils.Utils;

public class EventServiceQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public EventServiceQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	public List<String> activeEvents() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM evento e" +
            		"WHERE e.data >= current_date()");
            try {
    			while (resultSet.next()) {
    				final String idEvento = resultSet.getString("idEvento");
    				final String nome = resultSet.getString("nome");
    				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
    				final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
    				
    				queryResultTable.add(new StringBuilder().append(idEvento).append(" ")
    						.append(nome).append(" ")
    						.append(data).append(" ")
    						.append(descrizione).toString());
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
