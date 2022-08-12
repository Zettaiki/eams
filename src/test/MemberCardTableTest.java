package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import db.tables.MemberCardTable;
import utils.Utils;
import model.MemberCard;
import utils.ConnectionProvider;
import utils.ServerCredentials;
import utils.TableTestUtils;

class MemberCardTableTest {
    final static ConnectionProvider connectionProvider = new ConnectionProvider(ServerCredentials.USERNAME.getString(), 
    		ServerCredentials.PASSWORD.getString(), ServerCredentials.DBNAME.getString());
    final static MemberCardTable memberCardTable = new MemberCardTable(connectionProvider.getMySQLConnection());

    final MemberCard memberCard1 = new MemberCard("1", "AAAAAAAAAAAAAAAA", Utils.buildDate(11, 10, 2022).get());
    final MemberCard memberCard2 = new MemberCard("2", "BBBBBBBBBBBBBBBB", Utils.buildDate(11, 12, 2021).get());

    @BeforeEach
    void setUp() throws Exception {
    	TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), memberCardTable.getTableName());
        memberCardTable.createTable();
    }

    @Test
    void creationAndDropTest() {
    	assertTrue(TableTestUtils.dropTable(connectionProvider.getMySQLConnection(), memberCardTable.getTableName()));
        assertTrue(memberCardTable.createTable());
    }
    
    @Test
    void saveTest() {
        assertTrue(memberCardTable.save(this.memberCard1));
        assertFalse(memberCardTable.save(this.memberCard1));
        assertTrue(memberCardTable.save(this.memberCard2));
    }
    
    @Test
    void updateTest() {
        assertFalse(memberCardTable.update(this.memberCard1));
        memberCardTable.save(this.memberCard2);
        final MemberCard updatedMemberCard2 = new MemberCard("2", "BBBBBBBBBBBBBBBB", Utils.buildDate(13, 12, 2021).get());
        assertTrue(memberCardTable.update(updatedMemberCard2));
        final Optional<MemberCard> foundMemberCard = memberCardTable.findByPrimaryKey(updatedMemberCard2.getIdSocio());
        assertFalse(foundMemberCard.isEmpty());
        assertEquals(updatedMemberCard2.getDataAssociazione(), foundMemberCard.get().getDataAssociazione());
    }

    @Test
    void deleteTest() {
        memberCardTable.save(this.memberCard1);
        assertTrue(memberCardTable.delete(this.memberCard1.getIdSocio()));
        assertFalse(memberCardTable.delete(this.memberCard1.getIdSocio()));
        assertTrue(memberCardTable.findByPrimaryKey(this.memberCard1.getIdSocio()).isEmpty());
    }

    @Test
    void findByPrimaryKeyTest() {
        memberCardTable.save(this.memberCard1);
        memberCardTable.save(this.memberCard2);
        assertEquals(this.memberCard1, memberCardTable.findByPrimaryKey(this.memberCard1.getIdSocio()).orElse(null));
        assertEquals(this.memberCard2, memberCardTable.findByPrimaryKey(this.memberCard2.getIdSocio()).orElse(null));
    }

    @Test
    void findAllTest() {
        memberCardTable.save(this.memberCard1);
        memberCardTable.save(this.memberCard2);
        assertIterableEquals(
            List.of(this.memberCard1, this.memberCard2),
            memberCardTable.findAll()
        );
    }
}
