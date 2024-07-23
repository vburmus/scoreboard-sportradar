package org.sportradar.utils;

import java.util.Arrays;

public class ScoreboardUtils {
    public static void validateTeamNames(String homeTeam, String awayTeam) {
        if (homeTeam == null || awayTeam == null) {
            throw new IllegalArgumentException("Team names must not be null");
        }
        if (homeTeam.isBlank() || awayTeam.isBlank()) {
            throw new IllegalArgumentException("Team names must not be blank");
        }
        if (homeTeam.equals(awayTeam)) {
            throw new IllegalArgumentException("Teams must be different");
        }
    }

    public static void validateScores(int... scores) {
        if (Arrays.stream(scores).anyMatch(score -> score < 0)) {
            throw new IllegalArgumentException("Scores must be non-negative");
        }
    }
}