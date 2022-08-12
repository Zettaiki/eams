package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.CategoryTable;
import model.Category;
import utils.ConnectionProvider;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class CategoryTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static CategoryTable categoryTable = new CategoryTable(connectionProvider.getMySQLConnection());

    final Category category1 = new Category("a", Optional.of("mail1@nicemail.it"));
    final Category category2 = new Category("b", Optional.empty());

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), categoryTable.getTableName());
        categoryTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), categoryTable.getTableName()));
        assertTrue(categoryTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(categoryTable.save(this.category1));
        assertFalse(categoryTable.save(this.category1));
        assertTrue(categoryTable.save(this.category2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(categoryTable.update(this.category1));
        categoryTable.save(this.category2);
		final Category updatedCategory2 = new Category("b", Optional.of("WOW"));
		assertTrue(categoryTable.update(updatedCategory2));
        final Optional<Category> foundCategory = categoryTable.findByPrimaryKey(updatedCategory2.getNome());
        assertFalse(foundCategory.isEmpty());
        assertEquals(updatedCategory2.getDescrizione(), foundCategory.get().getDescrizione());
    }

    @Test
    public void deleteTest() {
        categoryTable.save(this.category1);
        assertTrue(categoryTable.delete(this.category1.getNome()));
        assertFalse(categoryTable.delete(this.category1.getNome()));
        assertTrue(categoryTable.findByPrimaryKey(this.category1.getNome()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        categoryTable.save(this.category1);
        categoryTable.save(this.category2);
        assertEquals(this.category1, categoryTable.findByPrimaryKey(this.category1.getNome()).orElse(null));
        assertEquals(this.category2, categoryTable.findByPrimaryKey(this.category2.getNome()).orElse(null));
    }

    @Test
    public void findAllTest() {
        categoryTable.save(this.category1);
        categoryTable.save(this.category2);
        assertIterableEquals(
            List.of(this.category1, this.category2),
            categoryTable.findAll()
        );
    }
}
