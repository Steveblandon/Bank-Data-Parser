package proj.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;

public class CustomOrderMappingStrategy <T> extends HeaderColumnNameMappingStrategy <T> {
	
	private final static String CONFIG_FILE = "config.properties";
	private final static String CONFIG_PROP_HEADER_ORDER = "HEADER_ORDER";
	
	
	public CustomOrderMappingStrategy(Class<T> type) {
		setType(type);
	}
	
	@Override
    public String[] generateHeader() {
		header = super.generateHeader();
		
		String[] newHeader = retrieveHeaderOrder();
		if (isHeaderOrderValid(newHeader)) {
			header = newHeader;
		}
		
		return header;
    }
	
	/**
	 * assures that the header order retrieved from a config file can be used
	 * with openCSV.
	 * @param headerOrder - String retrieved from config file.
	 * @return
	 */
	private boolean isHeaderOrderValid(String[] headerOrder) {
		if (header != null && headerOrder != null) {
			if (header.length != headerOrder.length) {
				return false;
			}
			
			List<String> headerOrderList = Arrays.asList(headerOrder);
			for (String name : header) {
				if (!headerOrderList.contains(name)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * retrieves a string of comma-separated column names from a config file. The order of these 
	 * names will be used as the order for writing into a csv file. 
	 * @return header array if property is loaded successfully. Otherwise, null.
	 */
	private String[] retrieveHeaderOrder() {
		Properties properties = new Properties();
		try(InputStream propStream = ClassLoader.getSystemResourceAsStream(CONFIG_FILE)){
			properties.load(propStream);
			String headerOrder = properties.getProperty(CONFIG_PROP_HEADER_ORDER);
			return StringUtils.split(headerOrder.toUpperCase(), ',');
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		return null;
	}
}
