package model;

public record Match(
    int id,
    int matchDay,
    int homeTeamId,
    int awayTeamId,
    String homeTeamName,
    String awayTeamName,
    Integer homeGoals,
    Integer awayGoals
) {}
