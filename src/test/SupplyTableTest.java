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

import db.tables.SupplyTable;
import model.Supply;
import utils.ConnectionProvider;
import utils.TableTestUtils;
import utils.Utils;

public class SupplyTableTest {
    final static SupplyTable supplyTable = new SupplyTable(ConnectionProvider.getMySQLConnection());

    final Supply supply1 = new Supply("p1", new BigDecimal("12345678911"), Utils.buildDate(11, 12, 2021).get(), 20);
    final Supply supply2 = new Supply("p2", new BigDecimal("25369874102"), Utils.buildDate(11, 12, 2021).get(), 15);

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), supplyTable.getTableName());
        supplyTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), supplyTable.getTableName()));
        assertTrue(supplyTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(supplyTable.save(this.supply1));
        assertFalse(supplyTable.save(this.supply1));
        assertTrue(supplyTable.save(this.supply2));
    }
    
    @Test
    void updateTest() {
        assertFalse(supplyTable.update(this.supply1));
        supplyTable.save(this.supply2);
        final Supply updatedSupply2 = new Supply("p2", new BigDecimal("25369874102"), Utils.buildDate(11, 12, 2021).get(), 150);
        assertTrue(supplyTable.update(updatedSupply2));
		final Optional<Supply> foundSupply = supplyTable.findByPrimaryKey(
				updatedSupply2.getIdProdotto(), updatedSupply2.getPartitaIVA(),
				updatedSupply2.getData());
		assertFalse(foundSupply.isEmpty());
        assertEquals(updatedSupply2.getQuantitàFornita(), foundSupply.get().getQuantitàFornita());
    }

    @Test
    void deleteTest() {
        supplyTable.save(this.supply1);
        assertTrue(supplyTable.delete(
        		this.supply1.getIdProdotto(), this.supply1.getPartitaIVA(),
        		this.supply1.getData()));
        assertFalse(supplyTable.delete(
        		this.supply1.getIdProdotto(), this.supply1.getPartitaIVA(),
        		this.supply1.getData()));
        assertTrue(supplyTable.findByPrimaryKey(
        		this.supply1.getIdProdotto(), this.supply1.getPartitaIVA(),
        		this.supply1.getData()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        supplyTable.save(this.supply1);
        supplyTable.save(this.supply2);
		assertEquals(this.supply1, supplyTable
				.findByPrimaryKey(this.supply1.getIdProdotto(), this.supply1.getPartitaIVA(),
		        		this.supply1.getData())
				.orElse(null));
		assertEquals(this.supply2, supplyTable
				.findByPrimaryKey(this.supply2.getIdProdotto(), this.supply2.getPartitaIVA(),
						this.supply2.getData())
				.orElse(null));
	}

    @Test
    void findAllTest() {
        supplyTable.save(this.supply1);
        supplyTable.save(this.supply2);
        assertIterableEquals(
            List.of(this.supply1, this.supply2),
            supplyTable.findAll()
        );
    }
}
