package hu.nye;

import jakarta.xml.bind.JAXBException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameXmlHandlerTest {

    private Game game;
    private File file;

    @BeforeEach
    void setUp() {
        game = new Game(6, 7);
        file = new File("test_game.xml");
    }

    @AfterEach
    void tearDown() {
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveToXmlSavesGameSuccessfully() throws JAXBException {
        GameXmlHandler.saveToXml(game, file);
        assertTrue(file.exists());
    }

    @Test
    void saveToXmlThrowsExceptionForInvalidFile() {
        File invalidFile = new File("/invalid/path/test_game.xml");
        assertThrows(JAXBException.class, () -> GameXmlHandler.saveToXml(game, invalidFile));
    }

    @Test
    void loadFromXmlLoadsGameSuccessfully() throws JAXBException {
        GameXmlHandler.saveToXml(game, file);
        Game loadedGame = GameXmlHandler.loadFromXml(file);
        assertEquals(game.getRows(), loadedGame.getRows());
        assertEquals(game.getColumns(), loadedGame.getColumns());
    }
}