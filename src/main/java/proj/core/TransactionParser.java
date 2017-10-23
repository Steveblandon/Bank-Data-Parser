package proj.core;

import java.util.ArrayList;
import java.util.List;

import proj.core.beans.Identifier;
import proj.core.beans.ParsedTransaction;
import proj.core.beans.Transaction;

public class TransactionParser {
	
	private IdentifierRegistry identifierRegistry;
	private String account;
	private int successfulParses, totalParses;
	
	public TransactionParser(IdentifierRegistry identifierRegistry, String account) {
		this.account = account;
		this.identifierRegistry = identifierRegistry;
	}
	
	
	public List<ParsedTransaction> parseAll(List<Transaction> transactions) {
		List<ParsedTransaction> parsedTransactions = new ArrayList<>();
		ParsedTransaction parsedTransaction;
		for (Transaction transaction : transactions) {
			parsedTransaction = parseTransaction(transaction);
			if (parsedTransaction.hasValidType()) {
				successfulParses++;
			}
			parsedTransactions.add(parsedTransaction);
		}
		totalParses = parsedTransactions.size();
		return parsedTransactions;
	}
	
	
	public ParsedTransaction parseTransaction(Transaction transaction) {
		String description = transaction.getDescription();
		Identifier identifier = identifierRegistry.findIdentifier(description);
		return new ParsedTransactionBuilder(transaction, account)
				.applyIdentifier(identifier)
				.build();
	}
	
	public double getSuccessRateOfLastRun() {
		return successfulParses / totalParses;
	}
	
	public String getSuccessRatioMessage() {
		return String.format("%d out of %d transactions parsed", successfulParses, totalParses);
	}
}
