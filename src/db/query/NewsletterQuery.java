package db.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NewsletterQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public NewsletterQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	public List<String> rank() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT i.idNewsletter, " +
            		"COUNT(*) as iscritti " +
            		"FROM iscrizione i " +
            		"GROUP BY i.idNewsletter " +
            		"ORDER BY iscritti DESC");
            try {
    			while (resultSet.next()) {
    				final Integer idNewsletter = resultSet.getInt("idNewsletter");
    				final Integer iscritti = resultSet.getInt("iscritti");
    				
    				queryResultTable.add(new StringBuilder().append(idNewsletter).append(" ")
    						.append(iscritti).toString());
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
