package proj.core.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

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

public class TransactionParserTest {

	String PARSED_TRANSACTIONS_FILE = "C:\\Users\\steve\\Workshop\\Git\\bank-data-parser\\target\\test-classes\\parsedTransactionData.csv";
	Transaction transaction;
	TransactionParser transactionParser;
	List<Transaction> transactions;
	String account;

	@Before
	public void setUp() throws Exception{
		account = "debit";
		List<Identifier> identifiers = OpenCsvAgent.read("identifiers.csv", Identifier.class);
		IdentifierRegistry identifierRegistry = new IdentifierRegistry(identifiers);
		transactionParser = new TransactionParser(identifierRegistry, account);
	}
	
	@Test
	public void testParseAll() {
		parseAllTestSetUp();
		List<ParsedTransaction> expectedParsedTransactions = OpenCsvAgent.read(PARSED_TRANSACTIONS_FILE, ParsedTransaction.class);
		
		List<ParsedTransaction> actualParsedTransactions = transactionParser.parseAll(transactions);
		
		assertEquals(expectedParsedTransactions, actualParsedTransactions);
	}
	
	public void parseAllTestSetUp() {
		transactions = OpenCsvAgent.read("transactionData.CSV", Transaction.class);
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
