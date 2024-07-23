package org.sportradar.exceptions;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String homeTeam, String awayTeam) {
        super("Match not found between " + homeTeam + " and " + awayTeam);
    }
}