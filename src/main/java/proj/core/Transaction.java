package proj.core;

import java.util.Date;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;

public class Transaction {
	
	@CsvBindByName
    private double amount;
	
	@CsvDate("MM/dd/yyyy")
	@CsvBindByName
    private Date date;

	@CsvBindByName
    private String description;
    
	
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
		this.description = description;
	}
}
