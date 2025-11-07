package repositories;

import database.TestDatabaseUtil;
import model.Match;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MatchRepositoryTest {

    MatchRepository matchRepository;
    TeamRepository teamRepository;

    @BeforeEach
    void setUp() throws Exception {
        var connection = TestDatabaseUtil.newInMemoryDatabase();
        matchRepository = new MatchRepository(connection);
        teamRepository = new TeamRepository(connection);

        // we need teams because match table references them
        teamRepository.insert("Schalke 04");
        teamRepository.insert("Borussia Dortmund");
    }

    @Test
    void insertAndListMatches() {
        // id has AUTOINCREMENT
        matchRepository.insertMatch(1, 1, 2);

        List<Match> matches = matchRepository.findAll();
        assertEquals(1, matches.size());
        Match m = matches.getFirst();
        assertEquals(1, m.matchDay());
        assertEquals("Schalke 04", m.homeTeamName());
        assertEquals("Borussia Dortmund", m.awayTeamName());
        assertNull(m.homeGoals());
        assertNull(m.awayGoals());
    }

    @Test
    void updateMatchResult() {
        matchRepository.insertMatch(1, 1, 2);
        List<Match> matches = matchRepository.findAll();
        int matchId = matches.getFirst().id();

        matchRepository.updateResult(matchId, 3, 1);

        List<Match> checkUpdate = matchRepository.findAll();
        Match m2 = checkUpdate.getFirst();
        assertEquals(3, m2.homeGoals());
        assertEquals(1, m2.awayGoals());
    }

    @Test
    void deleteAllClearsTable() {
        matchRepository.insertMatch(1, 1, 2);
        matchRepository.insertMatch(2, 1, 2);

        matchRepository.deleteAll();

        List<Match> matches = matchRepository.findAll();
        assertTrue(matches.isEmpty());
    }
}
