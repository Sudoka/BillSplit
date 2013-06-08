package billsplit.engine;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParticipantTest {
	
	private Account testOwner;
	private Participant ptpt1;
	
	@Before
	public void setUp() throws Exception {
		testOwner = new Account("owner@gmail.com", "Owner");
		ptpt1 = new Participant(testOwner);
	}

	@After
	public void tearDown() throws Exception {
	}

	/*
	 * I am not testing constructors due to default true-ness.  Should I? 
	 */  

	@Test
	public void testToString() {
		if(!(ptpt1.toString().equals("Owner"))){
			fail("toString failed to return participant name");
		}
	}

	@Test
	public void testGetAccount() {
		if(ptpt1.getAccount() != testOwner){
			fail("Get Account did not return the account");
		}
		
	}

	@Test
	public void testSetAccount() {
		Account tempAccount = ptpt1.getAccount();
		Account newAccount = new Account("Bogus@gmail.com", "Bogus");
		ptpt1.setAccount(newAccount);
		if(ptpt1.getAccount() == tempAccount || ptpt1.getAccount() != newAccount){
			fail("Failed to set new account");
		}
		ptpt1.setAccount(tempAccount);
	}

	@Test
	public void testGetName() {
		if(!(ptpt1.getName().equals("Owner"))){
			fail("Get Name failed");
		}
	}

	@Test
	public void testSetName() {
		String tempString = ptpt1.getName();
		String newString = new String("Bogus");
		ptpt1.setName(newString);
		if(ptpt1.getName() == tempString || ptpt1.getName() != newString){
			fail("Failed to set new Name");
		}
		ptpt1.setName(tempString);

	}

	@Test
	public void testGetBalance() {
		// balance starts at 0
		if(ptpt1.getBalance() != 0){
			fail("Get Balance failed to return balance of 0");
		}
		ptpt1.addBalance(15.00);
		if(ptpt1.getBalance() != 15){
			fail("Get Balance failed to return balance of 15");
		}
		ptpt1.addBalance(-15.00);
		if(ptpt1.getBalance() != 0){
			fail("Get Balance failed to return balance of 0");
		}
		
	}

	@Test
	public void testAddBalance() {
		ptpt1.addBalance(15.00);
		if(ptpt1.getBalance() != 15){
			fail("Add Balance failed to return balance of 15");
		}
		ptpt1.addBalance(-15.00);
		if(ptpt1.getBalance() != 0){
			fail("Add Balance failed to return balance of 0");
		}
		
	}

}
