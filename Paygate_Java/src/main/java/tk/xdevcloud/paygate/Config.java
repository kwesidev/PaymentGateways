package tk.xdevcloud.paygate;

import java.util.Properties;
import java.io.IOException;
/**
 * Object to get configs
 * @author kwesidev
 *
 */
public class Config {
	
	/**
	 * Method to get config values
	 * @param key String
	 * @return Object
	 * @throws IOException
	 */
	public static Object getValue(String key)  throws IOException{
		Properties prop = new Properties();
		prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		return prop.get(key);
	}

}
