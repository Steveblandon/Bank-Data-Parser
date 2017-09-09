package proj.core;

import com.opencsv.bean.CsvBindByName;

public class ParsedTransaction extends Transaction {
	
	@CsvBindByName
	private String account;
	
	@CsvBindByName
	private String category;
	
	@CsvBindByName
	private String notes;
	
	@CsvBindByName
	private String type;
	
	
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
