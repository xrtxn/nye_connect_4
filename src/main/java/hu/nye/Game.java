package hu.nye;

import java.sql.SQLException;
import java.util.Scanner;

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
    private GameCharacters currentPlayer;

    /**
     * The current state of the game.
     */
    private GameState state;

    private String playerName;

    private static final int ROBOT_SLEEP_TIME = 500;

    private static final int WINNING_CONDITION = 4;

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
        this.currentPlayer = anextPlayer;
        this.state = GameState.SETUP;
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
        this.currentPlayer = GameCharacters.PLAYER1;
        this.board = new Board(arows, acolumns);
        this.state = GameState.SETUP;
    }

    public void userInput() {
        System.out.println("Enter the column number: ");

        Scanner scanner = new Scanner(System.in);
        int column = scanner.nextInt();
        if (column < 0 || column >= columns) {
            System.out.println("Invalid column number."
                    + " Please enter a number between 0 and " + (columns - 1));
            return;
        }
        if (board.pushToBoard(column, currentPlayer)) {
            switchPlayer();
        } else {
            System.out.println("Column is full. Please choose another column.");
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
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (board.getCharacterAt(i, j) == character) {
                    if (checkHorizontal(i, j, character)
                            || checkVertical(i, j, character)
                            || checkDiagonal(i, j, character)) {
                        if (character == GameCharacters.PLAYER1) {
                            setState(GameState.PLAYER_WON);
                        } else {
                            setState(GameState.ROBOT_WON);
                        }
                        return;
                    }
                }
            }
        }
    }

    public boolean checkHorizontal(final int row, final int column, final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (column + i < columns && board.getCharacterAt(row, column + i) == character) {
                count++;
            }
        }
        return count == WINNING_CONDITION;
    }

    public boolean checkVertical(final int row, final int column, final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && board.getCharacterAt(row + i, column) == character) {
                count++;
            }
        }
        return count == WINNING_CONDITION;
    }

    public boolean checkDiagonal(final int row, final int column, final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && column + i < columns
                    && board.getCharacterAt(row + i, column + i) == character) {
                count++;
            }
        }
        if (count == WINNING_CONDITION) {
            return true;
        }
        count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && column - i >= 0
                    && board.getCharacterAt(row + i, column - i) == character) {
                count++;
            }
        }
        return count == WINNING_CONDITION;
    }

    public void startNew() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter the player name: ");
        playerName = sc.nextLine();
        System.out.println(playerName + " vs. Robot");
        System.out.println(playerName + " starts the game");
        setState(GameState.PLAYING);

        HighScore highScore = new HighScore();
        try {
            highScore.createTables();
            highScore.insertUser(getPlayerName());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (getState() == GameState.PLAYING) {
            getGameInput();
            //not actually next player
            checkIfWinner(getNextPlayer());
            if (getBoard().isBoardFull()) {
                setState(GameState.DRAW);
            }
            System.out.println(this);
        }
        if (getState() == GameState.PLAYER_WON) {
            try {
                highScore.increaseHighScore(getPlayerName());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println(GameState.description(getState()));
    }

    public void getGameInput() {
        if (getCurrentPlayer() == GameCharacters.PLAYER1) {
            boolean validInput = false;
            Scanner sc = new Scanner(System.in);

            boolean isNum = false;
            int selectedColumn = Integer.MAX_VALUE;
            do {
                System.out.println("Please enter the column number");
                System.out.println("Or press 's' to save the game.");
                System.out.println("Or press 'q' to quit the game.");
                System.out.print("Input: ");
                String input = sc.nextLine();
                try {
                    selectedColumn = Integer.parseInt(input);
                    isNum = true;
                } catch (NumberFormatException e) {
                    switch (input) {
                        case "s" -> {
                            Saver.saveToTxt(this);
                            System.out.println("Game saved.");
                            continue;
                        }
                        case "q" -> {
                            System.out.println("Quitting the game...");
                            System.exit(0);
                        }
                    }
                }
                if (isNum) {
                    if (selectedColumn < 1 || selectedColumn >= columns + 1) {
                        System.out.print("Invalid column number."
                                + " Please enter a number between 1 and "
                                + (columns) + ": ");
                    } else {
                        if (getBoard().pushToBoard(selectedColumn, GameCharacters.PLAYER1)) {
                            validInput = true;
                        } else {
                            System.out.println("Column is full."
                                    + " Please choose another column.");
                        }
                    }
                }

            }
            while (!validInput);

            switchPlayer();
        } else {
            System.out.println("The robot is thinking...");
            try {
                Thread.sleep(ROBOT_SLEEP_TIME);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Thread was interrupted,"
                        + " failed to complete operation");
            }
            int botColumn = botMove();
            System.out.println("Robot moved to column " + botColumn);
            switchPlayer();
        }
    }

    public int botMove() {
        int column = (int) (Math.random() * App.COLUMNS) + 1;
        while (this.board.isColumnFull(column)) {
            column = (int) (Math.random() * App.COLUMNS) + 1;
        }
        this.board.pushToBoard(column, GameCharacters.ROBOT);
        return column;
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
    public GameCharacters getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Sets the character representing the next player to move.
     *
     * @param anextPlayer the new next player character
     */
    public void setCurrentPlayer(final GameCharacters anextPlayer) {
        this.currentPlayer = anextPlayer;
    }

    public GameCharacters getNextPlayer() {
        if (currentPlayer == GameCharacters.PLAYER1) {
            return GameCharacters.ROBOT;
        } else {
            return GameCharacters.PLAYER1;
        }
    }

    /**
     * Switches the current player to the other player.
     */
    public void switchPlayer() {
        if (currentPlayer == GameCharacters.PLAYER1) {
            currentPlayer = GameCharacters.ROBOT;
        } else {
            currentPlayer = GameCharacters.PLAYER1;
        }
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

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }


    /**
     * Returns a string representation of the game board.
     *
     * @return the string representation of the game board
     */
    @Override
    public String toString() {
        return this.board.toString();
    }
}
