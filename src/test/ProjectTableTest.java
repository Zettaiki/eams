package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.ProjectTable;
import model.Project;
import utils.ConnectionProvider;
import utils.TableTestUtils;
import utils.Utils;

public class ProjectTableTest {
	final static ProjectTable projectTable = new ProjectTable(ConnectionProvider.getMySQLConnection());

	final Project project1 = new Project("m", Utils.buildDate(11, 10, 2022).get(), 4);
	final Project project2 = new Project("a", Utils.buildDate(11, 12, 2021).get(), 5);

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), projectTable.getTableName());
        projectTable.createTable();
    }

    @Test
    void creationAndDropTest() {
        assertTrue(TableTestUtils.dropTable(ConnectionProvider.getMySQLConnection(), projectTable.getTableName()));
        assertTrue(projectTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(projectTable.save(this.project1));
        //assertFalse(projectTable.save(this.project1)); non serve perch� non inserisco pk
        assertTrue(projectTable.save(this.project2));
    }
    
    @Test
    void updateTest() {
        assertFalse(projectTable.update(this.project1));
        projectTable.save(this.project2);
        final Project updatedProject2 = new Project("a", Utils.buildDate(11, 12, 2011).get(), 5);
        assertTrue(projectTable.update(updatedProject2));
        final Optional<Project> foundProgetto = projectTable.findByPrimaryKey(updatedProject2.getIdProgetto());
        assertFalse(foundProgetto.isEmpty());
        assertEquals(updatedProject2.getDataInizio(), foundProgetto.get().getDataInizio());
    }

    @Test
    void deleteTest() {
        projectTable.save(this.project1);
        assertTrue(projectTable.delete(this.project1.getIdProgetto()));
        assertFalse(projectTable.delete(this.project1.getIdProgetto()));
        assertTrue(projectTable.findByPrimaryKey(this.project1.getIdProgetto()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        projectTable.save(this.project1);
        projectTable.save(this.project2);
        System.out.println("" + this.project2.getIdProgetto());
        assertEquals(this.project1, projectTable.findByPrimaryKey(this.project1.getIdProgetto()).orElse(null));
        assertEquals(this.project2, projectTable.findByPrimaryKey(this.project2.getIdProgetto()).orElse(null));
    }

    @Test
    void findAllTest() {
        projectTable.save(this.project1);
        projectTable.save(this.project2);
        assertIterableEquals(
            List.of(this.project1, this.project2),
            projectTable.findAll()
        );
    }
}
