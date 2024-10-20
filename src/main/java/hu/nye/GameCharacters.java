package hu.nye;

/**
 * Enum representing the different game characters.
 */
public enum GameCharacters {
    /**
     * Player 1 character.
     */
    PLAYER1('Y'),

    /**
     * Player 2 character.
     */
    ROBOT('R'),

    /**
     * Empty character.
     */
    EMPTY('O');

    /**
     * The character representing the game character.
     */
    private final char gameCharacter;

    /**
     * Constructs a GameCharacter with the specified character.
     *
     * @param agameCharacter the character representing the game character
     */
    GameCharacters(final char agameCharacter) {
        this.gameCharacter = agameCharacter;
    }

    /**
     * Returns the character representing the game character.
     *
     * @param agameCharacter the character representing the game character
     * @return the character representing the game character
     */
    public static GameCharacters fromChar(final char agameCharacter) {
        for (GameCharacters gameCharacter : GameCharacters.values()) {
            if (gameCharacter.gameCharacter == agameCharacter) {
                return gameCharacter;
            }
        }
        throw new IllegalArgumentException(
                "No enum constant with agameCharacter " + agameCharacter);
    }

    /**
     * Returns the string representation of the game character.
     *
     * @return the string representation of the game character
     */
    @Override
    public String toString() {
        return gameCharacter + "";
    }
}
