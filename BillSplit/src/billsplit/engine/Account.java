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
	
	public Account(String name) {
		GID = "unknown";
		this.name = name;
		events = new ArrayList<Event>();
		pastRelations = new ArrayList<Account>();
		settings = new ArrayList<String>();
	}
	
	/*
	 * This method should be called when the user creates a new account in the first-time use scenario
	 * */
	public static Account createNewAccount(String GID, String name) {
		Account newAccount = new Account(GID, name); 
		Account.currentAccount = newAccount;
		return newAccount;
	}
	
	public Event createEvent(String name) {
		Event newEvent = new Event(this.GID, name);
		events.add(newEvent);
		return newEvent;
	}
	
	Event createEvent(String name, String category) {
		Event newEvent = new Event(this.GID, name, category);
		events.add(newEvent);
		return newEvent;
	}
	
	/*
	 * Test method - returns account. Account.currentAccount is set to the account returned; two test events are created with some activities
	 */
	public static Account test() {
		Account newAccount = createNewAccount("address@email.com", "Test Account");
		
		//Create participants - accounts first
		Account partAccount1 = new Account("Manuel");
		Account partAccount2 = new Account("Tatenda");
		Account partAccount3 = new Account("Dan");
		Account partAccount4 = new Account("Kirill");
		
		//Creates first event - Trip
		newAccount.createEvent("Trip", "Short Term");
		
		//Add participants
		newAccount.events.get(0).addParticipant(new Participant(partAccount1));
		newAccount.events.get(0).addParticipant(new Participant(partAccount2));
		newAccount.events.get(0).addParticipant(new Participant(partAccount3));
		newAccount.events.get(0).addParticipant(new Participant(partAccount4));
		
		Event firstEvent = newAccount.events.get(0);
		ArrayList<Participant> firstParts = (ArrayList) firstEvent.getParticipants();
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("Bread1", 10.0));
		items.add(new Item("Bread2", 20.0));
		items.add(new Item("Bread3", 30.0));
		items.add(new Item("Bread4", 40.0));
		
		//Add activities
		firstEvent.addBalanceChange(new Transaction(firstParts, items));
		firstEvent.addBalanceChange(new Transaction(firstParts, items));
		//Could add more activities (transactions or payments here)
		
		//Creates second event - Household - and two three activities to it
		newAccount.createEvent("Household", "Long Term");
		
		ArrayList<Item> items2 = new ArrayList<Item>();
		items2.add(new Item("Meat1", 10.0));
		items2.add(new Item("Meat2", 20.0));
		items2.add(new Item("Meat3", 30.0));
		items2.add(new Item("Meat4", 40.0));		
		
		
		//Add participants
		newAccount.events.get(1).addParticipant(new Participant(partAccount1));
		newAccount.events.get(1).addParticipant(new Participant(partAccount2));
		
		
		Event secondEvent = newAccount.events.get(1);
		ArrayList<Participant> secondParts = (ArrayList) secondEvent.getParticipants();
		
		//Add activities
		secondEvent.addBalanceChange(new Transaction(secondParts, items2));
		secondEvent.addBalanceChange(new Transaction(secondParts, items2));
		
		return newAccount;
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
	
	/* 
	 * TODO: Modify to use iterators 
	 * */
	
	Collection<Account> listParticipants() {
		updatePastRelations();
		return pastRelations;
	}
	
	private void updatePastRelations() {
		
		for (int i=0; i<events.size(); i++) {
			ArrayList<Participant> currentParticipants = (ArrayList) events.get(i).getParticipants(); 
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
	
	static public void main(String[] args){
		test();
	}
}
