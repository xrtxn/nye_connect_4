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
    PLAYER2_WON
}
