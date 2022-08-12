package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.NewsletterTable;
import model.Newsletter;
import utils.ConnectionProvider;
import utils.TableTestUtils;

public class NewsletterTableTest {
    final static NewsletterTable newsletterTable = new NewsletterTable(ConnectionProvider.getMySQLConnection());

    final Newsletter newsletter1 = new Newsletter(1, "12345678911", Optional.of("interessante"));
    final Newsletter newsletter2 = new Newsletter(2,  "25369874102", Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), newsletterTable.getTableName());
        newsletterTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), newsletterTable.getTableName()));
        assertTrue(newsletterTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(newsletterTable.save(this.newsletter1));
        assertFalse(newsletterTable.save(this.newsletter1));
        assertTrue(newsletterTable.save(this.newsletter2));
    }
    
    @Test
    void updateTest() {
        assertFalse(newsletterTable.update(this.newsletter1));
        newsletterTable.save(this.newsletter2);
        final Newsletter updatedNewsletter2 = new Newsletter(2,  "25369874102", Optional.of("be ciao"));
        assertTrue(newsletterTable.update(updatedNewsletter2));
		final Optional<Newsletter> foundNewsletter = newsletterTable.findByPrimaryKey(updatedNewsletter2.getIdNewsletter());
		assertFalse(foundNewsletter.isEmpty());
        assertEquals(updatedNewsletter2.getDescrizione(), foundNewsletter.get().getDescrizione());
    }

    @Test
    void deleteTest() {
        newsletterTable.save(this.newsletter1);
        assertTrue(newsletterTable.delete(this.newsletter1.getIdNewsletter()));
        assertFalse(newsletterTable.delete(this.newsletter1.getIdNewsletter()));
        assertTrue(newsletterTable.findByPrimaryKey(this.newsletter1.getIdNewsletter()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        newsletterTable.save(this.newsletter1);
        newsletterTable.save(this.newsletter2);
		assertEquals(this.newsletter1, newsletterTable
				.findByPrimaryKey(this.newsletter1.getIdNewsletter()).orElse(null));
		assertEquals(this.newsletter2, newsletterTable
				.findByPrimaryKey(this.newsletter2.getIdNewsletter()).orElse(null));
	}

    @Test
    void findAllTest() {
        newsletterTable.save(this.newsletter1);
        newsletterTable.save(this.newsletter2);
        assertIterableEquals(
            List.of(this.newsletter1, this.newsletter2),
            newsletterTable.findAll()
        );
    }
}
