package billsplit.engine;

import java.io.Serializable;

public class Item implements Serializable {
	public static Item currentItem;
	private String name;
	private double cost;
	
	public Item(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}
	
	public String toString() {
		String str = String.format("Item(name=%s|cost=%f)",getName(),getCost());
		return str;
	}
	
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
}
