package properties_reader;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public String readFromConfig(String property, String configurationFileName) {
        InputStream inputStream;
        try {
            Properties prop = new Properties();
            String propFileName = configurationFileName + ".properties";
            inputStream = getClass().getClassLoader().getResourceAsStream("config/" + propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath " + propFileName);
            }
            inputStream.close();
            return prop.getProperty(property);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        return "";
    }
}
