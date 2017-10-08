package proj.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TransactionParser {
	
	private IdentifierRegistry identifierRegistry;
	private String account;
	private int totalOfunsuccessfulParses;
	
	public TransactionParser(IdentifierRegistry identifierRegistry, String account) {
		this.account = account;
		this.identifierRegistry = identifierRegistry;
	}
	
	
	public List<ParsedTransaction> parseAll(List<Transaction> transactions) {
		List<ParsedTransaction> parsedTransactions = new ArrayList<>();
		ParsedTransaction parsedTransaction;
		for (Transaction transaction : transactions) {
			parsedTransaction = parseTransaction(transaction);
			if (!parsedTransaction.hasValidType()) {
				totalOfunsuccessfulParses++;
			}
			parsedTransactions.add(parsedTransaction);
		}
		return parsedTransactions;
	}
	
	
	public ParsedTransaction parseTransaction(Transaction transaction) {
		String description = transaction.getDescription();
		Identifier identifier = identifierRegistry.findIdentifier(description);
		return new ParsedTransactionBuilder(transaction, account)
				.applyIdentifier(identifier)
				.build();
	}
	
	
	public int getTotalOfunsuccessfulParses() {
		return totalOfunsuccessfulParses;
	}
}
