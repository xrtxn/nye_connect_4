package hu.nye;

public final class Game {
    /**
     * The number of rows in the game board.
     */
    private final int rows;

    /**
     * The number of columns in the game board.
     */
    private final int columns;

    /**
     * The game board instance.
     */
    private Board board;

    /**
     * The character representing the next player to move.
     */
    private GameCharacters nextPlayer;

    /**
     * The current state of the game.
     */
    private GameState state;

    /**
     * Constructs a new Game instance with the specified parameters.
     *
     * @param arows       the number of rows in the game board
     * @param acolumns    the number of columns in the game board
     * @param aboard      the game board instance
     * @param anextPlayer the character representing the next player to move
     */
    public Game(final int arows,
                final int acolumns,
                final Board aboard,
                final GameCharacters anextPlayer) {
        this.rows = arows;
        this.columns = acolumns;
        this.board = aboard;
        this.nextPlayer = anextPlayer;
    }

    /**
     * Constructs a new Game instance with the specified number of rows and
     * columns.
     * Initializes the game board and sets the initial state and next player.
     *
     * @param arows    the number of rows in the game board
     * @param acolumns the number of columns in the game board
     */
    public Game(final int arows, final int acolumns) {
        this.rows = arows;
        this.columns = acolumns;
        this.nextPlayer = GameCharacters.PLAYER1;
        this.board = new Board(arows, acolumns);
        this.state = GameState.SETUP;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.columns; j++) {
                this.board.setCharacterAt(i, j, GameCharacters.EMPTY);
            }
        }
    }

    /**
     * Pushes a character to the specified column on the game board.
     * Checks if the move results in a win, draw, or continues the game.
     *
     * @param acolumn    the column to push the character to (1-based index)
     * @param acharacter the character to push to the board
     */
    public void pushToBoard(final int acolumn,
                            final GameCharacters acharacter) {
        int column = acolumn;
        // 0-based index
        column--;
        for (int i = rows - 1; i >= 0; i--) {
            if (this.board.getCharacterAt(i, column) == GameCharacters.EMPTY) {
                this.board.setCharacterAt(i, column, acharacter);
                break;
            }
        }
        checkIfWinner(acharacter);
        if (state == GameState.PLAYER1_WON) {
            System.out.println("Player 1 won!");
        } else if (state == GameState.PLAYER2_WON) {
            System.out.println("Player 2 won!");
        } else if (state == GameState.DRAW) {
            System.out.println("It's a draw!");
        }
    }

    /**
     * Checks if the given character has won the game.
     * This method should be implemented to check the current state
     * of the board and determine if the specified character has
     * achieved a winning condition.
     *
     * @param character the character to check for a winning condition
     */
    public void checkIfWinner(final GameCharacters character) {
    }

    /**
     * Starts the game by setting the state to PLAYING and printing the game
     * dimensions.
     */
    public void start() {
        System.out.println("Game started with width: " + rows
                + " and height: " + columns);
        setState(GameState.PLAYING);
    }

    /**
     * Gets the current state of the game.
     *
     * @return the current game state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the current state of the game.
     *
     * @param astate the new game state
     */
    public void setState(final GameState astate) {
        this.state = astate;
    }

    /**
     * Gets the number of rows in the game board.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Gets the number of columns in the game board.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Gets the character representing the next player to move.
     *
     * @return the next player character
     */
    public GameCharacters getNextPlayer() {
        return nextPlayer;
    }

    /**
     * Sets the character representing the next player to move.
     *
     * @param anextPlayer the new next player character
     */
    public void setNextPlayer(final GameCharacters anextPlayer) {
        this.nextPlayer = anextPlayer;
    }

    /**
     * Gets the game board instance.
     *
     * @return the game board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Sets the game board instance.
     *
     * @param aboard the new game board
     */
    public void setBoard(final Board aboard) {
        this.board = aboard;
    }

    /**
     * Returns a string representation of the game board.
     *
     * @return the string representation of the game board
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                sb.append(this.board.getCharacterAt(i, j));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
