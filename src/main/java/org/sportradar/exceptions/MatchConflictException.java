package org.sportradar.exceptions;

public class MatchConflictException extends IllegalArgumentException {

    public static final String MATCH_CONFLICT_MESSAGE = "A match involving %s already exists.";

    public MatchConflictException(String teamName) {
        super(MATCH_CONFLICT_MESSAGE.formatted(teamName));
    }
}