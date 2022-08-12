package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.ConnectionProvider;
import db.tables.ServiceTable;
import model.Service;
import utils.ServerCredentials;
import utils.TableTestUtils;

public class ServiceTableTest {
	final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static ServiceTable serviceTable = new ServiceTable(connectionProvider.getMySQLConnection());

    final Service service1 = new Service("s1", "e1", Time.valueOf("18:45:20"), Time.valueOf("19:45:20"), "ag", Optional.of(1));
    final Service service2 = new Service("s2", "e2", Time.valueOf("18:00:00"), Time.valueOf("19:45:20"), "bg", Optional.empty());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), serviceTable.getTableName());
        serviceTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), serviceTable.getTableName()));
        assertTrue(serviceTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(serviceTable.save(this.service1));
        assertFalse(serviceTable.save(this.service1));
        assertTrue(serviceTable.save(this.service2));
    }
    
    @Test
    void updateTest() {
        assertFalse(serviceTable.update(this.service1));
        serviceTable.save(this.service2);
        final Service updatedService2 = new Service("s2", "e2", Time.valueOf("19:00:00"), Time.valueOf("19:45:20"), "bg", Optional.of(2));
        assertTrue(serviceTable.update(updatedService2));
        final Optional<Service> foundService = serviceTable.findByPrimaryKey(updatedService2.getIdServizio());
        assertFalse(foundService.isEmpty());
        assertEquals(updatedService2.getOraInizio(), foundService.get().getOraInizio());
    }

    @Test
    void deleteTest() {
        serviceTable.save(this.service1);
        assertTrue(serviceTable.delete(this.service1.getIdServizio()));
        assertFalse(serviceTable.delete(this.service1.getIdServizio()));
        assertTrue(serviceTable.findByPrimaryKey(this.service1.getIdServizio()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        serviceTable.save(this.service1);
        serviceTable.save(this.service2);
        assertEquals(this.service1, serviceTable.findByPrimaryKey(this.service1.getIdServizio()).orElse(null));
        assertEquals(this.service2, serviceTable.findByPrimaryKey(this.service2.getIdServizio()).orElse(null));
    }

    @Test
    void findAllTest() {
        serviceTable.save(this.service1);
        serviceTable.save(this.service2);
        assertIterableEquals(
            List.of(this.service1, this.service2),
            serviceTable.findAll()
        );
    }
}
