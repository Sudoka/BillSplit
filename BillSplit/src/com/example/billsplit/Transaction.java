package com.example.billsplit;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

public class Transaction extends BalanceChange {
	private Hashtable<Participant,Item> paymentMatrix;
	private Hashtable<Item,Participant> itemMatrix;
	private Date transactionDate;
	private Event eventOwner;
	private ArrayList<Item> items;
	private String category;
	
	
	public Transaction(ArrayList<Participant> participants, String name) {}
	
	public boolean containsParticipant(Participant p) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public boolean containsItem(Item item) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void removeParticipant(Participant p) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public ArrayList<Item> getItems() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void removeItem(Item item) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public boolean removeItem(int itemIndex) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public int addItem(Item item) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setPayers(Item item, ArrayList<Participant> participants) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void addPayer(Item item, Participant p) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void removePayer(Item item, Participant p) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void splitAllEvenly() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void clearPayers(Item item) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public String getCategory() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setCategory(String category) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public String getDetails() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setDetails(String details) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void fromExternal() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}
