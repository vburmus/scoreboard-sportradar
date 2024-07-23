package org.sportradar.scoreboard;

import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ScoreboardService {
    private final Set<Match> activeMatches = new HashSet<>();

    public void startMatch(String homeTeam, String awayTeam) {
        activeMatches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public List<String> getSummary() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}