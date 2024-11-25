package hu.nye;


public final class Board {

    private GameCharacters[][] board;

    public Board() {
    }

    public Board(final int arows, final int acolumns) {
        this.board = new GameCharacters[arows][acolumns];

        for (int i = 0; i < arows; i++) {
            for (int j = 0; j < acolumns; j++) {
                this.setCharacterAt(i, j, GameCharacters.EMPTY);
            }
        }
    }

    public GameCharacters[][] getBoard() {
        return board;
    }

    public void setBoard(final GameCharacters[][] aboard) {
        this.board = aboard;
    }

    public GameCharacters getCharacterAt(final int arow, final int acolumn) {
        return board[arow][acolumn];
    }

    public void setCharacterAt(final int arow, final int acolumn, final GameCharacters acharacter) {
        board[arow][acolumn] = acharacter;
    }

    public GameCharacters getZeroBasedCharacterAt(final int arow, final int acolumn) {
        return board[arow][acolumn - 1];
    }

    public void setZeroBasedCharacterAt(final int arow, final int acolumn, final GameCharacters acharacter) {
        board[arow][acolumn - 1] = acharacter;
    }

    public boolean pushToBoard(final int acolumn, final GameCharacters acharacter) {
        int zeroBasedColumn = acolumn - 1;
        // this is correct?
        int rowCount = this.board.length - 1;
        for (int i = rowCount; i >= 0; i--) {
            if (getCharacterAt(i, zeroBasedColumn) == GameCharacters.EMPTY) {
                setCharacterAt(i, zeroBasedColumn, acharacter);
                return true;
            }
        }
        return false;
    }

    public boolean isColumnFull(final int acolumn) {
        return getZeroBasedCharacterAt(0, acolumn) != GameCharacters.EMPTY;
    }

    public boolean isBoardFull() {
        for (int i = 1; i <= App.COLUMNS; i++) {
            if (!isColumnFull(i)) {
                return false;
            }
        }
        return true;
    }

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
