package db.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CommerceQuery {
	private final Connection connection;
	private List<Object[]> queryResultTable = new ArrayList<>();

	public CommerceQuery(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }
	
	// 11
	public Optional<List<Object[]>> amountProductSoldLastMonth(String idProdotto) {
		final String query = "SELECT v.idProdotto, SUM(v.quantità) AS quantità "
				+ "FROM vendita v, servizio s, evento e WHERE v.idProdotto = ? "
				+ "AND v.idServizio = s.idServizio AND s.idEvento = e.idEvento "
				+ "AND e.data BETWEEN DATE_SUB(NOW(),INTERVAL 1 MONTH) AND CURRENT_DATE() GROUP BY v.idProdotto";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, idProdotto);
			final ResultSet resultSet = statement.executeQuery();
			try {
				while (resultSet.next()) {
					final Integer quantità = resultSet.getInt("quantità");

					Object[] data = { idProdotto, quantità };
					queryResultTable.add(data);
				}
			} catch (final SQLException e) {
			}
			return Optional.ofNullable(queryResultTable);
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

}
