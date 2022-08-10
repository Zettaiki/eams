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
import db.tables.DonationTable;
import model.Donation;
import utils.ServerCredentials;
import utils.TableTestUtils;
import utils.Utils;

public class DonationTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(),
			ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
	final static DonationTable donationTable = new DonationTable(connectionProvider.getMySQLConnection());

	final Donation donation1 = new Donation(new BigDecimal("22.20"), "AAAAAAAAAAAAAAAA",
			Utils.buildDate(11, 10, 2022).get(), Optional.of(1));
	final Donation donation2 = new Donation(new BigDecimal("3333.33"), "BBBBBBBBBBBBBBBB",
			Utils.buildDate(11, 12, 2021).get(), Optional.of(2));

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), donationTable.getTableName());
        donationTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), donationTable.getTableName()));
        assertTrue(donationTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(donationTable.save(this.donation1));
        //assertFalse(donationTable.save(this.donation1)); non serve perché non inserisco pk
        assertTrue(donationTable.save(this.donation2));
    }
    
    @Test
    void updateTest() {
        assertFalse(donationTable.update(this.donation1));
        donationTable.save(this.donation2);
        final Donation updatedDonation2 = new Donation(new BigDecimal("7777.77"), "BBBBBBBBBBBBBBBB",
    			Utils.buildDate(11, 12, 2021).get(), Optional.of(2));
        assertTrue(donationTable.update(updatedDonation2));
        final Optional<Donation> foundDonazione = donationTable.findByPrimaryKey(updatedDonation2.getIdDonazione());
        assertFalse(foundDonazione.isEmpty());
        assertEquals(updatedDonation2.getImporto(), foundDonazione.get().getImporto());
    }

    @Test
    void deleteTest() {
        donationTable.save(this.donation1);
        assertTrue(donationTable.delete(this.donation1.getIdDonazione()));
        assertFalse(donationTable.delete(this.donation1.getIdDonazione()));
        assertTrue(donationTable.findByPrimaryKey(this.donation1.getIdDonazione()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        donationTable.save(this.donation1);
        donationTable.save(this.donation2);
        assertEquals(this.donation1, donationTable.findByPrimaryKey(this.donation1.getIdDonazione()).orElse(null));
        assertEquals(this.donation2, donationTable.findByPrimaryKey(this.donation2.getIdDonazione()).orElse(null));
    }

    @Test
    void findAllTest() {
        donationTable.save(this.donation1);
        donationTable.save(this.donation2);
        assertIterableEquals(
            List.of(this.donation1, this.donation2),
            donationTable.findAll()
        );
    }
}
