package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.PlanningTable;
import model.Planning;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class PlanningTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static PlanningTable planningTable = new PlanningTable(connectionProvider.getMySQLConnection());

    final Planning planning1 = new Planning("AAAAAAAAAAAAAAAA", "1");
    final Planning planning2 = new Planning("BBBBBBBBBBBBBBBB", "2");

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), planningTable.getTableName());
        planningTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), planningTable.getTableName()));
        assertTrue(planningTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(planningTable.save(this.planning1));
        assertFalse(planningTable.save(this.planning1));
        assertTrue(planningTable.save(this.planning2));
    }
    
    @Test
    public void updateTest() {
        assertThrows(IllegalStateException.class, () -> planningTable.update(new Planning("-", "-")));
    }

    @Test
    public void deleteTest() {
        planningTable.save(this.planning1);
        assertTrue(planningTable.delete(this.planning1.getCodiceFiscaleDipendente(), this.planning1.getIdEvento()));
        assertFalse(planningTable.delete(this.planning1.getCodiceFiscaleDipendente(), this.planning1.getIdEvento()));
        assertTrue(planningTable.findByPrimaryKey(this.planning1.getCodiceFiscaleDipendente(), this.planning1.getIdEvento()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        planningTable.save(this.planning1);
        planningTable.save(this.planning2);
        assertEquals(this.planning1, planningTable.findByPrimaryKey(this.planning1.getCodiceFiscaleDipendente(), this.planning1.getIdEvento()).orElse(null));
        assertEquals(this.planning2, planningTable.findByPrimaryKey(this.planning2.getCodiceFiscaleDipendente(), this.planning2.getIdEvento()).orElse(null));
    }

    @Test
    public void findAllTest() {
        planningTable.save(this.planning1);
        planningTable.save(this.planning2);
        assertIterableEquals(
            List.of(this.planning1, this.planning2),
            planningTable.findAll()
        );
    }
}
