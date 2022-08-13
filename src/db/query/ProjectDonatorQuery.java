package db.query;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import db.tables.ProjectTable;
import model.Project;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
