package repositories;

import model.Team;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamRepository {

    private final Connection connection;

    public TeamRepository(Connection connection) {
        this.connection = connection;
    }

    public Team insert(String name) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO team(name) VALUES (?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            ps.setString(1, name);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Team(rs.getInt(1), name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not insert team", e);
        }
        throw new RuntimeException("No generated key returned");
    }

    public List<Team> findAll() {
        List<Team> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT id, name FROM team ORDER BY name ASC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Team(rs.getInt("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not list teams", e);
        }
        return result;
    }

}
