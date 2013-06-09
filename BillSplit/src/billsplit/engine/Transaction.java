package billsplit.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Collection;

public class Transaction extends BalanceChange {
	public static Transaction current;
	private DebtMatrix matrix;
	//private HashMap<Participant,Double> paymentMap;
	
	/*
	 * Constructors
	 */
	
	public Transaction(String name, Collection<Participant>participants) {
		// call other constructor w/ empty list of items
		this(name, participants, new ArrayList<Item>());
	}
	
	public Transaction(String name, Collection<Participant> participants, ArrayList<Item> items) {
		super(name,participants); //parent BalanceChange constructor
		this.matrix = new DebtMatrix(participants,items);
		//this.paymentMap = new HashMap<Participant,Double>(); dacashman remove me!
	}
	
	public String toString() {
		String str = String.format("Transaction(name=%s)",getName());
		return str;
	}
	
	public String toStringDebtMatrix() {
		return matrix.toString();
	}
	
	public ArrayList<Item> getItems() {
		return this.matrix.getItems();
	}
	
	/* item-bool pairs where bool is true if item 'done' */
	public HashMap<Item, Boolean> getItemsBools() {
		HashMap<Item, Boolean> hashy = new HashMap<Item, Boolean>();
		ArrayList<Item> itemList = getItems();
		for(Item i : itemList){
			hashy.put(i, debtItemDone(i));
		}
		return hashy;
	}
	
	public boolean containsItem(Item item) { 
		return this.matrix.contains(item);
	}
	
	public boolean addItem(Item item) {
		return this.matrix.addItem(item);
	}
	
	public void removeItem(Item item) {
		this.matrix.removeItem(item);
		this.updateDebits();
	}
	
	
	
	/**
	 * dacashman -see EDIT below. 
	 * Returns true only if all the debt for this transaction equals the amount that has been
	 * specified as payment.
	 * 
	 * EDIT: this function is now taken care of by BalanceChange superclass in getDebitCreditDiff()
	 * @return
	 */
	
	public boolean allDebtPaidFor() {
		/*double total = 0.0;
		for (Participant p : this.getParticipants()) {
			total += this.getAmount(p);
		}
		if (total == 0.0) {
			return true;
		} else {
			return false;
		} */
		return isPaymentComplete();
	}
	
	
	
	

	/**
	 * PRIVATE: Call the setDebit method inherited from balanceChange for each
	 * participant in this Transaction. Call this method after any changes to
	 * make sure the overall Debit totals are correct (should be very quick to run).
	 */
	private void updateDebits() {
		// First subtract debts from all participants
		for (Participant p : this.participants) {
			double debtAmt = this.matrix.getTotalForParticipant(p);
			this.setDebit(p, debtAmt);
		}
	}
	
	/////////////////////////////////////////////////////////////
	// Methods below are for assigning debt
	/////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @param p
	 * @param item
	 * @return Amount the participant owes for the item
	 */
	public double debtGetItemAmountParticipant(Participant p, Item item) {
		return matrix.getAmount(p, item);
	}
	
	/**
	 * 
	 * @param p
	 * @return Total amount the participant owes for all items
	 */
	public double debtGetTotalAmountParticipant(Participant p) {
		return matrix.getTotalForParticipant(p);
	}
	
	/**
	 * Get the total debt (cost) of all items in this transaction.  This 
	 *   repeated functionality of getDebitsTotal().
	 * @return
	 */
	public double debtGetTotal() {
		/* removed due to duplication:
		 double total = 0.0;
		for (Participant p : this.getParticipants()) {
			total += matrix.getTotalForParticipant(p);
		}
		return total; */
		return getDebitsTotal();
	}
	
	/**
	 * Returns the amount of the item's cost that has not yet been assigned to
	 * a participant as debt. May be a negative number if more debt has been assigned
	 * than the cost of the item.
	 * @param item
	 * @return
	 */
	public double debtGetItemDebtRemaining(Item item) {
		return matrix.getItemRemainingDebt(item);
	}
	
	/** Return true only if the specified item's cost has been completely
	 * assigned to one or more participants as debt.
	 * @param item
	 * @return
	 */
	public boolean debtItemDone(Item item) {
		return debtGetItemDebtRemaining(item) == 0.0;
	}
	
	/**
	 * Returns true only if all items in this transaction are done
	 * (that is, have all their debt assigned to a participant)
	 * @return
	 */
	public boolean debtAllItemsDone() {
		for (Item item : this.getItems()) {
			if (debtItemDone(item) == false) return false;
		}
		return true;
	}
	
	/**
	 * Literally splits every item cost evenly between all available participants.
	 * We might want to revisit how useful this even is...
	 */
	public void debtAllEvenly() {
		ArrayList<Item> items = this.matrix.getItems();
		ArrayList<Participant> participants = this.matrix.getParticipants();
		for (int j=0; j<items.size();j++) {
			Item item = items.get(j);
			debtItemEvenly(item, participants); //split item among all participants evenly
		}
	}
	
	/**
	 * Clear any existing payers set for item, and then evenly split that item
	 * cost among the specified participants.
	 * @param item
	 * @param participants
	 */
	public void debtItemEvenly(Item item, ArrayList<Participant> participants) {
		//todo: check that there are more than 0 participants
		matrix.reset(item); //remove any other payments on the item as a starting point
		double amtPerPerson = item.getCost() / participants.size();
		for (int j=0; j<participants.size(); j++) {
			Participant p = participants.get(j);
			matrix.setAmount(p, item, amtPerPerson);
		}
		updateDebits();
	}
	
	/**
	 * Exactly specify the debt that a particular participant should be assigned for a particular
	 * item.
	 * @param item
	 * @param p
	 * @param debtAmount
	 */
	public void debtSet(Item item, Participant p, double debtAmount) {
		matrix.setAmount(p, item, debtAmount);
		updateDebits();
	}
	
	/**
	 * Using this method ASSUMES that you want to split debt evenly between the 
	 * participants currently assigned to the item and the new participant p.
	 * It will reset any custom payment values for all other participants already
	 * assigned to pay for item to an even split between all involved.
	 * @param item
	 * @param p
	 */
	public void debtAddParticipant(Item item, Participant p) {
		ArrayList<Participant> currentPayers = matrix.getPayers(item);
		currentPayers.add(p);
		debtItemEvenly(item, currentPayers); //evenly split btw new set of payers
	}
	
	/**
	 * Removes a payer from an item. Does NOT adjust the amounts that other payers are 
	 * paying for that item (call setPayers() again on the remaining payers to redistribute
	 * the amount evenly between them). Also note, this does not remove that participant
	 * from the overall transaction; it only zeros their amount for that particular item.
	 * @param item
	 * @param p
	 */
	public void debtRemoveParticipant(Item item, Participant p) {
		matrix.setAmount(p, item, 0.0);
		updateDebits();
	}
	
	/**
	 * For a particular item, resets any debt assignments made on it. No participants
	 * will have its cost debted to them.
	 * @param item
	 */
	public void debtResetItem(Item item) {
		matrix.reset(item);
		updateDebits();
	}
	
	/**
	 * Resets a participant's debt for all items. They will owe nothing across the board. 
	 * @param p
	 */
	public void debtResetParticipant(Participant p) {
		matrix.reset(p);
		updateDebits();
	}
		
	
	/////////////////////////////////////////////////////////////
	// Methods below are for specifying who pays what
	/////////////////////////////////////////////////////////////
	
	/**
	 * dacashman: replaced by BalanceChange setCredit()
	 * Sets the amount a participant is paying.
	 * @param amt
	 */
	/*
	public void paySetParticipantPayment(Participant p, double amt) {
		this.paymentMap.put(p, amt);
		updateDebits();
	} */
	
	/* dacashman: replaced by getCredit()
	public double payGetAmount(Participant p) {
		if (this.paymentMap.containsKey(p)) {
			return paymentMap.get(p);
		} else {
			return 0.0;
		}
	} */
	 
	/**
	 * Every participant pays for an equal amount of the total debt
	 */
	public void payEvenly() {
		double totalDebt = 0.0;
		for (Participant p : participants) totalDebt += matrix.getTotalForParticipant(p);
		double perPerson = totalDebt / participants.size();
		
		for (Participant p: participants) setCredit(p, perPerson);
	}
	
	/**
	 * Every participant pays their individual debt
	 */
	public void payFairly() {
		for (Participant p : participants) {
			double amt = matrix.getTotalForParticipant(p);
			setCredit(p, amt);
		}
	}
	
	/**
	 * Specify a custom amount paid by participants
	 */
	public void payCustom(HashMap<Participant, Double> paymentMap) {
		for (Map.Entry<Participant,Double> entry : paymentMap.entrySet()) {
			Participant p = entry.getKey();
			double amt = entry.getValue();
			setCredit(p, amt);
		}
	}
	
	public HashMap<Item, Boolean> getItemsBools() {
		return new HashMap<Item, Boolean>();
	}
	
	/**
	 * PaymentMatrix private inner class- only to be used by Transaction. Keeps track of all state
	 * of payments
	 * @author Danga
	 *
	 */
	class DebtMatrix {
		
		private ArrayList<ArrayList<Double>> m; //will be accessed m[participant #][item #] = amount
		private HashMap<Participant,Integer> participantMap;
		private HashMap<Item,Integer> itemMap;
		
		public DebtMatrix(Collection<Participant> participants,
				ArrayList<Item> items) {
			// Create 2d array using arraylists, initialized all to 0.0
			m = new ArrayList<ArrayList<Double>>();
			for (int i=0; i<participants.size(); i++) {
				ArrayList<Double> newRow = (new ArrayList<Double>());
				for (int j=0; j<items.size(); j++) {
					newRow.add(j, Double.valueOf(0.0));
				}
				m.add(i,newRow);
			}
			itemMap = new HashMap<Item,Integer>();
			participantMap = new HashMap<Participant,Integer>();
			
			for (Participant p : participants) addParticipant(p);
			for (Item item : items) addItem(item);
		}
		
		public boolean contains(Participant p) {
			return participantMap.containsKey(p);
		}
		
		public boolean contains(Item i) {
			return itemMap.containsKey(i);
		}
		
		public void removeParticipant(Participant p) {
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
				ArrayList<Double> newRow = new ArrayList<Double>();
				while(newRow.size() < itemMap.size()) newRow.add(null);
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
			ArrayList<Item> items = new ArrayList<Item>();
			while(items.size() < itemMap.size()) items.add(null);
					
			for (HashMap.Entry<Item, Integer> entry : itemMap.entrySet()) {
			    Item key = entry.getKey();
			    int value = entry.getValue();
			    items.set(value, key);
			}
			
			return items;
		}
		
		public ArrayList<Participant> getParticipants() {
			ArrayList<Participant> participants = new ArrayList<Participant>(participantMap.size());
			while(participants.size() < participantMap.size()) participants.add(null);
					
			for (HashMap.Entry<Participant, Integer> entry : participantMap.entrySet()) {
				Participant key = entry.getKey();
			    int value = entry.getValue();
			    participants.set(value, key);
			}
			
			return participants;
		}
		
		public void reset(Participant p) {
			int pid = this.getParticipantID(p);
			ArrayList<Double> thisRow = m.get(pid);
			for (int j=0; j<itemMap.size();j++) { thisRow.set(j, Double.valueOf(0.0)); }
		}
		
		public void reset(Item i) {
			int iid = this.getItemID(i);
			for (int j=0; j<m.size();j++) {
				m.get(j).set(iid,0.0); //reset amt to 0
			}
		}
		
		public ArrayList<Participant> getPayers(Item i) {
			int iid = this.getItemID(i);
			ArrayList<Participant> payers = new ArrayList<Participant>();
			for (int j=0; j<m.size();j++) {
				if (m.get(j).get(iid) > 0.0) {
					payers.add(this.getKeyByValue(participantMap, j));
				}
			}
			return payers;
		}
		
		public ArrayList<Item> getPurchases(Participant p) {
			int pid = this.getParticipantID(p);
			ArrayList<Item> items = new ArrayList<Item>();
			for (int j=0; j<itemMap.size();j++) {
				if (m.get(pid).get(j) > 0.0) {
					items.add(this.getKeyByValue(itemMap, j));
				}
			}
			return items;
		}
		
		public double getTotalForParticipant(Participant p) {
			double total = 0.0;
			ArrayList<Item> purchases = getPurchases(p);
			for (int j=0; j<purchases.size(); j++) {
				double contrib = this.getAmount(p, purchases.get(j));
				total += contrib;
			}
			return total;
		}
		
		public double getItemRemainingDebt(Item item) {
			double paidSoFar = 0.0;
			for (Participant p : this.getParticipants()) {
				paidSoFar += this.getAmount(p, item);
			}
			return item.getCost() - paidSoFar;
		}
		
		private <T, E> T getKeyByValue(Map<T, E> map, E value) {
		    for (Entry<T, E> entry : map.entrySet()) {
		        if (value.equals(entry.getValue())) {
		            return entry.getKey();
		        }
		    }
		    return null;
		}
		
		public String toString() {
			
			String str = "";
			
			// Print heading of items
			str += String.format("%10s |",""); //empty upper left corner
			for (int iid=0; iid<itemMap.size();iid++) {
				Item i = this.getKeyByValue(itemMap, iid);
				str += String.format("%-10s |",i.getName());
			}
			str+="\n";
			
			// Print all rows
			for (int pid=0; pid<m.size(); pid++) {
				Participant p = this.getKeyByValue(participantMap,pid);
				str += String.format("%10s |",p.getName());
				
				ArrayList<Double> rowOfItems = m.get(pid);

				for (int iid=0; iid<rowOfItems.size(); iid++) {
					Item i = this.getKeyByValue(itemMap, iid);
					double debt = this.getAmount(p, i);
					str += String.format("%-10.2f |", debt);
				}
				str += "\n";
			}
			
			return str;
		}
	}
	
	
	/***********************************************************************************************
	 * Methods not targetting version 1.0 release
	 * 
	 */
	/*
	public int addParticipant(Participant p){
		int idx = super.addParticipant(p);
		matrix.addParticipant(p);
		return idx;
	}
	
	public void removeParticipant(Participant p){
		super.removeParticipant(p);
		matrix.removeParticipant(p);
	}
	 */
	/*************************************************************************************************/
}


