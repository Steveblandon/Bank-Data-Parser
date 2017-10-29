package proj.core.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import proj.core.IdentifierRegistry;
import proj.core.OpenCsvAgent;
import proj.core.ParsedTransactionBuilder;
import proj.core.TransactionParser;
import proj.core.beans.DefaultIdentifier;
import proj.core.beans.Identifier;
import proj.core.beans.ParsedTransaction;
import proj.core.beans.Transaction;
import proj.core.utils.PropertiesUtils;

public class TransactionParserTest {

	private static final String CONFIG_FILE = "testConfig.properties";
	public static final String PROP_ACCOUNT = "ACCOUNT";
	private static final String PROP_PARSED_URI = "PARSED_TRANSACTIONS";
	private static final String PROP_TRANSACTIONS_URI = "TRANSACTIONS";
	private static final String PROP_IDENTIFIERS_URI = "IDENTIFIERS";
	private static final Properties properties = PropertiesUtils.loadProperties(CONFIG_FILE);
	private Transaction transaction;
	private TransactionParser transactionParser;
	private List<Transaction> transactions;
	private String account;

	@Before
	public void setUp() throws Exception{
		account = properties.getProperty(PROP_ACCOUNT);
		List<Identifier> identifiers = OpenCsvAgent.read(properties.getProperty(PROP_IDENTIFIERS_URI), Identifier.class);
		IdentifierRegistry identifierRegistry = new IdentifierRegistry(identifiers);
		transactionParser = new TransactionParser(identifierRegistry, account);
	}
	
	@Test
	public void testParseAll() {
		parseAllTestSetUp();
		List<ParsedTransaction> expectedParsedTransactions = OpenCsvAgent.read(properties.getProperty(PROP_PARSED_URI), ParsedTransaction.class);
		
		List<ParsedTransaction> actualParsedTransactions = transactionParser.parseAll(transactions);
		
		assertEquals(expectedParsedTransactions, actualParsedTransactions);
	}
	
	public void parseAllTestSetUp() {
		transactions = OpenCsvAgent.read(properties.getProperty(PROP_TRANSACTIONS_URI), Transaction.class);
	}
	
	
	@Test
	public void testParseTransaction() {
		parseTransactionTestSetUp();
		ParsedTransaction expectedParsedTransaction = new ParsedTransactionBuilder(transaction, account)
				.applyIdentifier(new DefaultIdentifier())
				.build();
		
		ParsedTransaction actualParsedTransaction = transactionParser.parseTransaction(transaction);
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}
	
	public void parseTransactionTestSetUp() {
		Date date = new Date();
		double positiveAmount = 5;
		String description = "TEST";
		
		transaction = new Transaction();
		transaction.setAmount(positiveAmount);
		transaction.setDate(date);
		transaction.setDescription(description);
	}

}
