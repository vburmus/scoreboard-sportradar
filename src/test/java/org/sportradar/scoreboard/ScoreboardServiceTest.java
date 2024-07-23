package org.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ScoreboardServiceTest {
    private ScoreboardService service;

    @BeforeEach
    void setUp() {
        service = new ScoreboardService();
    }
    @Test
    void startMatch_validTeams_success() {
        service.startMatch("Team A", "Team B");
        assertEquals(1, service.getActiveMatches().size());
    }
}