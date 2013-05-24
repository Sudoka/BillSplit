package billsplit.engine;

public class Item {
	private String name;
	private double cost;
	private String category;
	private String details;
	
	public Item(String name, float cost) {}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	
		
}
