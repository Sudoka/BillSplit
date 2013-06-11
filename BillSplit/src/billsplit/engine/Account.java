/**
 * 
 */
package billsplit.engine;
import java.util.*;
import java.io.*;

import android.content.Context;

/**
 * @author kmakarov
 *
 */

public class Account implements Serializable {

	private static final long serialVersionUID = 1587685686600386406L;
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
		
	////BASIC QUERIES////
	
	/*
	 * ensure Result == name 
	 * */
	public String getName() {
		return name;
	}
	
	/*
	 * ensure Result == events
	 */
	public AbstractList<Event> getEvents() {
		return (AbstractList<Event>) events;
	}
	
	/*
	 * require isEvent(String name) == true
	 * ensure result.name == name
	 */
	
	AbstractList<Event> getEventByName(String eventName) {
		return events;
	}
	
	/*
	 * require name != null	
	 * */
	public boolean isEvent(String eventName) {
		boolean result = false;
		for (Event e : events) {
			if (e.getName().equals(eventName)) {
				result = true;
			}
		}
		return result;
	}
	
	/*
	 * ensure Result == netBalance
	 * */	
	public double getNetBalance() {
		return netBalance;
	}
	
	/*
	 * ensure Result == totalOwedToYou
	 * */
	public double getTotalOwed() {
		return totalOwedToYou;
	}
	
	/*
	 * ensure Result == totalYouOwe
	 * */
	public double getTotalYouOwe() {
		return totalYouOwe;
	}

	/*
	 * ensure Result = Account.currentAccount
	 * */
	public static Account getCurrentAccount() {
		return currentAccount;
	}
	
	////END OF BASIC QUERIES////
	
	////CREATION COMMANDS////
	
	/*
	 * Account for a known GID
	 * require GID contains '@' character
	 * 
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
		this("unknown@billsplit.com", "Account");
	}
	
	public Account(String name) {
		this("unknown@billsplit.com", name);
	}
	
	/*
	 * This method should be called when the user creates a new account in the first-time use scenario
	 * */
	public static Account createNewAccount(String GID, String name) {
		
		String[] emailParts = GID.split("@"); 
		
		//Check if this Account is already stored
		String fileName = name + "_" + emailParts[0] + "_" + emailParts[1]; 
		
		File f = new File(fileName);
		
		if(f.exists()) {
		    try {
		        //use buffering
		        InputStream file = new FileInputStream(fileName);
		        InputStream buffer = new BufferedInputStream(file);
		        ObjectInput input = new ObjectInputStream(buffer);
		        
		        try {
			        //deserialize the Account object
			        Account recoveredAccount = (Account)input.readObject();
			        currentAccount = recoveredAccount;
			        return recoveredAccount;
		        }
		        
		        finally {
		        	input.close();
		        }
		      }
		    
		    catch(ClassNotFoundException ex){
		    	System.out.println("Cannot perform input. Class not found.");
		    	return createAndStoreAccount(fileName, GID, name);
		    }
		    catch(IOException ex){
		        System.out.println("Cannot perform input.");
		        return createAndStoreAccount(fileName, GID, name);
		    }
		    
		} else {
			return createAndStoreAccount(fileName, GID, name);
		}
	}
	
	/*a new version of createNewAccount that also takes in a context*/
	public static Account createNewAccount(String GID, String name, Context activity) {
		
		String[] emailParts = GID.split("@"); 
		
		//Check if this Account is already stored
		String fileName = name + "_" + emailParts[0] + "_" + emailParts[1]; 
		
		File f = new File(activity.getFilesDir(), fileName);
		
		if(f.exists()) {
		    try {
		        //use buffering
		        FileInputStream fis = activity.openFileInput(fileName);
				ObjectInputStream ois = new ObjectInputStream(fis);
				try {
					Account recoveredAccount = (Account) ois.readObject();
					currentAccount = recoveredAccount;
			        return recoveredAccount;
				}
		        finally {
		        	ois.close();
		        }
		      }
		    
		    catch(ClassNotFoundException ex){
		    	System.out.println("Cannot perform input. Class not found.");
		    	return createAndStoreAccount(fileName, GID, name);
		    }
		    catch(IOException ex){
		        System.out.println("Cannot perform input.");
		        return createAndStoreAccount(fileName, GID, name);
		    }
		    
		} else {
			return createAndStoreAccount(fileName, GID, name, activity);
		}
	}
	
	private static Account createAndStoreAccount(String fileName, String GID, String name) {
		Account newAccount = new Account(GID, name); 
		Account.currentAccount = newAccount;
		
	    try {
	        //use buffering
	        OutputStream file = new FileOutputStream( fileName );
	        OutputStream buffer = new BufferedOutputStream( file );
	        ObjectOutput output = new ObjectOutputStream( buffer );
	        try {
	        	output.writeObject(newAccount);
	        }
	        finally {
	        	output.close();
	        }
	      }  
	      catch(IOException ex){
	        System.out.println("Cannot perform output.");
	      }
		
		return newAccount;
	}
	
	/*updated createNewAccount helper which takes in the activity*/
	private static Account createAndStoreAccount(String fileName, String GID, String name, Context activity) {
		Account newAccount = new Account(GID, name); 
		Account.currentAccount = newAccount;
		
	    try {
	        //use buffering
	        FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(newAccount); 
	        try {
	        	oos.writeObject(newAccount);
	        }
	        finally {
	        	oos.close();
	        }
	      }  
	      catch(IOException ex){
	        System.out.println("Cannot perform output.");
	      }
		
		return newAccount;
	}
	
	////END CREATION COMMANDS////
	
	////OTHER COMMANDS////
	/*
	 * updateAccount() should be called by the UI every time the account screen is displayed
	 * goes through each Event in the 'events' Collection and adds up the balances from them
	 * 
	 * */
	
	public void updateAccount() {
		
		//update all totals
		totalYouOwe = 0.0;
		totalOwedToYou = 0.0;
		balance = 0.0;
		netBalance = 0.0;
		
		//totalYouOwe and totalOwedToYou
		for (Event e : events) {
			if (e.isParticipantByGID(GID)) {
				if (e.getParticipantByGID(GID).getBalance() > 0) {
					totalOwedToYou = totalOwedToYou + e.getParticipantByGID(GID).getBalance(); 
				} else {
					totalYouOwe = totalYouOwe + (-e.getParticipantByGID(GID).getBalance());
				}
			}
		}
		
		//netBalance/balance - the same in current implementation
		netBalance = totalOwedToYou - totalYouOwe;
		balance = totalOwedToYou - totalYouOwe;
		
		updatePastRelations();
	}
	
	/*
	 * ensure Account.currentAccount == account
	 */
	public static void setCurrentAccount(Account currentAccount) {
		Account.currentAccount = currentAccount;
	}
	
	/*
	 * ensure Account.name == name
	 */
	
	public void setName(String name) {
		
		String[] emailParts = GID.split("@");
		//Recover the original file name
		String fileName = this.name + "_" + emailParts[0] + "_" + emailParts[1];

		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		} 
		this.name = name;
		saveAccount();
	}
	
	public void setName(String name, Context activity) {
		
		String[] emailParts = GID.split("@");
		//Recover the original file name
		String fileName = this.name + "_" + emailParts[0] + "_" + emailParts[1];

		File f = new File(activity.getFilesDir(), fileName);
		if (f.exists()) {
			f.delete();
		} 
		this.name = name;
		saveAccount(activity);
	}

	/*
	 * ensure Account.GID == GID
	 * */
	
	public void setGID(String GID) {
		String[] emailParts = this.GID.split("@");
		//Recover the original file name
		String fileName = this.name + "_" + emailParts[0] + "_" + emailParts[1];

		File f = new File(fileName);
		if (f.exists()) {
			f.delete();
		} 
		this.GID = GID;
		saveAccount();
	}
	
	public void setGID(String GID, Context activity) {
		String[] emailParts = this.GID.split("@");
		//Recover the original file name
		String fileName = this.name + "_" + emailParts[0] + "_" + emailParts[1];

		File f = new File(activity.getFilesDir(), fileName);
		if (f.exists()) {
			f.delete();
		} 
		this.GID = GID;
		saveAccount(activity);
	}

	/*
	 * Store the changes into the file
	 * TODO Add to contracts
	 */
	
	public void saveAccount() {
		
		String[] emailParts = GID.split("@");
		//Recover the file name
		String fileName = name + "_" + emailParts[0] + "_" + emailParts[1];
		
	    try {
	        //use buffering
	        OutputStream file = new FileOutputStream( fileName );
	        OutputStream buffer = new BufferedOutputStream( file );
	        ObjectOutput output = new ObjectOutputStream( buffer );
	        
	        try {
	        	output.writeObject(this);
	        }
	        finally {
	        	output.close();
	        }
	      }
	    
	      catch(IOException ex){
	    	  System.out.println("Cannot perform output.");
	      }
	}
	
	public void saveAccount(Context activity) {
		
		String[] emailParts = GID.split("@");
		//Recover the file name
		String fileName = name + "_" + emailParts[0] + "_" + emailParts[1];
		
	    try {
	        //use buffering
			FileOutputStream fos = activity.openFileOutput(fileName, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos); 
	        
	        try {
	        	oos.writeObject(this);
	        }
	        finally {
	        	oos.close();
	        }
	      }
	    
	      catch(IOException ex){
	    	  System.out.println("Cannot perform output.");
	      }
	}
	
	////END OTHER COMMANDS////
	
	////TEST CODE////
	
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
		ArrayList<Participant> firstParts = (ArrayList<Participant>) firstEvent.getParticipants();
		
		ArrayList<Item> items = new ArrayList<Item>();
		items.add(new Item("Bread1", 10.0));
		items.add(new Item("Bread2", 20.0));
		items.add(new Item("Bread3", 30.0));
		items.add(new Item("Bread4", 40.0));
		
		//Add activities
		firstEvent.addBalanceChange(new Transaction("tmpname1", firstParts, items));
		firstEvent.addBalanceChange(new Transaction("tmpname2", firstParts, items));
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
		ArrayList<Participant> secondParts = (ArrayList<Participant>) secondEvent.getParticipants();
		
		//Add activities
		secondEvent.addBalanceChange(new Transaction("tmpname3", secondParts, items2));
		secondEvent.addBalanceChange(new Transaction("tmpname4", secondParts, items2));
		
		newAccount.updatePastRelations();
		
		return newAccount;
	}

	////END TEST CODE////
	
		
	/*Update Helper Methods*/
		
	Collection<Account> listParticipants() {
		updatePastRelations();
		return pastRelations;
	}
	
	private void updatePastRelations() {
		
		for (int i=0; i<events.size(); i++) {
			Collection<Participant> currentParticipants = events.get(i).getParticipants(); 
			for (Participant part : currentParticipants) {
				if (!pastRelations.contains(part) && part.getAccount() != null) {
					pastRelations.add(part.getAccount());
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

	//METHODS NOT CURRENTLY IN THE CONTRACT//
	
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
	
	public void setTotalYouOwe(double totalYouOwe) {
		this.totalYouOwe = totalYouOwe;
	}

	public void setTotalOwed(double totalOwedToYou) {
		this.totalOwedToYou = totalOwedToYou;
	}

	public void setNetBalance(double netBalance) {
		this.netBalance = netBalance;
	}



	//TODO Assume array list for now; Change later
	public void setEvents(AbstractList<Event> events) {
		this.events = (ArrayList<Event>) events;
	}
	
	public String getGID() {
		return GID;
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

}
