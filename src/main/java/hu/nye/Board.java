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

        for (int i = 0; i < arows; i++) {
            for (int j = 0; j < acolumns; j++) {
                this.setCharacterAt(i, j, GameCharacters.EMPTY);
            }
        }
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

    /**
     * Gets the character at the specified position on the game board.
     *
     * @param arow    the row index
     * @param acolumn the column index
     * @return the GameCharacter at the specified position
     */
    public GameCharacters getZeroBasedCharacterAt(
            final int arow,
            final int acolumn) {
        return board[arow][acolumn - 1];
    }

    /**
     * Sets the character at the specified position on the game board.
     *
     * @param arow       the row index
     * @param acolumn    the column index
     * @param acharacter the GameCharacter to set
     */
    public void setZeroBasedCharacterAt(final int arow,
                                        final int acolumn,
                                        final GameCharacters acharacter) {
        board[arow][acolumn - 1] = acharacter;
    }

    /**
     * Pushes a character to the specified column on the game board.
     *
     * @param acolumn    the column to push the character to (1-based index)
     * @param acharacter the character to push to the board
     */
    public boolean pushToBoard(final int acolumn,
                               final GameCharacters acharacter) {
        int zeroBasedColumn = acolumn - 1;
        // this is correct?
        int rowCount = this.board.length - 1;
        for (int i = rowCount; i >= 0; i--) {
            if (getCharacterAt(i, zeroBasedColumn) == GameCharacters.EMPTY) {
                setCharacterAt(i, zeroBasedColumn, acharacter);
                break;
            }
        }
        return true;
    }

    public boolean isColumnFull(final int acolumn) {
        return getZeroBasedCharacterAt(0, acolumn) != GameCharacters.EMPTY;
    }

    //this can be improved
    public boolean isBoardFull() {
        for (int i = 1; i <= App.COLUMNS; i++) {
            if (!isColumnFull(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the game board.
     *
     * @return the string representation of the game board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                sb.append(getCharacterAt(i, j)).append("  ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
