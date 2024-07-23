package org.sportradar.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sportradar.exceptions.MatchConflictException;
import org.sportradar.exceptions.MatchNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ScoreboardServiceTest {
    public static final String TEAM_A = "Team A";
    public static final String TEAM_B = "Team B";
    public static final String TEAM_C = "Team C";
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
    void startMatch_existingMatchWithWhitespaces_throwsException() {
        //given
        service.startMatch(TEAM_A, TEAM_B);
        //when
        var repeatingTeamException = assertThrows(MatchConflictException.class,
                () -> service.startMatch(" " + TEAM_A + " ", TEAM_C));
        //then
        assertEquals(MatchConflictException.MATCH_CONFLICT_MESSAGE.formatted(TEAM_A), repeatingTeamException.getMessage());
    }

    @Test
    void updateScore_existingTeamsAndValidScores_success() {
        //given
        service.startMatch(TEAM_A, TEAM_B);
        //when
        service.updateScore(TEAM_A, TEAM_B, 1, 2);
        //then
        var expectedMatch = new Match(TEAM_A, TEAM_B);
        expectedMatch.setHomeScore(1);
        expectedMatch.setAwayScore(2);
        assertEquals(expectedMatch, service.getActiveMatches().toArray()[0]);
    }

    @Test
    void updateScore_matchNotFound_throwException() {
        assertThrows(MatchNotFoundException.class,
                () -> service.updateScore(TEAM_A, TEAM_B, 1, 2));
    }

    @Test
    void updateScore_invalidScores_throwException() {
        //when
        service.startMatch(TEAM_A, TEAM_C);
        //then
        assertThrows(IllegalArgumentException.class,
                () -> service.updateScore(TEAM_A, TEAM_C, 0, -8));
    }

    @Test
    void finishMatch_matchExists_success() {
        //given
        service.startMatch(TEAM_A, TEAM_B);
        int sizeBeforeFinish = service.getActiveMatches().size();
        //when
        service.finishMatch(TEAM_A, TEAM_B);
        //then
        assertEquals(1, sizeBeforeFinish);
        assertEquals(0, service.getActiveMatches().size());
    }

    @Test
    void finishMatch_matchNotFound_throwsException() {
        assertThrows(MatchNotFoundException.class, () -> service.finishMatch(TEAM_A, TEAM_B));
    }
}