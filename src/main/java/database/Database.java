package database;

import java.nio.file.*;
import java.sql.*;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:tournament.db";
    private static boolean initialized = false;

    static {
        init();
    }

    private static void init() {
        try (Connection con = getConnection()) {
            String schema = Files.readString(
                    Paths.get(Database.class.getClassLoader().getResource("schema.sql").toURI())
            );
            try (Statement st = con.createStatement()) {
                st.executeUpdate(schema);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to init DB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void ensureInitialized() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }
}
