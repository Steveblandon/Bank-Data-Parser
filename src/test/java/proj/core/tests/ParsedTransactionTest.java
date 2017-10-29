package proj.core.tests;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import proj.core.beans.DefaultIdentifier;
import proj.core.beans.ParsedTransaction;

public class ParsedTransactionTest {

	private ParsedTransaction expectedParsedTransaction;
	private Date date;
	private double amount;
	private String description;
	private String account;
	private String notes;
	private String category;
	private String type;
	
	@Before
	public void setUp() throws Exception {
		date = new Date();
		amount = 5;
		description = "TEST";
		account = "debit";
		notes = "";
		category = DefaultIdentifier.CATEGORY_UNKNOWN;
		type = DefaultIdentifier.TYPE_UNKNOWN;
		
		expectedParsedTransaction = new ParsedTransaction();
		expectedParsedTransaction.setAmount(amount);
		expectedParsedTransaction.setDate(date);
		expectedParsedTransaction.setDescription(description);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(null);	//this is to test that null and empty strings are treated as equals
		expectedParsedTransaction.setCategory(category);
		expectedParsedTransaction.setType(type);
	}

	@Test
	public void testEquals() {
		ParsedTransaction actualParsedTransaction = new ParsedTransaction();
		actualParsedTransaction.setAmount(amount);
		actualParsedTransaction.setDate(date);
		actualParsedTransaction.setDescription(description);
		actualParsedTransaction.setAccount(account);
		actualParsedTransaction.setNotes(notes);
		actualParsedTransaction.setCategory(category);
		actualParsedTransaction.setType(type);
		
		assertEquals(actualParsedTransaction, actualParsedTransaction);
	}

}
