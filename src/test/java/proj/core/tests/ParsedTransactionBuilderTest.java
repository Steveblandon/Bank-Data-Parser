package proj.core.tests;
import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import proj.core.DefaultIdentifier;
import proj.core.Identifier;
import proj.core.ParsedTransaction;
import proj.core.ParsedTransactionBuilder;
import proj.core.Transaction;

public class ParsedTransactionBuilderTest {

	final String TYPE_TRANSFER = "transfer";
	Transaction transaction;
	Date date;
	double positiveAmount;
	double negativeAmount;
	String description;
	String descriptor;
	String account;
	String notes;
	String defaultCategory;
	String testCategory;
	String defaultType;
	
	@Before
	public void setUp() throws Exception {
		date = new Date();
		positiveAmount = 5;
		negativeAmount = -5;
		description = "TEST";
		descriptor = "test_";
		account = "debit";
		notes = "";
		testCategory = "testcat";
		defaultCategory = DefaultIdentifier.CATEGORY_UNKNOWN;
		defaultType = DefaultIdentifier.TYPE_UNKNOWN;
		
		transaction = new Transaction();
		transaction.setAmount(positiveAmount);
		transaction.setDate(date);
		transaction.setDescription(description);
	}

	@Test
	public void testBuildWithDefaultIdentifier() {
		ParsedTransaction expectedParsedTransaction = new ParsedTransaction(transaction);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(notes);
		expectedParsedTransaction.setCategory(defaultCategory);
		expectedParsedTransaction.setType(defaultType);
		
		Identifier identifier = new DefaultIdentifier();
		
		ParsedTransactionBuilder builder = new ParsedTransactionBuilder(transaction, account);
		ParsedTransaction actualParsedTransaction = builder.applyIdentifier(identifier).build();
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}

	@Test
	public void testBuildWithTransferIdentifier() {
		ParsedTransaction expectedParsedTransaction = new ParsedTransaction(transaction);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(notes);
		expectedParsedTransaction.setType(TYPE_TRANSFER);
		expectedParsedTransaction.setCategory(defaultCategory);
		expectedParsedTransaction.setDescription(descriptor);
		
		Identifier identifier = new Identifier();
		identifier.setType(TYPE_TRANSFER);
		identifier.setCategory(defaultCategory);
		identifier.setDescriptor(descriptor);
		
		ParsedTransactionBuilder builder = new ParsedTransactionBuilder(transaction, account);
		ParsedTransaction actualParsedTransaction = builder.applyIdentifier(identifier).build();
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}
	
	@Test
	public void testBuildWithIncomeIdentifier() {
		ParsedTransaction expectedParsedTransaction = new ParsedTransaction(transaction);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(notes);
		expectedParsedTransaction.setType(ParsedTransaction.TYPE_INCOME);
		expectedParsedTransaction.setCategory(defaultCategory);
		expectedParsedTransaction.setDescription(descriptor);
		
		Identifier identifier = new Identifier();
		identifier.setType(defaultType);
		identifier.setCategory(defaultCategory);
		identifier.setDescriptor(descriptor);
		
		ParsedTransactionBuilder builder = new ParsedTransactionBuilder(transaction, account);
		ParsedTransaction actualParsedTransaction = builder.applyIdentifier(identifier).build();
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}
	
	@Test
	public void testBuildWithExpenseIdentifier() {
		transaction.setAmount(negativeAmount);
		ParsedTransaction expectedParsedTransaction = new ParsedTransaction(transaction);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(notes);
		expectedParsedTransaction.setType(ParsedTransaction.TYPE_EXPENSE);
		expectedParsedTransaction.setCategory(defaultCategory);
		expectedParsedTransaction.setDescription(descriptor);
		
		Identifier identifier = new Identifier();
		identifier.setType(defaultType);
		identifier.setCategory(defaultCategory);
		identifier.setDescriptor(descriptor);
		
		ParsedTransactionBuilder builder = new ParsedTransactionBuilder(transaction, account);
		ParsedTransaction actualParsedTransaction = builder.applyIdentifier(identifier).build();
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}
	
	@Test
	public void testBuildWithAmbiguousIdentifier() {
		ParsedTransaction expectedParsedTransaction = new ParsedTransaction(transaction);
		expectedParsedTransaction.setAccount(account);
		expectedParsedTransaction.setNotes(notes);
		expectedParsedTransaction.setType(ParsedTransaction.TYPE_INCOME);
		expectedParsedTransaction.setCategory(defaultCategory);
		expectedParsedTransaction.setDescription(descriptor);
		
		Identifier identifier = new Identifier();
		identifier.setAmbiguous(true);
		identifier.setType(defaultType);
		identifier.setCategory(testCategory);
		identifier.setDescriptor(descriptor);
		
		ParsedTransactionBuilder builder = new ParsedTransactionBuilder(transaction, account);
		ParsedTransaction actualParsedTransaction = builder.applyIdentifier(identifier).build();
		
		assertEquals(expectedParsedTransaction, actualParsedTransaction);
	}
}
