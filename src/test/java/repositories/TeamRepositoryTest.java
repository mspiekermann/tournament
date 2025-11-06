package repositories;

import database.TestDatabaseUtil;
import model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamRepositoryTest {
    private TeamRepository repo;

    @BeforeEach
    void setUp() throws Exception {
        var connection = TestDatabaseUtil.newInMemoryDatabase();
        repo = new TeamRepository(connection);
    }

    @Test
    void insertAndListTeams() {
        var t1 = repo.insert("Schalke 04");
        var t2 = repo.insert("VFB Stuttgart");

        assertNotNull(t1);
        assertTrue(t1.id() > 0);

        List<Team> teams = repo.findAll();
        assertEquals(2, teams.size());
        assertEquals("Bayern", teams.get(0).name());
        assertEquals("Real Madrid", teams.get(1).name());
    }
}
