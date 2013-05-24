package billsplit.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

public class Transaction extends BalanceChange {
	
	private Event eventOwner;
	private PaymentMatrix matrix;
	
	/*
	 * Constructors
	 */
	
	public Transaction(ArrayList<Participant> participants) {
		// call other constructor w/ empty list of items
		this(participants, new ArrayList<Item>());
	}
	
	public Transaction(ArrayList<Participant> participants, ArrayList<Item> items) {
		super(participants,new ArrayList<Double>()); //parent BalanceChange constructor
		this.matrix = new PaymentMatrix(participants,items);
	}
	
	/*
	 * Getter, setter and checker methods
	 */
	
	public boolean containsItem(Item item) { 
		return this.matrix.contains(item);
	}
	
	public ArrayList<Item> getItems() {
		return this.matrix.getItems();
	}
	
	public void removeItem(Item item) {
		this.matrix.removeItem(item);
	}
	
	public boolean addItem(Item item) {
		return this.matrix.addItem(item);
	}
	
	public Event getEventOwner() {
		return eventOwner;
	}

	public void setEventOwner(Event eventOwner) {
		this.eventOwner = eventOwner;
	}
	
	/*
	 * The following methods manipulate the actual payers/payees
	 */
	
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
		//for (int i=0;i<participants.size();i++) {
		//	matrix.get(i)
		//}
	}
	
	public void clearPayers(Item item) {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void fromExternal() {
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}

class PaymentMatrix {
	
	private ArrayList<ArrayList<Double>> m; //will be accessed m[participant #][item #] = amount
	private HashMap<Participant,Integer> participantMap;
	private HashMap<Item,Integer> itemMap;
	
	public PaymentMatrix(ArrayList<Participant> participants,
			ArrayList<Item> items) {
		// Create 2d array using arraylists, initialized all to 0.0
		m = new ArrayList<ArrayList<Double>>(participants.size());
		for (int i=0; i<participants.size(); i++) {
			ArrayList<Double> newRow = (new ArrayList<Double>(items.size()));
			for (int j=0; j<items.size(); j++) {
				newRow.set(j, Double.valueOf(0.0));
			}
			m.set(i,newRow);
		}
	}
	
	public boolean contains(Participant p) {
		return participantMap.containsKey(p);
	}
	
	public boolean contains(Item i) {
		return itemMap.containsKey(i);
	}
	
	public void removeParticipant(Participant p) throws Exception{
		int pid = this.getParticipantID(p);
		participantMap.remove(p);
		m.remove(pid);
		
	}
	
	public void removeItem(Item i) {
		int iid = this.getItemID(i);
		itemMap.remove(i);
		for (int j=0; j<m.size();j++) {
			m.get(j).remove(iid);
		}
		m.remove(iid);
	}
	
	public boolean addParticipant(Participant p){
		if (this.contains(p)) {
			return false;
		} else {
			int newpid = participantMap.size();
			participantMap.put(p, newpid);
			ArrayList<Double> newRow = new ArrayList<Double>(itemMap.size());
			for (int j=0; j<itemMap.size();j++) { newRow.set(j, Double.valueOf(0.0)); }
			m.add(newRow); // add new participant column
			return true;
		}
	}
	
	public boolean addItem(Item i) {
		if (this.contains(i)) {
			return false;
		} else {
			int newiid = itemMap.size();
			itemMap.put(i, newiid);
			for (int j=0; j<m.size(); j++) {
				m.get(j).add(0.0); // add new item to each participant's column
			}
			return true;
		}
	}
	
	public double getAmount(Participant p, Item i) {
		int pid = this.getParticipantID(p);
		int iid = this.getItemID(i);
		return m.get(pid).get(iid);
	}
	
	public void setAmount(Participant p, Item i, double amount) {
		int pid = this.getParticipantID(p);
		int iid = this.getItemID(i);
		m.get(pid).set(iid, amount);
	}
	
	private int getParticipantID(Participant p) throws RuntimeException  {
		if (this.contains(p)) {
			int pid = participantMap.get(p);
			return pid;
		} else {
			throw new RuntimeException(String.format("Participant %s does not exist in this PaymentMatrix.",p.getName()));
		}
	}
	
	private int getItemID(Item i) throws RuntimeException  {
		if (this.contains(i)) {
			int iid = itemMap.get(i);
			return iid;
		} else {
			throw new RuntimeException(String.format("Item %s does not exist in this PaymentMatrix.",i.getName()));
		}
	}
	
	public ArrayList<Item> getItems() {
		ArrayList<Item> items = new ArrayList<Item>(itemMap.size());
				
		for (HashMap.Entry<Item, Integer> entry : itemMap.entrySet()) {
		    Item key = entry.getKey();
		    int value = entry.getValue();
		    items.set(value, key);
		}
		
		return items;
	}
	
	public ArrayList<Participant> getParticipants() {
		ArrayList<Participant> participants = new ArrayList<Participant>(participantMap.size());
				
		for (HashMap.Entry<Participant, Integer> entry : participantMap.entrySet()) {
			Participant key = entry.getKey();
		    int value = entry.getValue();
		    participants.set(value, key);
		}
		
		return participants;
	}

	
}
