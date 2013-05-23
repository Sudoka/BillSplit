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

	public static Account getCurrentAccount() {
		return currentAccount;
	}
	public static void setCurrentAccount(Account currentAccount) {
		Account.currentAccount = currentAccount;
	}
	public Collection<String> getSettings() {
		return settings;
	}
	public void setSettings(Collection<String> settings) {
		this.settings = settings;
	}
	public Collection<Account> getPastRelations() {
		return pastRelations;
	}
	public void setPastRelations(Collection<Account> pastRelations) {
		this.pastRelations = pastRelations;
	}
	public double getTotalDue() {
		return totalDue;
	}
	public void setTotalDue(double totalDue) {
		this.totalDue = totalDue;
	}
	public double getTotalYouOwe() {
		return totalYouOwe;
	}
	public void setTotalYouOwe(double totalYouOwe) {
		this.totalYouOwe = totalYouOwe;
	}
	public double getTotalOwed() {
		return totalOwed;
	}
	public void setTotalOwed(double totalOwed) {
		this.totalOwed = totalOwed;
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
		return events;
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
	private Collection<Event> events=null;
	private String name;
	private double netBalance;
	private double totalOwed;
	private double totalPaid;
	private double balance;
	private double totalYouOwe;
	private double totalDue;
	private Collection<Account> pastRelations=null;
	private Collection<String> settings=null;
	private static Account currentAccount=null;
}
