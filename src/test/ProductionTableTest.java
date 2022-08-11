package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.ProductionTable;
import model.Production;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class ProductionTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static ProductionTable productionTable = new ProductionTable(connectionProvider.getMySQLConnection());

    final Production production1 = new Production("s1", "e1", "2", 20, "carta", new BigDecimal("30.20"));
    final Production production2 = new Production("s2", "e2", "21", 15, "plastica", new BigDecimal("220.10"));

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), productionTable.getTableName());
        productionTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), productionTable.getTableName()));
        assertTrue(productionTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(productionTable.save(this.production1));
        assertFalse(productionTable.save(this.production1));
        assertTrue(productionTable.save(this.production2));
    }
    
    @Test
    void updateTest() {
        assertFalse(productionTable.update(this.production1));
        productionTable.save(this.production2);
        final Production updatedProduction2 = new Production("s2", "e2", "21", 15, "plastica", new BigDecimal("200.00"));
        assertTrue(productionTable.update(updatedProduction2));
		final Optional<Production> foundProduction = productionTable.findByPrimaryKey(
				updatedProduction2.getIdServizio(), updatedProduction2.getIdEvento(),
				updatedProduction2.getIdProdotto());
		assertFalse(foundProduction.isEmpty());
        assertEquals(updatedProduction2.getKgRifiutiUsati(), foundProduction.get().getKgRifiutiUsati());
    }

    @Test
    void deleteTest() {
        productionTable.save(this.production1);
        assertTrue(productionTable.delete(
        		this.production1.getIdServizio(), this.production1.getIdEvento(),
        		this.production1.getIdProdotto()));
        assertFalse(productionTable.delete(
        		this.production1.getIdServizio(), this.production1.getIdEvento(),
        		this.production1.getIdProdotto()));
        assertTrue(productionTable.findByPrimaryKey(
        		this.production1.getIdServizio(), this.production1.getIdEvento(),
        		this.production1.getIdProdotto()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        productionTable.save(this.production1);
        productionTable.save(this.production2);
		assertEquals(this.production1, productionTable
				.findByPrimaryKey(this.production1.getIdEvento(), this.production1.getIdEvento(),
		        		this.production1.getIdProdotto())
				.orElse(null));
		assertEquals(this.production2, productionTable
				.findByPrimaryKey(this.production2.getIdEvento(), this.production2.getIdServizio(),
						this.production2.getIdProdotto())
				.orElse(null));
	}

    @Test
    void findAllTest() {
        productionTable.save(this.production1);
        productionTable.save(this.production2);
        assertIterableEquals(
            List.of(this.production1, this.production2),
            productionTable.findAll()
        );
    }
}
