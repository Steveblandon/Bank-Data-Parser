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
	
	@Override
	public boolean equals(Object object) {
		if (object instanceof Transaction) {
			Transaction other = (Transaction) object;
			if (other.amount == this.amount
			  && other.date.equals(this.date)
			  && other.description == this.description) {
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
		this.description = description;
	}
}
