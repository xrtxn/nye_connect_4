package hu.nye;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Utility class for saving and loading game state to and from text files.
 */
public final class Saver {
    /**
     * Private constructor to prevent instantiation.
     */
    private Saver() {
    }

    /**
     * Reads the game state from a text file and constructs a Game object.
     *
     * @return the Game object constructed from the text file
     */
    public static Game readFromTxt() {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(
                                    "src/main/resources/save.txt"));
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fromReader(sb.toString());
    }

    /**
     * Writes the game state to a text file.
     *
     * @param agame the Game object to write to the text file
     */
    public static void saveToTxt(final Game agame) {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(
                    new FileWriter(
                            "src/main/resources/save.txt"));
            writer.write(toWriter(agame));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts a Game object to a string representation suitable for
     * writing to a text file.
     *
     * @param agame the Game object to convert
     * @return the string representation of the Game object
     */
    public static String toWriter(final Game agame) {
        StringBuilder sb = new StringBuilder();
        sb.append(agame.getRows()).append(",")
                .append(agame.getColumns())
                .append(",")
                .append(agame.getCurrentPlayer())
                .append("\n");
        for (int i = 0; i < agame.getRows(); i++) {
            for (int j = 0; j < agame.getColumns(); j++) {
                sb.append(agame.getBoard().getCharacterAt(i, j));
            }
            if (i != agame.getRows() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    /**
     * Constructs a Game object from a string representation read from a
     * text file.
     *
     * @param ainput the string representation of the game state
     * @return the Game object constructed from the string representation
     */
    public static Game fromReader(final String ainput) {
        String[] lines = ainput.split("\n");
        String[] firstPart = lines[0].split(",");
        String[] secondPart = lines[1].split(",");
        int rowNum = Integer.parseInt(firstPart[0]);
        int columnNum = Integer.parseInt(firstPart[1]);
        GameCharacters nextPlayer = GameCharacters
                .fromChar(firstPart[2].charAt(0));
        Board board = new Board(rowNum, columnNum);
        for (int i = 0; i < rowNum; i++) {
            for (int j = 0; j < columnNum; j++) {
                board.setCharacterAt(i, j,
                        GameCharacters.fromChar(secondPart[i].charAt(j)));
            }
        }

        return new Game(rowNum, columnNum, board, nextPlayer);
    }
}
