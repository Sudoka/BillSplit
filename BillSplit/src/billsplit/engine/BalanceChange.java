package billsplit.engine;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.lang.UnsupportedOperationException;


public abstract class BalanceChange {
	protected Date date;
	protected String name;
	protected ArrayList<PersonalBalanceChange> amounts;
	protected ArrayList<Participant> participants;
	protected String details;
	protected String category;
	
	protected BalanceChange() {}
	
	protected BalanceChange(ArrayList<Participant> participants2,
			ArrayList<Double> amounts2) {
		// TODO Auto-generated constructor stub
		this.participants = participants2;
		for (int i=0; i<participants2.size(); i++) {
			Participant p = participants2.get(i);
			double a = amounts2.get(i);
			this.amounts.add(new PersonalBalanceChange(p,a));		
		}
	}

	public boolean containsParticipant(Participant p) {
		return this.participants.contains(p);
	}
	
	public ArrayList<Participant> getParticipants(){
		return this.participants;
	}
	
	public int addParticipant(Participant p){
		this.participants.add(p);
		int newidx = this.participants.size();
		return newidx;
	}

	public void removeParticipant(Participant p){
		this.participants.remove(p);
	}
	
	public void removeParticipant(int index){
		this.participants.remove(index);
	}
	
	public void setAmountForPerson(Participant p, double amount){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public HashMap<Participant,Double> getAmounts(){
		HashMap<Participant,Double> ret = new HashMap<Participant,Double>();
		for (int i=0; i<this.participants.size(); i++) {
			Participant participant = this.participants.get(i);
			double amt = this.amounts.get(i).amount;
			ret.put(participant, amt);
		}
		return ret;
	}
	
	public double getAmount(Participant p){
		int index = this.participants.indexOf(p);
		double amount = this.amounts.get(index).amount;
		return amount;
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