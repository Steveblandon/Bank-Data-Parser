package proj.core;

import com.opencsv.bean.CsvBindByName;

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
			  && other.account == this.account
			  && other.category == this.category
			  && other.notes == this.notes
			  && other.type == this.type) {
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
}
