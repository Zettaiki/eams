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

public class ProjectDonatorQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public ProjectDonatorQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	public List<String> activeProjects() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM progetto p " +
            		"WHERE p.dataInizio >= current_date()");
            try {
    			while (resultSet.next()) {
    				final Optional<Integer> idProgetto = Optional.of(resultSet.getInt("idProgetto"));
    			    final String obbiettivo = resultSet.getString("obbiettivo");
    			    final Date dataInizio = Utils.sqlDateToDate(resultSet.getDate("dataInizio"));
    			    final Integer durataMesi = resultSet.getInt("durataMesi");
    			    final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
    				
    				queryResultTable.add(new StringBuilder().append(idProgetto).append(" ")
    						.append(obbiettivo).append(" ")
    						.append(dataInizio).append(" ")
    						.append(durataMesi).append(" ")
    						.append(descrizione).toString());
    			}
    		} catch (final SQLException e) {}
            return queryResultTable;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
