package hu.nye;

import javax.xml.bind.JAXBException;
import java.sql.SQLException;
import java.util.Scanner;

public final class Menu {

    private MenuAction menuAction;

    public Menu() {
        this.menuAction = MenuAction.MAIN_MENU;
    }

    @GeneratedJacocoExcluded
    public void printMenu() {
        while (menuAction != MenuAction.QUIT) {
            System.out.println("Main menu");
            System.out.println("1. New game");
            System.out.println("2. Load game");
            System.out.println("3. Show highscores");
            System.out.println("4. Quit");
            System.out.print("Input: ");

            Scanner scanner = new Scanner(System.in);
            Integer choice = null;
            while (choice == null) {
                try {
                    choice = scanner.nextInt();
                    if (choice < 1 || choice > 4) {
                        System.out.println("Invalid choice. Please try again.");
                        choice = null;
                    }
                } catch (Exception e) {
                    System.out.println("Invalid choice. Please try again.");
                }
            }
            switch (choice) {
                case 1 -> this.menuAction = MenuAction.NEW_GAME;
                case 2 -> this.menuAction = MenuAction.LOAD_GAME;
                case 3 -> this.menuAction = MenuAction.SHOW_HIGHSCORES;
                case 4 -> this.menuAction = MenuAction.QUIT;
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    printMenu();
                }
            }
            action();
        }
    }

    @GeneratedJacocoExcluded
    public void action() {
        switch (menuAction) {
            case MAIN_MENU -> printMenu();
            case NEW_GAME -> {
                System.out.println("Starting a new game...");
                Game game = new Game(7, 6);
                game.startNew();
            }
            case LOAD_GAME -> {
                System.out.println("Loading a game...");
                Game readGame;
                try {
                    readGame = GameXmlHandler.loadFromXml(GameXmlHandler.SAVE_FILE);
                } catch (JAXBException e) {
                    throw new RuntimeException(e);
                }
                readGame.startNew();
            }
            case SHOW_HIGHSCORES -> {
                System.out.println("Showing high scores...");
                HighScore highScore = new HighScore();
                try {
                    highScore.showHighScore();
                } catch (SQLException e) {
                    System.out.println("Unable to show high scores.");
                }
            }
            case QUIT -> {
                System.out.println("Quitting the game...");
                System.exit(0);
            }
            default -> {
                System.out.println("FIXME: Option not implemented.");
                this.menuAction = MenuAction.MAIN_MENU;
            }
        }
    }
}
