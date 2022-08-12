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

import db.tables.ProductTable;
import model.Product;
import utils.ConnectionProvider;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class ProductTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static ProductTable productTable = new ProductTable(connectionProvider.getMySQLConnection());

	final Product product1 = new Product("p1", "a", "borraccia", new BigDecimal("20.90"), 20, "raccolta rifiuti",
			Optional.of("molto belle"));
	final Product product2 = new Product("p2", "b", "cappellino", new BigDecimal("10.50"), 50, "fornito",
			Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), productTable.getTableName());
        productTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), productTable.getTableName()));
        assertTrue(productTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(productTable.save(this.product1));
        assertFalse(productTable.save(this.product1));
        assertTrue(productTable.save(this.product2));
    }
    
    @Test
    void updateTest() {
        assertFalse(productTable.update(this.product1));
        productTable.save(this.product2);
        final Product updatedProduct2 = new Product("p2", "b", "cappellino", new BigDecimal("10.50"), 50, "fornito",
    			Optional.of("AAAAA"));
        assertTrue(productTable.update(updatedProduct2));
        final Optional<Product> foundProduct = productTable.findByPrimaryKey(updatedProduct2.getIdProdotto());
        assertFalse(foundProduct.isEmpty());
        assertEquals(updatedProduct2.getNome(), foundProduct.get().getNome());
        assertEquals(updatedProduct2.getDescrizione(), foundProduct.get().getDescrizione());
    }

    @Test
    void deleteTest() {
        productTable.save(this.product1);
        assertTrue(productTable.delete(this.product1.getIdProdotto()));
        assertFalse(productTable.delete(this.product1.getIdProdotto()));
        assertTrue(productTable.findByPrimaryKey(this.product1.getIdProdotto()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        productTable.save(this.product1);
        productTable.save(this.product2);
        assertEquals(this.product1, productTable.findByPrimaryKey(this.product1.getIdProdotto()).orElse(null));
        assertEquals(this.product2, productTable.findByPrimaryKey(this.product2.getIdProdotto()).orElse(null));
    }

    @Test
    void findAllTest() {
        productTable.save(this.product1);
        productTable.save(this.product2);
        assertIterableEquals(
            List.of(this.product1, this.product2),
            productTable.findAll()
        );
    }
}
