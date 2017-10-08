package proj.core;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class Main {

	public static void main (String[] args)  {
		Properties properties = loadProperties();
		final String account = properties.getProperty("ACCOUNT", "UNKNOWN");
		final String transactions_filename = properties.getProperty("TRANSACTIONS_FILENAME", "");
		final String parsed_transactions_filename = properties.getProperty("PARSED_TRANSACTIONS_FILENAME", "");
		final String identifiers_filename = properties.getProperty("IDENTIFIERS_FILENAME", "");
		
		System.out.println("loading transactions and identifier data...");
		List<Transaction> transactions = OpenCsvAgent.read(transactions_filename, Transaction.class);
		List<Identifier> identifiers = OpenCsvAgent.read(identifiers_filename, Identifier.class);
		IdentifierRegistry identifierRegistry = new IdentifierRegistry(identifiers);
		System.out.print("DONE.");
		System.out.println("parsing data...");
		TransactionParser parser = new TransactionParser(identifierRegistry, account);
		List<ParsedTransaction> parsedTransactions = parser.parseAll(transactions);
		System.out.print("DONE.");
		System.out.printf("success rate: %.2f", Math.round((parser.getTotalOfunsuccessfulParses() / parsedTransactions.size())));
		OpenCsvAgent.write(parsed_transactions_filename, ParsedTransaction.class, parsedTransactions);
	} 
	
	
	public static Properties loadProperties() {
		Properties properties = new Properties();
		try(InputStream propStream = ClassLoader.getSystemResourceAsStream("config.properties")){
			properties.load(propStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return properties;
	}

}
