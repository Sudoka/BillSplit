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
	
	public String getDetails() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setDetails(String details) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
		
}
