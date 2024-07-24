package org.sportradar.scoreboard;

import org.sportradar.exceptions.MatchConflictException;
import org.sportradar.exceptions.MatchNotFoundException;
import org.sportradar.utils.ScoreboardUtils;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ScoreboardService {
    private final Set<Match> activeMatches = new HashSet<>();

    public void startMatch(String homeTeam, String awayTeam) {
        ScoreboardUtils.validateTeamNames(homeTeam, awayTeam);
        // Trim the team names to remove leading and trailing whitespaces
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
        // Sort the matches by total score and start time in descending order and return the list of match summaries
        return activeMatches.stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore)
                        .thenComparingLong(Match::getStartTime)
                        .reversed())
                .map(Match::toString)
                .toList();
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