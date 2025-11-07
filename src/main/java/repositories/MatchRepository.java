package repositories;

import model.Match;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MatchRepository {
    private final Connection connection;

    public MatchRepository(Connection connection) { this.connection = connection; }

    public void insertMatch(int matchDay, int homeTeamId, int awayTeamId) {
        try (PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO match(match_day, home_team_id, away_team_id) VALUES (?,?,?)"
        )) {
            ps.setInt(1, matchDay);
            ps.setInt(2, homeTeamId);
            ps.setInt(3, awayTeamId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not insert match", e);
        }
    }

    public List<Match> findAll() {
        List<Match> result = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement(
                "SELECT m.id, m.match_day, m.home_team_id, m.away_team_id, " +
                        "m.home_goals, m.away_goals, " +
                        "th.name AS home_name, ta.name AS away_name " +
                        "FROM match m " +
                        "JOIN team th ON th.id = m.home_team_id " +
                        "JOIN team ta ON ta.id = m.away_team_id " +
                        "ORDER BY m.match_day ASC, m.id ASC"
        );
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.add(new Match(
                        rs.getInt("id"),
                        rs.getInt("match_day"),
                        rs.getInt("home_team_id"),
                        rs.getInt("away_team_id"),
                        rs.getString("home_name"),
                        rs.getString("away_name"),
                        (Integer) rs.getObject("home_goals"),
                        (Integer) rs.getObject("away_goals")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Could not list matches", e);
        }
        return result;
    }

    public void updateResult(int matchId, Integer homeGoals, Integer awayGoals) {
        try (PreparedStatement ps = connection.prepareStatement(
                "UPDATE match SET home_goals = ?, away_goals = ? WHERE id = ?"
        )) {
            if (homeGoals == null) {
                ps.setNull(1, Types.INTEGER);
            } else {
                ps.setInt(1, homeGoals);
            }
            if (awayGoals == null) {
                ps.setNull(2, Types.INTEGER);
            } else {
                ps.setInt(2, awayGoals);
            }
            ps.setInt(3, matchId);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Could not update match result", e);
        }
    }

}
