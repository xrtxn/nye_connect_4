package hu.nye;

/*
 * The main class of the Connect 4 game.
 */
public final class App {
    /**
     * The number of rows of the game board.
     */
    static final int ROWS = 7;
    /**
     * The number of columns of the game board.
     */
    static final int COLUMNS = 6;

    /**
     * Private constructor to hide the implicit public one.
     */
    private App() {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String[] args) {
        Game game = new Game(ROWS, COLUMNS);
        game.startNew();
        while (game.getState() == GameState.PLAYING) {
            game.getGameInput();
            //not actually next player
            game.checkIfWinner(game.getNextPlayer());
            if (game.getBoard().isBoardFull()) {
                game.setState(GameState.DRAW);
            }
            System.out.println(game);
        }
        System.out.println(GameState.description(game.getState()));


        Game readGame = Saver.readFromTxt();
        System.out.println(readGame);
        readGame.getBoard().pushToBoard(1, GameCharacters.PLAYER1);
        readGame.getBoard().pushToBoard(5, GameCharacters.PLAYER1);
        System.out.println(readGame);
//        Saver.writeToTxt(readGame);
    }
}
