/**
 * 
 */
package poc.prop;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Credential Reader from Environment
 */
public class PropertyReader {

    private static Properties properties;

    
    private static void initialize() {
    	properties = new Properties();
        try (InputStream input = PropertyReader.class.getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
    	if(properties==null ) {
    		initialize();
    	}
        return properties.getProperty(key);
    }
    
	
    public static void main(String[] args) {
    	System.out.println(PropertyReader.getProperty(IProperty.VEDIOLOCATION));
    }

}
