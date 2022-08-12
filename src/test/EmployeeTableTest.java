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

import db.tables.EmployeeTable;
import model.Employee;
import utils.ConnectionProvider;
import utils.TableTestUtils;
import utils.Utils;

public class EmployeeTableTest {
    final static EmployeeTable employeeTable = new EmployeeTable(ConnectionProvider.getMySQLConnection());

	final Employee employee1 = new Employee("AAAAAAAAAAAAAAAA", "Roma", Utils.buildDate(11, 12, 2022).get(),
			new BigDecimal("1000.00"));
	final Employee employee2 = new Employee("BBBBBBBBBBBBBBBB", "Bari", Utils.buildDate(11, 12, 2021).get(),
			new BigDecimal("2000.00"));

    @BeforeEach
	public void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), employeeTable.getTableName());
        employeeTable.createTable();
    }

    @Test
	public void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), employeeTable.getTableName()));
        assertTrue(employeeTable.createTable());
    }
    
    @Test
    public void saveTest() {
        assertTrue(employeeTable.save(this.employee1));
        assertFalse(employeeTable.save(this.employee1));
        assertTrue(employeeTable.save(this.employee2));
    }
    
    @Test
    public void updateTest() {
        assertFalse(employeeTable.update(this.employee1));
        employeeTable.save(this.employee2);
		final Employee updatedEmployee2 = new Employee("BBBBBBBBBBBBBBBB", "Bari", Utils.buildDate(11, 12, 2021).get(),
				new BigDecimal("1800.00"));
		assertTrue(employeeTable.update(updatedEmployee2));
        final Optional<Employee> foundEmployee = employeeTable.findByPrimaryKey(updatedEmployee2.getCodiceFiscale());
        assertFalse(foundEmployee.isEmpty());
        assertEquals(updatedEmployee2.getSalario(), foundEmployee.get().getSalario());
    }

    @Test
    public void deleteTest() {
        employeeTable.save(this.employee1);
        assertTrue(employeeTable.delete(this.employee1.getCodiceFiscale()));
        assertFalse(employeeTable.delete(this.employee1.getCodiceFiscale()));
        assertTrue(employeeTable.findByPrimaryKey(this.employee1.getCodiceFiscale()).isEmpty());
    }

    @Test
    public void findByPrimaryKeyTest() {
        employeeTable.save(this.employee1);
        employeeTable.save(this.employee2);
        assertEquals(this.employee1, employeeTable.findByPrimaryKey(this.employee1.getCodiceFiscale()).orElse(null));
        assertEquals(this.employee2, employeeTable.findByPrimaryKey(this.employee2.getCodiceFiscale()).orElse(null));
    }

    @Test
    public void findAllTest() {
        employeeTable.save(this.employee1);
        employeeTable.save(this.employee2);
        assertIterableEquals(
            List.of(this.employee1, this.employee2),
            employeeTable.findAll()
        );
    }
}
