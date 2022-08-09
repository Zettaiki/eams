package db.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import db.Table;
import model.Evento;
import utils.Utils;

public class EventoTable implements Table<Evento, String> {

	public static final String TABLE_NAME = "evento";

	private final Connection connection;

	public EventoTable(final Connection connection) {
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
            			"idEvento CHAR(20) NOT NULL PRIMARY KEY," +
            			"nome VARCHAR(30) NOT NULL," +
            			"data DATETIME NOT NULL," +
            			"descrizione VARCHAR(60) NULL DEFAULT NULL" +
            		")");
            return true;
        } catch (final SQLException e) {        	
            return false;
        }
	}

	@Override
	public Optional<Evento> findByPrimaryKey(String idEvento) {
		final String query = "SELECT * FROM " + TABLE_NAME + " WHERE idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idEvento);
            final ResultSet resultSet = statement.executeQuery();
            return readFromResultSet(resultSet).stream().findFirst();
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Evento> findAll() {
		try (final Statement statement = this.connection.createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SELECT * FROM " + TABLE_NAME);
            return readFromResultSet(resultSet);
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public List<Evento> readFromResultSet(ResultSet resultSet) {
		final List<Evento> eventi = new ArrayList<>();
		try {
			while (resultSet.next()) {
				final String idEvento = resultSet.getString("idEvento");
				final String nome = resultSet.getString("nome");
				final Date data = Utils.sqlDateToDate(resultSet.getDate("data"));
				final Optional<String> descrizione =  Optional.ofNullable(resultSet.getString("descrizione"));
				
				final Evento evento = new Evento(idEvento, nome, data, descrizione);
				eventi.add(evento);
			}
		} catch (final SQLException e) {}
		return eventi;
	}

	@Override
	public boolean save(Evento evento) {
		final String query = "INSERT INTO " + TABLE_NAME +
				"(idEvento, nome, data, descrizione) VALUES (?,?,?,?)";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, evento.getIdEvento());
            statement.setString(2, evento.getNome());
            statement.setDate(3, Utils.dateToSqlDate(evento.getData()));
            statement.setString(4, evento.getDescrizione().orElse(null));
            statement.executeUpdate();
            return true;
        } catch (final SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

	@Override
	public boolean update(Evento updatedEvento) {
		final String query = "UPDATE " + TABLE_NAME + " SET nome = ?," + "data = ?," + "descrizione = ? "
				+ "WHERE idEvento = ?";
		try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
			statement.setString(1, updatedEvento.getIdEvento());
			statement.setString(2, updatedEvento.getNome());
			statement.setDate(3, Utils.dateToSqlDate(updatedEvento.getData()));
			statement.setString(4, updatedEvento.getDescrizione().orElse(null));
			return statement.executeUpdate() > 0;
		} catch (final SQLException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public boolean delete(String idEvento) {
		final String query = "DELETE FROM " + TABLE_NAME + " WHERE idEvento = ?";
        try (final PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setString(1, idEvento);
            return statement.executeUpdate() > 0;
        } catch (final SQLException e) {
            throw new IllegalStateException(e);
        }
	}

}
