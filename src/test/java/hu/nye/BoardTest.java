package hu.nye;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BoardTest {
    @Test
    void isColumnFullReturnsTrueWhenColumnIsFull() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 6; i++) {
            board.pushToBoard(1, GameCharacters.PLAYER1);
        }
        Assertions.assertTrue(board.isColumnFull(1));
    }

    @Test
    void isColumnFullReturnsFalseWhenColumnIsEmpty() {
        Board board = new Board(6, 7);
        Assertions.assertFalse(board.isColumnFull(1));
    }

    @Test
    void isColumnFullReturnsFalseWhenColumnIsPartiallyFilled() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 3; i++) {
            board.pushToBoard(1, GameCharacters.PLAYER1);
        }
        Assertions.assertFalse(board.isColumnFull(1));
    }

    @Test
    void isBoardFullReturnsTrueWhenBoardIsFull() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j <= 7; j++) {
                board.pushToBoard(j, GameCharacters.PLAYER1);
            }
        }
        Assertions.assertTrue(board.isBoardFull());
    }

    @Test
    void isBoardFullReturnsFalseWhenBoardIsNotFull() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 5; i++) {
            for (int j = 1; j <= 7; j++) {
                board.pushToBoard(j, GameCharacters.PLAYER1);
            }
        }
        Assertions.assertFalse(board.isBoardFull());
    }

    @Test
    void isBoardFullReturnsFalseWhenOneColumnIsNotFull() {
        Board board = new Board(6, 7);
        for (int i = 0; i < 6; i++) {
            for (int j = 1; j <= 6; j++) {
                board.pushToBoard(j, GameCharacters.PLAYER1);
            }
        }
        Assertions.assertFalse(board.isBoardFull());
    }
}
