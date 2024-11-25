package hu.nye;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.SQLException;
import java.util.Scanner;

@XmlRootElement
public final class Game {
    
    private static final int ROBOT_SLEEP_TIME = 500;
    
    private static final int WINNING_CONDITION = 4;
    
    private int rows;
    
    private int columns;
    
    private Board board;
    
    private GameCharacters currentPlayer;
    
    private GameState state;
    
    private String playerName;

    public Game() {
    }

    
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

    
    public Game(final int arows, final int acolumns) {
        this.rows = arows;
        this.columns = acolumns;
        this.currentPlayer = GameCharacters.PLAYER1;
        this.board = new Board(arows, acolumns);
        this.state = GameState.SETUP;
    }

    
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

    
    public boolean checkHorizontal(final int row,
                                   final int column,
                                   final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (column + i < columns && board.
                    getCharacterAt(row, column + i) == character) {
                count++;
            }
        }
        return count == WINNING_CONDITION;
    }

    
    public boolean checkVertical(final int row,
                                 final int column,
                                 final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && board.
                    getCharacterAt(row + i, column) == character) {
                count++;
            }
        }
        return count == WINNING_CONDITION;
    }

    
    public boolean checkDiagonal(final int row,
                                 final int column,
                                 final GameCharacters character) {
        int count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && column + i < columns
                    && board.getCharacterAt(row + i,
                    column + i) == character) {
                count++;
            }
        }
        if (count == WINNING_CONDITION) {
            return true;
        }
        count = 0;
        for (int i = 0; i < WINNING_CONDITION; i++) {
            if (row + i < rows && column - i >= 0
                    && board.getCharacterAt(row + i,
                    column - i) == character) {
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
            // not actually next player
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
                            try {
                                GameXmlHandler.saveToXml(this, GameXmlHandler.SAVE_FILE);
                            } catch (JAXBException ex) {
                                throw new RuntimeException(ex);
                            }
                            System.out.println("Game saved.");
                            continue;
                        }
                        case "q" -> {
                            System.out.println("Quitting the game...");
                            System.exit(0);
                        }
                        default -> {
                            System.out.println("Invalid input."
                                    + " Please try again.");
                            continue;
                        }
                    }
                }
                if (isNum) {
                    if (selectedColumn < 1 || selectedColumn >= columns + 1) {
                        System.out.print("Invalid column number."
                                + " Please enter a number between 1 and "
                                + (columns) + ": ");
                    } else {
                        if (getBoard().pushToBoard(selectedColumn,
                                GameCharacters.PLAYER1)) {
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

    
    @XmlElement
    public GameState getState() {
        return state;
    }

    
    public void setState(final GameState astate) {
        this.state = astate;
    }

    
    @XmlElement
    public int getRows() {
        return rows;
    }

    
    @XmlElement
    public int getColumns() {
        return columns;
    }

    
    @XmlElement
    public GameCharacters getCurrentPlayer() {
        return currentPlayer;
    }

    
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

    
    public void switchPlayer() {
        if (currentPlayer == GameCharacters.PLAYER1) {
            currentPlayer = GameCharacters.ROBOT;
        } else {
            currentPlayer = GameCharacters.PLAYER1;
        }
    }

    
    @XmlElement
    public Board getBoard() {
        return board;
    }

    
    public void setBoard(final Board aboard) {
        this.board = aboard;
    }

    
    public String getPlayerName() {
        return playerName;
    }

    
    public void setPlayerName(final String aplayerName) {
        this.playerName = aplayerName;
    }

    public void setRows(final int rows) {
        this.rows = rows;
    }

    public void setColumns(final int columns) {
        this.columns = columns;
    }

    
    @Override
    public String toString() {
        return this.board.toString();
    }
}
