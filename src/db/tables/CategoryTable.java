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

import model.Category;

public class CategoryTable {

	public static final String TABLE_NAME = "categoria";

	private final Connection connection;

	public CategoryTable(final Connection connection) {
        this.connection = Objects.requireNonNull(connection);
    }

	public String getTableName() {
		return TABLE_NAME;
	}

	public Optional<Category> findByPrimaryKey(String nome) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE nome = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, nome);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Category> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	public List<Category> readFromResultSet(ResultSet resultSet) {
		final List<Category> categorie = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String nome = resultSet.getString("nome");
				final Optional<String> descrizione = Optional.ofNullable(resultSet.getString("descrizione"));
			    
				final Category categoria = new Category(nome, descrizione);
				categorie.add(categoria);
			}
		} catch (final SQLException e) {}
		return categorie;
	}

	public boolean save(Category Categoria) {
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

	public boolean update(Category updatedCategoria) {
		final String query = "UPDATE " + TABLE_NAME + " SET descrizione = ? WHERE nome = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedCategoria.getDescrizione().orElse(null));
			statement.setString(2, updatedCategoria.getNome());
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

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
