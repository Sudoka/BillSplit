package billsplit.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public abstract class BalanceChange {
	private Date date;
	private String name;
	protected HashMap<Participant,Double> amounts; //unordered (its a hash)
	protected ArrayList<Participant> participants; //ordered list of participants
	
	protected BalanceChange(String name, ArrayList<Participant> participants) {
		//Call other constructor, but with empty amount array
		this(name, participants, new ArrayList<Double>());
	}
	
	protected BalanceChange(String name, ArrayList<Participant> participants, ArrayList<Double> amounts) {
		// The 'real' constructor that all other constructors call
		this.name = name;
		this.amounts = new HashMap<Participant,Double>();
		this.participants = participants;
		for (int i=0; i<participants.size(); i++) {
			Participant p = participants.get(i);
			//double a = amounts.get(i);
			this.amounts.put(p, 0.0);		
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
	
}