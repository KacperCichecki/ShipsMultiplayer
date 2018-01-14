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
    private static Config instance;

    private static String serverHost;
    private static int serverPort;

    private static String databaseHost;
    private static int databasePort;
    private static String databaseLogin;
    private static String databasePassword;
    private static String schemaName;

    static {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = Config.class.getClassLoader();
            URL serverUrlResources = classLoader.getResource(propFileName);
            File configFile = new File(serverUrlResources.getFile());
            properties.load(new FileReader(configFile));

            serverHost = properties.getProperty("server.host", "localhost");
            serverPort = Integer.parseInt(properties.getProperty("server.port", "2002"));

            databaseHost = properties.getProperty("database.host", "localhost");
            databasePort = Integer.parseInt(properties.getProperty("database.port", "3306"));
            databaseLogin = properties.getProperty("database.login", "root");
            databasePassword = properties.getProperty("database.password", "root");
            schemaName = properties.getProperty("database.name", "ships");


        } catch (IOException e) {
            LogManager.getLogger().error("Problem with loading propetties", e.getMessage());
        }
    }

    public static void load() {
        if (instance == null){
            instance = new Config();
        }
    }

    public static String serverHost() {
        return instance.serverHost;
    }

    public static int serverPort() {
        return instance.serverPort;
    }

    public static String databaseHost() {
        return instance.databaseHost;
    }

    public static int databasePort() {
        return instance.databasePort;
    }

    public static String databaseLogin() {
        return instance.databaseLogin;
    }

    public static String databasePassword() {
        return instance.databasePassword;
    }

    public static String schemaNameName() {
        return instance.schemaName;
    }


}
