package hu.nye;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(6, 7);
    }

    @Test
    void switchPlayerChangesFromPlayer1ToRobot() {
        game.setCurrentPlayer(GameCharacters.PLAYER1);
        game.switchPlayer();
        assertEquals(GameCharacters.ROBOT, game.getCurrentPlayer());
    }

    @Test
    void switchPlayerChangesFromRobotToPlayer1() {
        game.setCurrentPlayer(GameCharacters.ROBOT);
        game.switchPlayer();
        assertEquals(GameCharacters.PLAYER1, game.getCurrentPlayer());
    }

    @Test
    void botMoveReturnsValidColumn() {
        int column = game.botMove();
        Assertions.assertTrue(column >= 1 && column <= game.getColumns());
    }

    @Test
    void getNextPlayerReturnsRobotWhenCurrentPlayerIsPlayer1() {
        game.setCurrentPlayer(GameCharacters.PLAYER1);
        assertEquals(GameCharacters.ROBOT, game.getNextPlayer());
    }

    @Test
    void getNextPlayerReturnsPlayer1WhenCurrentPlayerIsRobot() {
        game.setCurrentPlayer(GameCharacters.ROBOT);
        assertEquals(GameCharacters.PLAYER1, game.getNextPlayer());
    }
}