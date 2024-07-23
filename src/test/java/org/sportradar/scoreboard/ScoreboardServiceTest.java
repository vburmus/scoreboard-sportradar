package org.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.exceptions.MatchConflictException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ScoreboardServiceTest {
    public static final String TEAM_A = "Team A";
    public static final String TEAM_B = "Team B";
    private ScoreboardService service;

    @BeforeEach
    void setUp() {
        service = new ScoreboardService();
    }

    @Test
    void startMatch_validTeams_success() {
        //when
        service.startMatch(TEAM_A, TEAM_B);
        //then
        assertEquals(1, service.getActiveMatches().size());
    }

    @Test
    void startMatch_invalidTeamNames_throwsException() {
        //when
        var nullNameException = assertThrows(IllegalArgumentException.class,
                () -> service.startMatch(null, TEAM_B));
        var blankNameException = assertThrows(IllegalArgumentException.class,
                () -> service.startMatch("", TEAM_B));
        var sameNameException = assertThrows(IllegalArgumentException.class,
                () -> service.startMatch(TEAM_A, TEAM_A));
        //then
        assertEquals("Team names must not be null", nullNameException.getMessage());
        assertEquals("Team names must not be blank", blankNameException.getMessage());
        assertEquals("Teams must be different", sameNameException.getMessage());
    }

    @Test
    void startMatch_existingMatch_throwsException() {
        //given
        String anotherAwayTeam = "Team C";
        service.startMatch(TEAM_A, TEAM_B);
        //when
        var repeatingTeamException = assertThrows(MatchConflictException.class,
                () -> service.startMatch(TEAM_A, anotherAwayTeam));
        //then
        assertEquals(MatchConflictException.MATCH_CONFLICT_MESSAGE.formatted(TEAM_A), repeatingTeamException.getMessage());
    }
}