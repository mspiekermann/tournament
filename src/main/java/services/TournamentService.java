package services;

import model.Config;
import model.Match;
import model.Result;
import model.Team;

import java.util.List;

public interface TournamentService {

    Team addTeam(String name);

    List<Team> listTeams();

    Config getConfig();

    Config updateConfig(int matchDays, int advanceCount);

    void generateSchedule();

    List<Match> listMatches();

    void setMatchResult(int matchId, Integer homeGoals, Integer awayGoals);

    List<Result> computeStandings();

    List<Result> knockoutQualifiers();
}

