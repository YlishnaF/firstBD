import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropManager {
    private static PropManager INSTANCE = null;
    private final Properties properties = new Properties();

    private PropManager() {
        loadApplicationProperties();
    }

    public static PropManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PropManager();
        }
        return INSTANCE;
    }

    private void loadApplicationProperties() {
        String nameFile = System.getProperty("propFile", "db");

        try {
            properties.load(new FileInputStream("src/main/resources/" + nameFile + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}

