package repositories;

import database.TestDatabaseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigRepositoryTest {

    private ConfigRepository repo;

    @BeforeEach
    void setUp() throws Exception {
        var connection = TestDatabaseUtil.newInMemoryDatabase();
        repo = new ConfigRepository(connection);
    }

    @Test
    void readDefaultConfig() {
        var cfg = repo.get();
        assertNotNull(cfg);
        assertEquals(8, cfg.matchDays());      // from schema.sql default
        assertEquals(8, cfg.advanceCount());
    }

    @Test
    void updateConfig() {
        var updated = repo.update(10, 4);
        assertEquals(10, updated.matchDays());
        assertEquals(4, updated.advanceCount());
    }
}
