package proj.core.beans;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

import proj.core.utils.StringUtil;

public class Transaction {
	
	@CsvBindByName
    private double amount;
	
	@CsvDate("MM/dd/yyyy")
	@CsvBindByName
    private Date date;

	@CsvBindByName
    private String description;
	
	public Transaction() {
		super();
	}
	
	public Transaction(Date date, String description, double amount) {
		this.date = date;
		this.description = description;
		this.amount = amount;
	}
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Transaction) {
			Transaction other = (Transaction) object;
			if (other.amount == this.amount
					&& other.date.equals(this.date)
					&& StringUtil.equalsWithFilters(this.description, other.description, true, true)) {
				return true;
			}
	    }
		return false;
	}
	
	public double getAmount() {
		return amount;
	}
	
	
	public Date getDate() {
		return date;
	}
	

	public String getDescription() {
		return description;
	}
	
	
	public void setAmount(double amount) {
		this.amount = amount;
	}

	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	public void setDescription(String description) {
		this.description = description.toUpperCase();
	}
}
