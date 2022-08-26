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
	
	public Optional<List<Object[]>> showProductSalePrice() {
		final String query = "SELECT v.idProdotto, v.codiceFiscaleCliente, (v.quantità * p.prezzo) "
				+ "AS prezzoVendita, s.sconto, "
				+ "(((p.prezzo / 100) * (100-s.sconto))*v.quantità) AS prezzoScontato "
				+ "FROM vendita v, prodotto p, sconto s WHERE p.idProdotto = v.idProdotto "
				+ "AND s.codiceFiscale = v.codiceFiscaleCliente";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
        	final ResultSet resultSet = statement.executeQuery();
        	try {
    			while (resultSet.next()) {
    				final String idProdotto = resultSet.getString("idProdotto");
    				final String prezzoVendita = resultSet.getString("prezzoVendita");
    				final String sconto = resultSet.getString("sconto");
    				final String prezzoScontato = resultSet.getString("prezzoScontato");
    				
    				Object[] data = {idProdotto, prezzoVendita, sconto, prezzoScontato};
    				
    				queryResultTable.add(data);
    			}
    		} catch (final SQLException e) {}
            return Optional.ofNullable(queryResultTable);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}
	
}
