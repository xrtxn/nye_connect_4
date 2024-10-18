package hu.nye;

/**
 * Represents a game board with a 2D array of GameCharacters.
 */
public final class Board {
    /**
     * The 2D array representing the game board.
     */
    private GameCharacters[][] board;

    /**
     * Constructs a new Board instance with the specified number of rows
     * and columns.
     *
     * @param arows    the number of rows in the game board
     * @param acolumns the number of columns in the game board
     */
    public Board(final int arows, final int acolumns) {
        this.board = new GameCharacters[arows][acolumns];
    }

    /**
     * Gets the 2D array representing the game board.
     *
     * @return the 2D array of GameCharacters
     */
    public GameCharacters[][] getBoard() {
        return board;
    }

    /**
     * Sets the 2D array representing the game board.
     *
     * @param aboard the new 2D array of GameCharacters
     */
    public void setBoard(final GameCharacters[][] aboard) {
        this.board = aboard;
    }

    /**
     * Gets the character at the specified position on the game board.
     *
     * @param arow    the row index
     * @param acolumn the column index
     * @return the GameCharacter at the specified position
     */
    public GameCharacters getCharacterAt(final int arow, final int acolumn) {
        return board[arow][acolumn];
    }

    /**
     * Sets the character at the specified position on the game board.
     *
     * @param arow       the row index
     * @param acolumn    the column index
     * @param acharacter the GameCharacter to set
     */
    public void setCharacterAt(final int arow,
                               final int acolumn,
                               final GameCharacters acharacter) {
        board[arow][acolumn] = acharacter;
    }
}
