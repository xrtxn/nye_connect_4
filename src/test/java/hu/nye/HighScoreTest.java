package hu.nye;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

//generated with GitHub copilot
public class HighScoreTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    private HighScore highScore;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        highScore = new HighScore();
        highScore.setConnection(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.execute(anyString())).thenReturn(true);
        when(mockStatement.getResultSet()).thenReturn(mockResultSet);
    }

    @Test
    void insertUserInsertsNewUserWhenNotExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        highScore.insertUser("newUser");
        verify(mockStatement, times(1)).execute("INSERT INTO users (name) VALUES ('newUser')");
    }

    @Test
    void insertUserDoesNotInsertExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        highScore.insertUser("existingUser");
        verify(mockStatement, never()).execute("INSERT INTO users (name) VALUES ('existingUser')");
    }

    @Test
    void increaseHighScoreIncreasesScoreForExistingUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("score")).thenReturn(5);
        highScore.increaseHighScore("existingUser");
        verify(mockStatement, times(1)).execute("UPDATE highscore INNER JOIN users ON highscore.nameId = users.id SET score = 6 WHERE users.name = 'existingUser'");
    }

    @Test
    void increaseHighScoreInsertsNewScoreForNewUser() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);
        highScore.increaseHighScore("newUser");
        verify(mockStatement, times(1)).execute("INSERT INTO highscore (nameId, score) SELECT id, 1 FROM users WHERE name = 'newUser'");
    }

    @Test
    void showHighScoreDisplaysTopScores() throws SQLException {
        when(mockResultSet.next()).thenReturn(true, true, true, true, true, false);
        when(mockResultSet.getString("name")).thenReturn("user1", "user2", "user3", "user4", "user5");
        when(mockResultSet.getInt("score")).thenReturn(10, 9, 8, 7, 6);
        highScore.showHighScore();
        verify(mockResultSet, times(5)).getString("name");
        verify(mockResultSet, times(5)).getInt("score");
    }
}