package database;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class TestDatabaseUtil {

    public static Connection newInMemoryDatabase() throws Exception {
        Connection connection = DriverManager.getConnection("jdbc:sqlite::memory:");
        applySchema(connection);
        return connection;
    }

    private static void applySchema(Connection connection) throws Exception {
        String schema = Files.readString(
                Paths.get(Database.class.getClassLoader().getResource("schema.sql").toURI()));
        try (Statement st = connection.createStatement()) {
            st.executeUpdate(schema);
        } catch (Exception e) {
            throw new RuntimeException("Failed to init DB", e);
        }
    }
}

