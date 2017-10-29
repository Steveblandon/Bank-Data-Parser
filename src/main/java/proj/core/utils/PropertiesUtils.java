package proj.core.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class PropertiesUtils {
	
	private PropertiesUtils() {};
	
	public static Properties loadProperties(String file) {
		Properties properties = new Properties();
		try{
			InputStream propStream = ClassLoader.getSystemResourceAsStream(file);
			properties.load(propStream);
			propStream.close();
		} catch (IOException e2) {
			//TODO: log error
		}
		return properties;
	}
	
	public static void storeProperties(Properties properties, String file) {
		try{
			OutputStream propStream = new FileOutputStream(ClassLoader.getSystemResource(file).getPath());
			properties.store(propStream, "");
			propStream.close();
		} catch (IOException e2) {
			//TODO: log error
		}
	}
}
