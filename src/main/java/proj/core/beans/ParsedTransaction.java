package proj.core.beans;

import com.opencsv.bean.CsvBindByName;

import proj.core.utils.StringUtil;

public class ParsedTransaction extends Transaction {
	
	public static final String TYPE_INCOME = "income";
	public static final String TYPE_EXPENSE = "expense";
	
	@CsvBindByName
	private String account;
	
	@CsvBindByName
	private String category;
	
	@CsvBindByName
	private String notes;
	
	@CsvBindByName
	private String type;
	
	public ParsedTransaction() {
		super();
	}
	
	public ParsedTransaction(Transaction transaction) {
		this.setAmount(transaction.getAmount());
		this.setDate(transaction.getDate());
		this.setDescription(transaction.getDescription());
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof ParsedTransaction) {
			ParsedTransaction other = (ParsedTransaction) object;
			
			if (super.equals(object)
					&& StringUtil.equalsWithFilters(this.account, other.account, true, true)
					&& StringUtil.equalsWithFilters(this.category, other.category, true, true)
					&& StringUtil.equalsWithFilters(this.notes, other.notes, true, true)
					&& StringUtil.equalsWithFilters(this.type, other.type, true, true)) {
				return true;
			}
	    }
		return false;
	}
	
	
	public String getAccount() {
		return account;
	}

	
	public String getCategory() {
		return category;
	}

	
	public String getNotes() {
		return notes;
	}

	
	public String getType() {
		return type;
	}
	
	public boolean hasValidType() {
		return (type.equals(TYPE_EXPENSE) || type.equals(TYPE_INCOME));
	}

	
	public void setAccount(String account) {
		this.account = account;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}

	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	
	public void setType(String type) {
		this.type = type;
	}
	
	
	public void copyAllFrom(ParsedTransaction parsedTransaction) {
		this.account = parsedTransaction.account;
		this.category = parsedTransaction.category;
		this.notes = parsedTransaction.notes;
		this.type = parsedTransaction.type;
		this.setAmount(parsedTransaction.getAmount());
		this.setDate(parsedTransaction.getDate());
		this.setDescription(parsedTransaction.getDescription());
	}
}
