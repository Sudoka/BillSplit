package billsplit.engine;

import java.util.ArrayList;
import java.util.Date;
import java.lang.UnsupportedOperationException;


public abstract class BalanceChange {
	private Date date;
	private String name;
	private ArrayList<PersonalBalanceChange> amounts;
	private ArrayList<Participant> participants;
	private String details;
	
	public ArrayList<Participant> getParticipants(){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public int addParticipant(Participant p){
		throw new UnsupportedOperationException("Not implemented yet.");
	}

	public void removeParticipant(Participant p){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void removeParticipant(int index){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setAmountForPerson(Participant p, double amount){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public ArrayList<Double> getAmounts(){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public double getAmount(Participant p){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public String getName(){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public String setName(String name){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public Date getDate(){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
	
	public void setDate(){
		throw new UnsupportedOperationException("Not implemented yet.");
	}
}