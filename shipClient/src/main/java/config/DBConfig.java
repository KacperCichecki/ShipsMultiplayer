package config;

import exceptions.ApiException;
import java.io.File;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.transform.stream.StreamSource;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class DBConfig {

    public static DBConfig instance;

    @XmlElement
    private String databaseHost;

    @XmlElement
    private int databasePort;

    @XmlElement
    private String databaseLogin;

    @XmlElement
    private String databasePassword;

    private DBConfig() {
    }

    public String databaseHost() {
        return this.databaseHost;
    }
    public int databasePort() {
        return this.databasePort;
    }
    public String databaseLogin() {
        return this.databaseLogin;
    }
    public String databasePassword() {
        return this.databasePassword;
    }

    static {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(DBConfig.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            ClassLoader classLoader = DBConfig.class.getClassLoader();
            File configFile = new File(classLoader.getResource("config.xml").getFile());
            instance = unmarshaller.unmarshal(new StreamSource(configFile), DBConfig.class).getValue();
        } catch (Exception e) {
            throw new ApiException(e);
        }
    }
}