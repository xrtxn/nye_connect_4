package hu.nye;

/**
 * Enum representing the different states of the game.
 */
public enum GameState {
    /**
     * The game is in setup state.
     */
    SETUP,

    /**
     * The game is currently being played.
     */
    PLAYING,

    /**
     * The game ended in a draw.
     */
    DRAW,

    /**
     * Player 1 has won the game.
     */
    PLAYER1_WON,

    /**
     * Player 2 has won the game.
     */
    PLAYER2_WON;

    /**
     * Returns a description of the given game state.
     *
     * @param state the game state to describe
     * @return a string description of the game state
     * @throws IllegalStateException if the game state is unexpected
     */
    public static String description(final GameState state) {
        switch (state) {
            case SETUP -> {
                return "The game is in setup state.";
            }
            case PLAYING -> {
                return "The game is currently being played.";
            }
            case DRAW -> {
                return "The game ended in a draw.";
            }
            case PLAYER1_WON -> {
                return "Player 1 has won the game.";
            }
            case PLAYER2_WON -> {
                return "Player 2 has won the game.";
            }
            default -> throw new IllegalStateException("Unexpected value: "
                    + state);
        }
    }
}
