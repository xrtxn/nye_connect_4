package hu.nye;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AppTest {

    @Test
    void GameCharactersTest() {
        GameCharacters gameCharacters = GameCharacters.PLAYER1;
        Assertions.assertEquals(gameCharacters, GameCharacters.fromChar('Y'));
        Assertions.assertThrows(IllegalArgumentException.class, () -> GameCharacters.fromChar('X'));
    }

    @Test
    void BoardTest() {
        final int ROWS = 7;
        final int COLUMNS = 6;
        final int bottomRow = ROWS - 1;
        Game game = new Game(ROWS, COLUMNS);
        Assertions.assertEquals(game.getBoard().getCharacterAt(bottomRow, 0), GameCharacters.EMPTY);
        game.getBoard().pushToBoard(1, GameCharacters.PLAYER1);
        Assertions.assertEquals(GameCharacters.PLAYER1, game.getBoard().getZeroBasedCharacterAt(bottomRow, 1));
        game.getBoard().pushToBoard(6, GameCharacters.PLAYER2);
        Assertions.assertEquals(GameCharacters.PLAYER2, game.getBoard().getZeroBasedCharacterAt(bottomRow, 6));
    }
}
