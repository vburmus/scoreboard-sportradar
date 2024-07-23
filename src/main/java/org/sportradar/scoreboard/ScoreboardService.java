package org.sportradar.scoreboard;

import lombok.Getter;
import org.sportradar.exceptions.MatchConflictException;
import org.sportradar.exceptions.MatchNotFoundException;
import org.sportradar.utils.ScoreboardUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
public class ScoreboardService {
    private final Set<Match> activeMatches = new HashSet<>();

    public void startMatch(String homeTeam, String awayTeam) {
        ScoreboardUtils.validateTeamNames(homeTeam, awayTeam);
        homeTeam = homeTeam.trim();
        awayTeam = awayTeam.trim();
        ensureMatchDoesNotExist(homeTeam, awayTeam);

        activeMatches.add(new Match(homeTeam, awayTeam));
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        ScoreboardUtils.validateScores(homeScore, awayScore);

        Match match = findMatch(homeTeam, awayTeam);
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        Match match = findMatch(homeTeam, awayTeam);
        activeMatches.remove(match);
    }

    public List<String> getSummary() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    private Match findMatch(String homeTeam, String awayTeam) {
        return activeMatches.stream()
                .filter(match -> match.getHomeTeam().equals(homeTeam) && match.getAwayTeam().equals(awayTeam))
                .findFirst()
                .orElseThrow(() -> new MatchNotFoundException(homeTeam, awayTeam));
    }

    private void ensureMatchDoesNotExist(String homeTeam, String awayTeam) {
        if (isTeamInMatch(homeTeam)) {
            throw new MatchConflictException(homeTeam);
        }
        if (isTeamInMatch(awayTeam)) {
            throw new MatchConflictException(awayTeam);
        }
    }

    private boolean isTeamInMatch(String team) {
        return activeMatches.stream()
                .anyMatch(match -> match.getHomeTeam().equals(team) || match.getAwayTeam().equals(team));
    }
}