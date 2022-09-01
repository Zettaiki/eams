package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

import model.Sale;

public class SaleTable {

	public static final String TABLE_NAME = "vendita";

	private final Connection connection;

	public SaleTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	// 16
	public boolean save(Sale vendita) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idProdotto, idServizio, codiceFiscaleCliente, quantità) " +
				"VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, vendita.getIdProdotto());
            statement.setString(2, vendita.getIdServizio());
            statement.setString(3, vendita.getCodiceFiscaleCliente());
            statement.setInt(4, vendita.getQuantità());
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
