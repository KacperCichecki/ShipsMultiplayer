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

    private final static String propFileName = "server.properties";

    private static String serverHost;
    private static int serverPort;

    private static String databaseHost;
    private static int databasePort;
    private static String databaseLogin;
    private static String databasePassword;
    private static String schemaName;

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

    public static String databaseHost() {
        return databaseHost;
    }

    public static int databasePort() {
        return databasePort;
    }

    public static String databaseLogin() {
        return databaseLogin;
    }

    public static String databasePassword() {
        return databasePassword;
    }

    public static String schemaNameName() {
        return schemaName;
    }
}
