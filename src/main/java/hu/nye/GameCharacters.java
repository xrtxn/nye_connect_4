package hu.nye;


public enum GameCharacters {
    
    PLAYER1('Y'),

    
    ROBOT('R'),

    
    EMPTY('O');

    
    private final char gameCharacter;

    
    GameCharacters(final char agameCharacter) {
        this.gameCharacter = agameCharacter;
    }

    
    public static GameCharacters fromChar(final char agameCharacter) {
        for (GameCharacters gameCharacter : GameCharacters.values()) {
            if (gameCharacter.gameCharacter == agameCharacter) {
                return gameCharacter;
            }
        }
        throw new IllegalArgumentException(
                "No enum constant with agameCharacter " + agameCharacter);
    }

    
    @Override
    public String toString() {
        return gameCharacter + "";
    }
}
