package db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import db.tables.VolunteerTable;
import model.Volunteer;

public class VolunteerQuery {
	private final Connection connection;
	private List<String> queryResultTable = new ArrayList<>();

	public VolunteerQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
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
}
