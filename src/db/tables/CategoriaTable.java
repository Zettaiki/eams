package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Categoria;

public class CategoriaTable implements Table<Categoria, String> {

	public static final String TABLE_NAME = "categoria";

	private final Connection connection;

	public CategoriaTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

	@Override
	public boolean createTable() {
		try (final Statement statement = this.connection.createStatement()) {
            statement.executeUpdate(
            	"CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            			"nome VARCHAR(30) NOT NULL PRIMARY KEY," +
            			"descrizione MEDIUMTEXT NULL DEFAULT NULL" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Categoria> findByPrimaryKey(String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, nome);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Categoria> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Categoria> readFromResultSet(ResultSet resultSet) {
		final List<Categoria> categorie = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String nome = resultSet.getString("nome");
				final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
			    
				final Categoria categoria = new Categoria(nome, descrizione);
				categorie.add(categoria);
			}
		} catch (final SQLException e) {}
		return categorie;
	}

	@Override
	public boolean save(Categoria Categoria) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(nome, descrizione) VALUES (?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, Categoria.getNome());
            statement.setString(2, Categoria.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Categoria updatedCategoria) {
		final String query = "UPDATE " + TABLE_NAME + " SET descrizione = ?, WHERE nome = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedCategoria.getDescrizione().orElse(null));
			statement.setString(2, updatedCategoria.getNome());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String nome) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, nome);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
