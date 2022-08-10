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
import db.tables.DeliveryTable;
import model.Delivery;
import utils.ServerCredentials;
import utils.TableTestUtils;
import utils.Utils;

public class DeliveryTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static DeliveryTable deliveryTable = new DeliveryTable(connectionProvider.getMySQLConnection());

    final Delivery delivery1 = new Delivery("carta", new BigDecimal("12345678911"), Utils.buildDate(11, 12, 2021).get(), new BigDecimal("420.50"));
    final Delivery delivery2 = new Delivery("plastica", new BigDecimal("25369874102"), Utils.buildDate(11, 12, 2022).get(), new BigDecimal("200"));

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), deliveryTable.getTableName());
        deliveryTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), deliveryTable.getTableName()));
        assertTrue(deliveryTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(deliveryTable.save(this.delivery1));
        assertFalse(deliveryTable.save(this.delivery1));
        assertTrue(deliveryTable.save(this.delivery2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(deliveryTable.update(this.delivery1));
        deliveryTable.save(this.delivery2);
		final Delivery updatedDelivery2 = new Delivery("plastica", new BigDecimal("25369874102"), Utils.buildDate(11, 12, 2022).get(), new BigDecimal("200,30"));
		assertTrue(deliveryTable.update(updatedDelivery2));
		final Optional<Delivery> foundDelivery = deliveryTable.findByPrimaryKey(updatedDelivery2.getMateriale(),
				updatedDelivery2.getPartitaIVA(), updatedDelivery2.getData());
		assertFalse(foundDelivery.isEmpty());
        assertEquals(updatedDelivery2.getKgConsegnati(), foundDelivery.get().getKgConsegnati());
    }

    @Test
    public void deleteTest() {
        deliveryTable.save(this.delivery1);
        assertTrue(deliveryTable.delete(this.delivery1.getMateriale(), this.delivery1.getPartitaIVA(), this.delivery1.getData()));
        assertFalse(deliveryTable.delete(this.delivery1.getMateriale(), this.delivery1.getPartitaIVA(), this.delivery1.getData()));
		assertTrue(deliveryTable.findByPrimaryKey(this.delivery1.getMateriale(), this.delivery1.getPartitaIVA(),
				this.delivery1.getData()).isEmpty());
	 }

    @Test
    public void findByPrimaryKeyTest() {
        deliveryTable.save(this.delivery1);
        deliveryTable.save(this.delivery2);
        assertEquals(this.delivery1, deliveryTable.findByPrimaryKey(this.delivery1.getMateriale(), this.delivery1.getPartitaIVA(),
				this.delivery1.getData()).orElse(null));
        assertEquals(this.delivery2, deliveryTable.findByPrimaryKey(this.delivery2.getMateriale(), this.delivery2.getPartitaIVA(),
				this.delivery2.getData()).orElse(null));
    }

    @Test
    public void findAllTest() {
        deliveryTable.save(this.delivery1);
        deliveryTable.save(this.delivery2);
        assertIterableEquals(
            List.of(this.delivery1, this.delivery2),
            deliveryTable.findAll()
        );
    }
}
