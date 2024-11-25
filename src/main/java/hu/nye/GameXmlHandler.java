package hu.nye;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;


public final class GameXmlHandler {
    public static final File SAVE_FILE = new File("src/main/resources/game.xml");

    
    private GameXmlHandler() {
    }

    
    public static void saveToXml(Game game, File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Game.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(game, file);
    }

    
    public static Game loadFromXml(File file) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Game.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Game) unmarshaller.unmarshal(file);
    }
}
