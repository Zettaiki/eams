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
	private List<Object[]> queryResultTable = new ArrayList<>();

	public NewsletterQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	// 5
	public List<Object[]> rank() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT i.idNewsletter, n.argomento, " +
            		"COUNT(*) as iscritti " +
            		"FROM iscrizione i, newsletter n " +
            		"WHERE n.idNewsletter = i.idNewsletter " +
            		"GROUP BY i.idNewsletter " +
            		"ORDER BY iscritti DESC");
            try {
    			while (resultSet.next()) {
    				final Integer idNewsletter = resultSet.getInt("idNewsletter");
    				final String argomento = resultSet.getString("argomento");
    				final Integer iscritti = resultSet.getInt("iscritti");
    				
    				Object[] temp = {idNewsletter, argomento, iscritti};
    				
    				queryResultTable.add(temp);
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
}
