package services;

import model.Config;
import model.Match;
import model.Result;
import model.Team;
import repositories.ConfigRepository;
import repositories.MatchRepository;
import repositories.TeamRepository;

import java.util.List;

public class DefaultTournamentService implements TournamentService {

    TeamRepository teamRepository;
    MatchRepository matchRepository;
    ConfigRepository configRepository;

    DefaultTournamentService(TeamRepository teamRepository, MatchRepository matchRepository, ConfigRepository configRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
        this.configRepository = configRepository;
    }

    @Override
    public Team addTeam(String name) {
        return teamRepository.insert(name.trim());
    }

    @Override
    public List<Team> listTeams() {
        return teamRepository.findAll();
    }

    @Override
    public Config getConfig() {
        return configRepository.get();
    }

    @Override
    public Config updateConfig(int matchDays, int advanceCount) {
        return configRepository.update(matchDays, advanceCount);
    }

    @Override
    public void generateSchedule() {

    }

    @Override
    public List<Match> listMatches() {
        return matchRepository.findAll();
    }

    @Override
    public void setMatchResult(int matchId, Integer homeGoals, Integer awayGoals) {
        matchRepository.insertMatch(matchId, homeGoals, awayGoals);
    }

    @Override
    public List<Result> computeStandings() {
        return List.of();
    }

    @Override
    public List<Result> knockoutQualifiers() {
        return List.of();
    }
}
