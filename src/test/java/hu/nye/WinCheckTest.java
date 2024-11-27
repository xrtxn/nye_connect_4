package hu.nye;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WinCheckTest {
    @Test
    void checkIfWinner_PlayerWinsHorizontally() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 2, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 3, GameCharacters.PLAYER1);
        game.checkIfWinner(GameCharacters.PLAYER1);
        Assertions.assertEquals(GameState.PLAYER_WON, game.getState());
    }

    @Test
    void checkIfWinner_PlayerWinsVertically() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(3, 0, GameCharacters.PLAYER1);
        game.checkIfWinner(GameCharacters.PLAYER1);
        Assertions.assertEquals(GameState.PLAYER_WON, game.getState());
    }

    @Test
    void checkIfWinner_PlayerWinsDiagonally() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 2, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(3, 3, GameCharacters.PLAYER1);
        game.checkIfWinner(GameCharacters.PLAYER1);
        Assertions.assertEquals(GameState.PLAYER_WON, game.getState());
    }

    @Test
    void checkIfWinner_NoWinner() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 2, GameCharacters.PLAYER1);
        game.checkIfWinner(GameCharacters.PLAYER1);
        Assertions.assertNotEquals(GameState.PLAYER_WON, game.getState());
    }

    @Test
    void checkHorizontal_WinningConditionMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 2, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 3, GameCharacters.PLAYER1);
        Assertions.assertTrue(game.checkHorizontal(0, 0, GameCharacters.PLAYER1));
    }

    @Test
    void checkHorizontal_WinningConditionNotMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(0, 2, GameCharacters.PLAYER1);
        Assertions.assertFalse(game.checkHorizontal(0, 0, GameCharacters.PLAYER1));
    }

    @Test
    void checkVertical_WinningConditionMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(3, 0, GameCharacters.PLAYER1);
        Assertions.assertTrue(game.checkVertical(0, 0, GameCharacters.PLAYER1));
    }

    @Test
    void checkVertical_WinningConditionNotMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 0, GameCharacters.PLAYER1);
        Assertions.assertFalse(game.checkVertical(0, 0, GameCharacters.PLAYER1));
    }

    @Test
    void checkDiagonal_WinningConditionMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 2, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(3, 3, GameCharacters.PLAYER1);
        Assertions.assertTrue(game.checkDiagonal(0, 0, GameCharacters.PLAYER1));
    }

    @Test
    void checkDiagonal_WinningConditionNotMet() {
        Game game = new Game(6, 7);
        game.getBoard().setCharacterAt(0, 0, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(1, 1, GameCharacters.PLAYER1);
        game.getBoard().setCharacterAt(2, 2, GameCharacters.PLAYER1);
        Assertions.assertFalse(game.checkDiagonal(0, 0, GameCharacters.PLAYER1));
    }
}
