/**
 * 
 */
package billsplit.engine;
import java.util.*;

/**
 * @author kmakarov
 *
 */

public class Account {

    private String GID;
	private ArrayList<Event> events=null;
	private String name;
	private double netBalance;
	private double totalOwedToYou;
	private double balance;
	private double totalYouOwe;
	private ArrayList<Account> pastRelations=null;
	private ArrayList<String> settings=null;
	private static Account currentAccount=null;
	
	/*
	 * Account for a known GID
	 */
	
	public Account(String GID, String name) {
		this.GID = GID;
		this.name = name;
		events = new ArrayList<Event>();
		pastRelations = new ArrayList<Account>();
		settings = new ArrayList<String>();
	}
	
	/*
	 * Account for an unknown contact
	 */
	
	public Account() {
		GID = "unknown";
		name = "Account";
		events = new ArrayList<Event>();
		pastRelations = new ArrayList<Account>();
		settings = new ArrayList<String>();
	}
	
	/*
	 * This method should be called when the user creates a new account in the first-time use scenario
	 * */
	public Account createNewAccount(String GID, String name) {
		Account newAccount = new Account(GID, name); 
		Account.currentAccount = newAccount;
		return newAccount;
	}
	
	Event createEvent(String c){
		Event newEvent = new Event(this.GID, c);
		events.add(newEvent);
		return newEvent;
	}
	
	/*
	 * The methods below return all events sorted by various parameters (TODO - comparators not implemented yet)  
	 * */
	AbstractList<Event> getEventByCreateDate(Date d){
		return events;
	}
	
	AbstractList<Event> getEventByEditDate(Date d) {
		return events;
	}

	AbstractList<Event> getEventByParticipantID(String participantID) {
		return events;
	}

	AbstractList<Event> getEventByName(String eventName) {
		return events;
	}

	AbstractList<Event> getEventByCreator(String creator) {
		return events;
	}

	AbstractList<Event> getEventByCategory(String category) {
		return events;
	}

	AbstractList<Event> getAllEventSortedByTotalSpent() {
		return events;
	}

	AbstractList<Event> getAllEventSortedByTotalOwed() {
		return events;
	}
	
	double viewTotalBalance() {
		return balance;
	}
	double viewTotalOwed() {
		return totalOwedToYou;
	}
	
	double viewTotalDue() {
		return totalYouOwe;
	}
	
	AbstractList<Account> listParticipants() {
		updatePastRelations();
		return pastRelations;
	}
	
	private void updatePastRelations() {
		
		for (int i=0; i<events.size(); i++) {
			AbstractList<Participant> currentParticipants = events.get(i).getParticipants(); 
			for (int j=0; j<currentParticipants.size(); j++) {
				Participant currentParticipant = events.get(i).getParticipants().get(j);
				if (!pastRelations.contains(currentParticipant) && currentParticipant.getAccount() != null) {
					pastRelations.add(currentParticipant.getAccount());
				}
			}
		}
	}
	
	/* 
	 * TODO
	 * This method returns up to 10 of the last balance changes (if available)
	 * */
	AbstractList<BalanceChange> recentActivity() {
		ArrayList<BalanceChange> activity = new ArrayList<BalanceChange>();
		
		int i=10;
		while (i > 0) {
			//TODO
			i--;
		}
		
		return activity;
	}
	
	public static Account getCurrentAccount() {
		return currentAccount;
	}
	public static void setCurrentAccount(Account currentAccount) {
		Account.currentAccount = currentAccount;
	}
	public AbstractList<String> getSettings() {
		return (AbstractList<String>) settings;
	}
	
	//TODO Assume array list for now; Change later
	public void setSettings(AbstractList<String> settings) {
		this.settings = (ArrayList<String>) settings;
	}
	public AbstractList<Account> getPastRelations() {
		return (AbstractList<Account>) pastRelations;
	}
	
	//TODO Assume array list for now; Change later
	public void setPastRelations(AbstractList<Account> pastRelations) {
		this.pastRelations = (ArrayList<Account>) pastRelations;
	}
	
	public double getTotalYouOwe() {
		return totalYouOwe;
	}
	public void setTotalYouOwe(double totalYouOwe) {
		this.totalYouOwe = totalYouOwe;
	}
	public double getTotalOwed() {
		return totalOwedToYou;
	}
	public void setTotalOwed(double totalOwedToYou) {
		this.totalOwedToYou = totalOwedToYou;
	}
	public double getNetBalance() {
		return netBalance;
	}
	public void setNetBalance(double netBalance) {
		this.netBalance = netBalance;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public AbstractList<Event> getEvents() {
		return (AbstractList<Event>) events;
	}
	
	//TODO Assume array list for now; Change later
	public void setEvents(AbstractList<Event> events) {
		this.events = (ArrayList<Event>) events;
	}

	
	public String getGID() {
		return GID;
	}
	public void setGID(String GID) {
		this.GID = GID;
	}
	
}
