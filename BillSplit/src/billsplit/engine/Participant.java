package billsplit.engine;

import java.io.Serializable;

public class Participant implements Serializable {

	private static final long serialVersionUID = 1305975903913062148L;
	private Account associatedAccount;
	private double balance; //always initialized to 0
	private double spent;
	private String name;
	
	/* deprecated
	public Participant(){
		associatedAccount = null;
		balance = 0;
		name = "Anon";
	} */
	
	/*
	 * This version is for quick 'n' dirty participant with 
	 * no associated account. 
	 */
	public Participant(String name){
		associatedAccount = null;
		balance = 0;
		spent = 0;
		this.name = name;
	}
	
	public Participant(Account associatedAccount){
		this.associatedAccount =associatedAccount;
		balance = 0;
		spent = 0;
		/* dacashman - make sure this account API exists
		 * and verify that this is the desired functionality.
		 * Same for other constructors.
		 */
		name = associatedAccount.getName();
	}
	
	
	/*
	 * This version is called if user wants to define his/her
	 * own "nickname" to user that isn't the account name
	 */
	public Participant(Account associatedAccount, String name){
		this.associatedAccount = associatedAccount;
		this.name = name;
		balance = 0;
		spent = 0;
	}

	
	public Account getAccount(){
		return associatedAccount;
	}
	
	
	public String getName(){
		return name;
	}
	
	
	
	public double getBalance(){
		return balance;
	}
	
	
	public String toString() {
		String str = String.format("Participant(name=%s)",getName());
		return str;
	}

	
	public void setAccount(Account associatedAccount){
		/* dacashman - should we check to see if an account is
		 * already associated with the participant?
		 */
		this.associatedAccount = associatedAccount;
	}
	
	
	public void setName(String name){
		this.name = name;
	}

	
	/*
	 * Adds the indicated amount (positive or negative) to the 
	 * participant balance and returns the new result.
	 */
	public double addToBalance(double addAmount){
		balance +=addAmount;
		return balance;
	}
	
	/*
	 * Adds the indicated amount (positive or negative) to the 
	 * participant spent field and returns the new result.
	 */
	public double addToSpent(double addAmount){
		spent +=addAmount;
		return spent;
	}
	
}
