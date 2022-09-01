package db.tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import model.Office;

public class OfficeTable {

	public static final String TABLE_NAME = "sede";

	private final Connection connection;

	public OfficeTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	// show
	public List<Office> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Office> readFromResultSet(ResultSet resultSet) {
		final List<Office> sedi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String città = resultSet.getString("città");
				final String indirizzo = resultSet.getString("indirizzo");
				final String regione = resultSet.getString("regione");
				final String codicePostale = resultSet.getString("codicePostale");
				final String telefono = resultSet.getString("telefono");
				
				final Office sede = new Office(città, indirizzo, regione, codicePostale, telefono);
				sedi.add(sede);
			}
		} catch (final SQLException e) {}
		return sedi;
	}

}
