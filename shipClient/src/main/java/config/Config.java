package config;

import org.apache.logging.log4j.LogManager;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

/**
 * Konfiguracja aplikacji
 */
public class Config {

    private final static String propFileName = "properties";

    private static String serverHost;
    private static int serverPort;
    private static String clientHost;
    private static int clientPort;
    private static String path;
    private static String apikey;

    private Config(){
    }

    static {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = Config.class.getClassLoader();
            URL serverUrlResources = classLoader.getResource(propFileName);
            File configFile = new File(serverUrlResources.getFile());
            properties.load(new FileReader(configFile));

            serverHost = properties.getProperty("server.host", "localhost");
            serverPort = Integer.parseInt(properties.getProperty("server.port", "2002"));

            clientHost = properties.getProperty("client.host", "localhost");
            clientPort = Integer.parseInt(properties.getProperty("client.port", "5555"));

            path = properties.getProperty("path");
            apikey = properties.getProperty("apikey");

        } catch (IOException e) {
            LogManager.getLogger().error("Problem with loading propetties", e.getMessage());
        }
    }

    public static String serverHost() {
        return serverHost;
    }

    public static int serverPort() {
        return serverPort;
    }

    public static String clientHost() {
        return clientHost;
    }

    public static int clientPort() {
        return clientPort;
    }

    public static String serverPath() {
        return path;
    }

    public static String serverApiKey() {
        return apikey;
    }

}
