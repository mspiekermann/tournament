package model;

public record Result(
    int teamId,
    String teamName,
    int played,
    int won,
    int drawn,
    int lost,
    int goalsFor,
    int goalsAgainst,
    int goalDiff,
    int points
) {}
