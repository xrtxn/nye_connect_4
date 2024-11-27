package hu.nye;

public enum GameState {

    SETUP,

    PLAYING,

    DRAW,

    PLAYER_WON,

    ROBOT_WON;

    @GeneratedJacocoExcluded
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
            case PLAYER_WON -> {
                return "The player has won the game.";
            }
            case ROBOT_WON -> {
                return "The robot has won the game.";
            }
            default -> throw new IllegalStateException("Unexpected value: "
                    + state);
        }
    }
}
