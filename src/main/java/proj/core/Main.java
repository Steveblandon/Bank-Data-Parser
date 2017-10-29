package proj.core;

import java.awt.EventQueue;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import proj.core.gui.GUI;
import proj.core.utils.PropertiesUtils;

public class Main {

	public static final String CONFIG_FILE = "config.properties";
	public static final String PROP_HEADERS = "HEADER_ORDER";
	public static final String PROP_ACCOUNT = "ACCOUNT";
	public static final String PROP_TRANSACTIONS = "TRANSACTIONS_FILENAME";
	public static final String PROP_PARSED = "PARSED_TRANSACTIONS_FILENAME";
	public static final String PROP_PARSED_EXT = "PARSED_TRANSACTIONS_FILENAME_EXT";
	public static final String PROP_IDENTIFIERS = "IDENTIFIERS_FILENAME";
	public static Properties properties = PropertiesUtils.loadProperties(CONFIG_FILE);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI gui = new GUI();
					gui.start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
