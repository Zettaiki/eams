package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.SaleTable;
import model.Sale;
import utils.ServerCredentials;
import utils.TableTestUtils;
import utils.Utils;

public class SaleTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static SaleTable saleTable = new SaleTable(connectionProvider.getMySQLConnection());

    final Sale sale1 = new Sale("AAAAAAAAAAAAAAAA", "p1", Utils.buildDate(11, 12, 2021).get(), 20, "s1", "e1");
    final Sale sale2 = new Sale("BBBBBBBBBBBBBBBB", "p2", Utils.buildDate(11, 12, 2021).get(), 15, "s2", "e2");

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), saleTable.getTableName());
        saleTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), saleTable.getTableName()));
        assertTrue(saleTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(saleTable.save(this.sale1));
        assertFalse(saleTable.save(this.sale1));
        assertTrue(saleTable.save(this.sale2));
    }
    
    @Test
    void updateTest() {
        assertFalse(saleTable.update(this.sale1));
        saleTable.save(this.sale2);
        final Sale updatedSale2 = new Sale("BBBBBBBBBBBBBBBB", "e2", Utils.buildDate(11, 12, 2021).get(), 25, "s2", "e2");
        assertTrue(saleTable.update(updatedSale2));
		final Optional<Sale> foundSale = saleTable.findByPrimaryKey(
				updatedSale2.getCodiceFiscaleCliente(), updatedSale2.getIdProdotto(),
				updatedSale2.getData());
		assertFalse(foundSale.isEmpty());
        assertEquals(updatedSale2.getQuantità(), foundSale.get().getQuantità());
    }

    @Test
    void deleteTest() {
        saleTable.save(this.sale1);
        assertTrue(saleTable.delete(
        		this.sale1.getCodiceFiscaleCliente(), this.sale1.getIdProdotto(),
        		this.sale1.getData()));
        assertFalse(saleTable.delete(
        		this.sale1.getCodiceFiscaleCliente(), this.sale1.getIdProdotto(),
        		this.sale1.getData()));
        assertTrue(saleTable.findByPrimaryKey(
        		this.sale1.getCodiceFiscaleCliente(), this.sale1.getIdProdotto(),
        		this.sale1.getData()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        saleTable.save(this.sale1);
        saleTable.save(this.sale2);
		assertEquals(this.sale1, saleTable
				.findByPrimaryKey(this.sale1.getIdProdotto(), this.sale1.getIdProdotto(),
		        		this.sale1.getData())
				.orElse(null));
		assertEquals(this.sale2, saleTable
				.findByPrimaryKey(this.sale2.getIdProdotto(), this.sale2.getCodiceFiscaleCliente(),
						this.sale2.getData())
				.orElse(null));
	}

    @Test
    void findAllTest() {
        saleTable.save(this.sale1);
        saleTable.save(this.sale2);
        assertIterableEquals(
            List.of(this.sale1, this.sale2),
            saleTable.findAll()
        );
    }
}
