import controller.ServerConnectionController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerMainLoop {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String args[]) {
        new ServerConnectionController();
    }
}
