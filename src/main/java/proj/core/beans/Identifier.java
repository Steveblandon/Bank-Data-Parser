package proj.core.beans;

import com.opencsv.bean.CsvBindByName;

public class Identifier {

	public static final String CATEGORY_UNKNOWN = "UNKNOWN";
	public static final String TYPE_UNKNOWN = "UNKNOWN";
	
	@CsvBindByName
	private boolean ambiguous;
	
	@CsvBindByName
	private String category;
	
	@CsvBindByName
	private String descriptor;
	
	@CsvBindByName(column = "identifier")
	private String id;
	
	@CsvBindByName
	private String type;

	
	public boolean isAmbiguous() {
		return ambiguous;
	}

	
	public String getCategory() {
		return category;
	}

	
	public String getDescriptor() {
		return descriptor;
	}

	
	public String getId() {
		return id;
	}

	
	public String getType() {
		return type;
	}

	
	public void setAmbiguous(boolean ambiguous) {
		this.ambiguous = ambiguous;
	}

	
	public void setCategory(String category) {
		this.category = category;
	}

	
	public void setDescriptor(String descriptor) {
		this.descriptor = descriptor;
	}

	
	public void setId(String id) {
		this.id = id.toUpperCase();
	}

	
	public void setType(String type) {
		this.type = type;
	}
}
