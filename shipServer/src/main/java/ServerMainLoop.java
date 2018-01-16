import controller.ServerConnectionController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class ServerMainLoop {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String args[]) {
        try {
            new ServerConnectionController().start();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Server didn't start", e.getMessage());
        }
    }
}
