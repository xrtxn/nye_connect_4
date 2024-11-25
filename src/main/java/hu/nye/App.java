package hu.nye;

/*
 * The main class of the Connect 4 game.
 */
public final class App {
    
    static final int ROWS = 7;
    
    static final int COLUMNS = 6;

    
    private App() {
    }

    
    public static void main(final String[] args) {
        Menu menu = new Menu();
        menu.printMenu();
    }
}
