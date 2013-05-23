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

	double viewTotalBalance() {
		return balance;
	}
	double viewTotalOwed() {
		return totalOwedToYou;
	}
	
	double viewTotalDue() {
		return totalYouOwe;
	}
	
	Collection<Account> listParticipants() {
		updatePastRelations();
		return pastRelations;
	}
	
	void updatePastRelations() {
		
		ArrayList<Account> allParticipants = new ArrayList<Account>();
		
		for (int i=0; i<events.size(); i++) {
			ArrayList<Participant> currentParticipants = events.get(i).getParticipants(); 
			for (int j=0; j<currentParticipants.size(); j++) {
				if (pastRelations.contains(events.get(i).getParticipants().get(j))) {
					
				}
			}
		}
	}
	
	Collection<BalanceChange> recentActivity() {
		
	}
	
	public static Account getCurrentAccount() {
		return currentAccount;
	}
	public static void setCurrentAccount(Account currentAccount) {
		Account.currentAccount = currentAccount;
	}
	public Collection<String> getSettings() {
		return (Collection<String>) settings;
	}
	
	//TODO Assume array list for now; Change later
	public void setSettings(Collection<String> settings) {
		this.settings = (ArrayList<String>) settings;
	}
	public Collection<Account> getPastRelations() {
		return (Collection<Account>) pastRelations;
	}
	
	//TODO Assume array list for now; Change later
	public void setPastRelations(Collection<Account> pastRelations) {
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
	public Collection<Event> getEvents() {
		return (Collection) events;
	}
	public void setEvents(Collection<Event> events) {
		this.events = events;
	}

	
	public String getGID() {
		return GID;
	}
	public void setGID(String GID) {
		this.GID = GID;
	}

	private String GID;
	private ArrayList<Event> events=null;
	private String name;
	private double netBalance;
	private double totalOwedToYou;
	private double totalPaid;
	private double balance;
	private double totalYouOwe;
	private ArrayList<Account> pastRelations=null;
	private ArrayList<String> settings=null;
	private static Account currentAccount=null;
}
