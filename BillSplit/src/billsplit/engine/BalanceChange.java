package billsplit.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.lang.UnsupportedOperationException;


public abstract class BalanceChange {
	protected Date date;
	protected String name;
	protected HashMap<Participant,Double> amounts; //unordered (its a hash)
	protected ArrayList<Participant> participants; //ordered list of participants
	protected String details;
	protected String category;
	
	protected BalanceChange() {
		//Just call other constructor but with empty starting arrays
		this(new ArrayList<Participant>(), new ArrayList<Double>());
	}
	
	protected BalanceChange(ArrayList<Participant> participants) {
		//Call other constructor, but with empty amount array
		this(participants, new ArrayList<Double>());
	}
	
	protected BalanceChange(ArrayList<Participant> participants, ArrayList<Double> amounts) {
		// The 'real' constructor that all other constructors call
		this.participants = participants;
		for (int i=0; i<participants.size(); i++) {
			Participant p = participants.get(i);
			double a = amounts.get(i);
			this.amounts.put(p, a);		
		}
	}

	public boolean containsParticipant(Participant p) {
		return this.participants.contains(p);
	}
	
	public ArrayList<Participant> getParticipants(){
		return this.participants;
	}
	
	public int addParticipant(Participant p){
		int newidx = this.participants.size();
		this.participants.add(p);
		this.amounts.put(p, 0.0);
		return newidx;
	}

	public void removeParticipant(Participant p){
		this.participants.remove(p);
		this.amounts.remove(p);
	}
	
	/** DEPRECATED: see my notes on the contract. I'd like to remove this, since an 'index' of a participant
	 * no longer matters. Just use getters/setters with the actual Participant object. (note: this method
	 * still functions until a decision is made; it just calls the other removeParticipant(Participant p) method)
	 * @param index
	 */
	public void removeParticipant(int index){
		Participant p = this.participants.get(index);
		this.removeParticipant(p);
	}
	
	public void setAmountForPerson(Participant p, double amount){
		this.amounts.put(p, amount);
	}
	
	public HashMap<Participant,Double> getAmounts(){
		return amounts;
	}
	
	public double getAmount(Participant p){
		return this.amounts.get(p);
	}
	
	public String getName(){
		return this.name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setDate(Date date){
		this.date = date;
	}
	

	public String getCategory() {
		return this.category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
}