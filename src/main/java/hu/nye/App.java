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
        Menu menu = new Menu();
        menu.printMenu();
    }
}
