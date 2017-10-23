package proj.core;

import java.util.Date;

import proj.core.beans.DefaultIdentifier;
import proj.core.beans.Identifier;
import proj.core.beans.ParsedTransaction;
import proj.core.beans.Transaction;


public class ParsedTransactionBuilder {
	
	private Identifier identifier;
	private double amount;
    private Date date;
    private String description;
	private String account;
	private String category;
	private String notes;
	private String type;
	
	public ParsedTransactionBuilder(Transaction transaction, String account) {
		amount = transaction.getAmount();
		date = transaction.getDate();
		description = transaction.getDescription();
		notes = "";
		this.account = account;
	}
	
	
	public ParsedTransactionBuilder applyIdentifier(Identifier identifier) {
		this.identifier = identifier;
		if (identifier instanceof DefaultIdentifier) {
			applyDefaultIdentifier();
		}
		else {
			applyIdentifier();
		}
		return this;
	}
	
	
	public ParsedTransaction build() {
		ParsedTransaction parsedTransaction = new ParsedTransaction();
		parsedTransaction.setAccount(account);
		parsedTransaction.setAmount(amount);
		parsedTransaction.setCategory(category);
		parsedTransaction.setDate(date);
		parsedTransaction.setDescription(description);
		parsedTransaction.setNotes(notes);
		parsedTransaction.setType(type);
		return parsedTransaction;
	}
	
	
	private void applyDefaultIdentifier() {
		type = DefaultIdentifier.TYPE_UNKNOWN;
		category = DefaultIdentifier.CATEGORY_UNKNOWN;
	}
	
	
	private void applyIdentifier() {
		applyIdentifierType();
		applyIdentifierCategory();
		description = identifier.getDescriptor();
	}
	
	
	private void applyIdentifierCategory() {
		if (identifier.isAmbiguous()) {
			category = DefaultIdentifier.CATEGORY_UNKNOWN;
		}
		else {
			category = identifier.getCategory();
		}
	}
	
	
	private void applyIdentifierType() {
		if (identifier.getType().equalsIgnoreCase(DefaultIdentifier.TYPE_UNKNOWN)) {
			if (amount > 0) {
				type = ParsedTransaction.TYPE_INCOME;
			}
			else {
				type = ParsedTransaction.TYPE_EXPENSE;
			}
		}
		else {
			type = identifier.getType().toLowerCase();
		}
	}
}
