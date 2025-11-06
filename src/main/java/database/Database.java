package database;

import java.nio.file.*;
import java.sql.*;

public class Database {

    private static final String DB_URL = "jdbc:sqlite:tournament.db";
    private static boolean initialized = false;

    private static Connection connection;

    static {
        init();
    }

    private static void init() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            String schema = Files.readString(
                    Paths.get(Database.class.getClassLoader().getResource("schema.sql").toURI())
            );
            try (Statement st = connection.createStatement()) {
                st.executeUpdate(schema);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to init DB", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return connection;
    }

    public static void ensureInitialized() {
        if (!initialized) {
            init();
            initialized = true;
        }
    }

    public static void close() {
        try {
            connection.close();
            System.out.println("SQLite connection closed.");
        } catch (Exception e) {
            System.err.println("Failed to close DB: " + e.getMessage());
        }
    }
}
