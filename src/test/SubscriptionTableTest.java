package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.SubscriptionTable;
import model.Subscription;
import utils.ConnectionProvider;
import utils.TableTestUtils;

public class SubscriptionTableTest {
    final static SubscriptionTable subscriptionTable = new SubscriptionTable(ConnectionProvider.getMySQLConnection());

    final Subscription subscription1 = new Subscription("AAAAAAAAAAAAAAAA", 1);
    final Subscription subscription2 = new Subscription("BBBBBBBBBBBBBBBB", 2);

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), subscriptionTable.getTableName());
        subscriptionTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), subscriptionTable.getTableName()));
        assertTrue(subscriptionTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(subscriptionTable.save(this.subscription1));
        assertFalse(subscriptionTable.save(this.subscription1));
        assertTrue(subscriptionTable.save(this.subscription2));
    }
    
    @Test
    void updateTest() {
        assertThrows(IllegalStateException.class, () -> subscriptionTable.update(new Subscription("AAAAAAAAAAAAAAAA", 1)));
    }

    @Test
    void deleteTest() {
		subscriptionTable.save(this.subscription1);
		assertTrue(subscriptionTable.delete(this.subscription1.getCodiceFiscale(), this.subscription1.getIdNewsletter()));
		assertFalse(subscriptionTable.delete(this.subscription1.getCodiceFiscale(), this.subscription1.getIdNewsletter()));
		assertTrue(subscriptionTable.findByPrimaryKey(this.subscription1.getCodiceFiscale(), this.subscription1.getIdNewsletter()).isEmpty());
	}

	@Test
	void findByPrimaryKeyTest() {
		subscriptionTable.save(this.subscription1);
		subscriptionTable.save(this.subscription2);
		assertEquals(this.subscription1, subscriptionTable.findByPrimaryKey(this.subscription1.getCodiceFiscale(), this.subscription1.getIdNewsletter()).orElse(null));
		assertEquals(this.subscription2, subscriptionTable.findByPrimaryKey(this.subscription2.getCodiceFiscale(), this.subscription1.getIdNewsletter()).orElse(null));
	}

    @Test
    void findAllTest() {
        subscriptionTable.save(this.subscription1);
        subscriptionTable.save(this.subscription2);
        assertIterableEquals(
            List.of(this.subscription1, this.subscription2),
            subscriptionTable.findAll()
        );
    }
}
