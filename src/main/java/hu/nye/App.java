package hu.nye;

/*
 * The main class of the Connect 4 game.
 */
public final class App {
    /**
     * The number of rows of the game board.
     */
    static final int ROWS = 6;
    /**
     * The number of columns of the game board.
     */
    static final int COLUMNS = 7;


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
        game.start();

        System.out.println(game);
        Game readGame = Saver.readFromTxt();
        System.out.println(readGame);
        readGame.pushToBoard(2, GameCharacters.PLAYER1);
        System.out.println(readGame);
        Saver.writeToTxt(readGame);

    }
}
