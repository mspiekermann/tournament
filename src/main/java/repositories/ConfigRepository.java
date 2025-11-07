package repositories;

import model.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConfigRepository {

    private final Connection connection;

    public ConfigRepository(Connection connection) {
        this.connection = connection;
    }

    public Config get() {
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT match_days, advance_count FROM config WHERE id = ?"
        )) {
            ps.setInt(1, 1); //only one tournament for now
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Config(rs.getInt("match_days"), rs.getInt("advance_count"));
            }
            throw new RuntimeException("Config row not found");
        } catch (SQLException e) {
            throw new RuntimeException("Could not read config", e);
        }
    }

    public Config update(int matchDays, int advanceCount) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE config SET match_days = ?, advance_count = ? WHERE id = ?"
        )) {
            ps.setInt(1, matchDays);
            ps.setInt(2, advanceCount);
            ps.setInt(3, 1); //only one tournament for now
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update config", e);
        }
        return get();
    }
}
