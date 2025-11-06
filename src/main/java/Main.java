import database.Database;

public class Main {
    public static void main(String[] args) {
        Database.ensureInitialized();
        System.out.println("Database initialized (tournament.db created).");
    }
}
