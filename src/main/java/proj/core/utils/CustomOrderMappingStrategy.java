package proj.core.utils;

import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;

import proj.core.Main;

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
			String headerOrder = Main.properties.getProperty(Main.PROP_HEADERS);
			return StringUtils.split(headerOrder.toUpperCase(), ',');
	}
}
