package billsplit.engine;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EventTest {
	
	private Account testOwner;
	private Event testEvent;
	
	private ArrayList<Participant> ptpts;
	private Participant ptpt1;
	private Participant ptpt2;
	private Participant ptpt3;
	private Participant ptpt4;
	
	private Transaction testTxn;
	private BalanceChange testPayment;
	
	
	private Item item1;
	private Item item2;
	private Item item3;
		
	
	@Before
	public void setUp() throws Exception {
		//create test event for account owner
		this.testOwner = new Account("owner@gmail.com", "Owner");
		this.testEvent = new Event(testOwner.getGID(), "testEventName");

		//add event participants
		ptpts = new ArrayList<Participant>();
		this.ptpt1 = new Participant(this.testOwner);
		ptpts.add(ptpt1);
		testEvent.addParticipant(ptpt1);
		this.ptpt2 = new Participant("Part2");
		ptpts.add(ptpt2);
		testEvent.addParticipant(ptpt2);
		this.ptpt3 = new Participant("Part3");
		ptpts.add(ptpt3);
		testEvent.addParticipant(ptpt3);
		this.ptpt4 = new Participant("Part4");
		ptpts.add(ptpt4);
		testEvent.addParticipant(ptpt4);
		
		/*
		 * put participants in transaction, add items, and add to event
		 */
		this.testTxn = new Transaction("testTxn", ptpts);
		item1 = new Item("Item1", 30.00);
		this.testTxn.addItem(item1);
		this.testTxn.debtAddParticipant(item1, ptpt1); //ptpt1 pays for first item
		
		item2 = new Item("Item2", 18.00);
		this.testTxn.addItem(item2);
		this.testTxn.debtItemEvenly(item2, ptpts); //all split item2
		
		item3 = new Item("Item3", 22.00);
		this.testTxn.addItem(item3);
		// custom split of item3 between ptpts 2-4 (must equal exact item cost)
		HashMap<Participant, Double> customCosts = new HashMap<Participant, Double>();
		customCosts.put(ptpt2, (Double) 12.00);
		customCosts.put(ptpt3, (Double) 4.00);
		customCosts.put(ptpt4, (Double) 6.00);
		Set<Map.Entry<Participant, Double>> es = customCosts.entrySet();
		for(Map.Entry<Participant, Double> e : es){
			this.testTxn.debtSet(item3, e.getKey(), e.getValue());
		}
		

		/*
		 * finally, pay for this txn and update amounts
  		 * total amount = 70, so each paying 70/4 = 17.50
  		 * ppt1 pays 17.50, owes 30 + 4.50
  		 * ppt2 pays 17.50, owes 4.50 + 12
  		 * ppt3 pays 17.50, owes 4.50 + 4
  		 * ppt4 pays 17.50, owes 4.50 + 6  
		 */
		this.testTxn.payEvenly();
		
		//payment must sum to zero: ptpt1 and 3 are paying 2 and 4
		HashMap<Participant, Double> paymentMap = new HashMap<Participant, Double>();
		paymentMap.put(ptpt1, 40.00);
		paymentMap.put(ptpt2, -15.00);
		paymentMap.put(ptpt3, 10.00);
		paymentMap.put(ptpt4, -35.00);
		
		this.testPayment = new BalanceChange("testPayment", this.ptpts);
		
		es = paymentMap.entrySet();
		for(Map.Entry<Participant, Double> e : es){
			this.testPayment.setCredit(e.getKey(), e.getValue());
		}
		
		
		
		this.testEvent.addBalanceChange(testTxn);
		this.testEvent.addBalanceChange(testPayment);

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetName() {
		if(!testEvent.getName().equals("testEventName")){
			fail("testGetName failure");
		}
	}

	@Test
	public void testGetCreatorGID() {
		if(!testEvent.getCreatorGID().equals("owner@gmail.com")){
			fail("testGetCreatorGID failure");
		}
	}

	@Test
	public void testGetParticipants() {
		/* due to nature of collection, need to verify that each ptpt is
		 * in the returned collection, but only once. 
		 */
		boolean ptpt1Check = false;
		boolean ptpt2Check = false;
		boolean ptpt3Check = false;
		boolean ptpt4Check = false;
		
		Collection<Participant> testPtpts = testEvent.getParticipants();
		for(Participant p : testPtpts){
			if(p == ptpt1 && ptpt1Check == false){
				ptpt1Check = true;
			}else if(p == ptpt1){
				fail("testGetParticipants contained ptpt1 twice");
			}
			if(p == ptpt2 && ptpt2Check == false){
				ptpt2Check = true;
			}else if(p == ptpt2){
				fail("testGetParticipants contained ptpt2 twice");
			}
			if(p == ptpt3 && ptpt3Check == false){
				ptpt3Check = true;
			}else if(p == ptpt3){
				fail("testGetParticipants contained ptpt3 twice");
			}
			if(p == ptpt4 && ptpt4Check == false){
				ptpt4Check = true;
			}else if(p == ptpt4){
				fail("testGetParticipants contained ptpt4 twice");
			}
		}
		if(!(ptpt1Check && ptpt2Check && ptpt3Check && ptpt4Check)){
			fail("testGetParticipants missing at least one participant");
		}
	}

	@Test
	public void testIsParticipant(){
		if(!this.testEvent.isParticipant("Owner")){
			fail("Owner not a participant");
		}
		if(!this.testEvent.isParticipant("Part2")){
			fail("Part2 not a participant");
		}
		if(!this.testEvent.isParticipant("Part3")){
			fail("Part3 not a participant");
		}
		if(!this.testEvent.isParticipant("Part4")){
			fail("Part4 not a participant");
		}
		if(this.testEvent.isParticipant("Bogus")){
			fail("Bogus participant recognized");
		}
	}

	@Test
	public void testIsParticipantByGID() {
		if(!this.testEvent.isParticipantByGID(this.testOwner.getName())){
			fail("Owner not a participant");
		}
		if(this.testEvent.isParticipantByGID("BogusGID")){
			fail("BogusGID incorrectly identified as a participant");
		}
	}

	@Test
	public void testGetBalanceChanges() {
		
		/* due to nature of collection, need to verify that each balanceChange is
		 * in the returned collection, but only once. 
		 */
		boolean testTxnCheck = false;
		boolean testPaymentCheck = false;
		
		Collection<BalanceChange> balanceChanges = testEvent.getBalanceChanges();
		for(BalanceChange b : balanceChanges){
			if(b == testTxn && testTxnCheck == false){
				testTxnCheck = true;
			}else if(b == testTxn){
				fail("testGetBalanceChanges contained testTxn twice");
			}
			if(b == testPayment && testPaymentCheck == false){
				testPaymentCheck = true;
			}else if(b == testPayment){
				fail("testGetBalanceChanges contained testPayment twice");
			}
		}
		if(!(testTxnCheck && testPaymentCheck)){
			fail("testGetBalanceChanges missing at least one BalanceChange");
		}

	}

	@Test
	public void testAddBalanceChange() {
		boolean txnResult = false;
		boolean paymentResult = false;
		
		Transaction testTxn = new Transaction("addTxnTest", testEvent.getParticipants());
		testEvent.addBalanceChange(testTxn);
		BalanceChange testPayment = new BalanceChange("addPaymentTest", testEvent.getParticipants());
		testEvent.addBalanceChange(testPayment);
		
		Collection<BalanceChange> balanceChanges = testEvent.getBalanceChanges();
		for(BalanceChange b : balanceChanges){
			if(b == testTxn){
				txnResult = true;
			}
			if(b == testPayment){
				paymentResult = true;
			}
		}
		if(!(txnResult && paymentResult)){
			fail("testAddBalanceChange did not contain both added BalanceChanges");
		}
		testEvent.removeBalanceChange(testTxn);
		testEvent.removeBalanceChange(testPayment);
	}
	
	
	@Test
	public void testRemoveBalanceChange() {
		boolean txnResult = false;
		boolean paymentResult = false;
		
		Transaction testTxn = new Transaction("addTxnTest", testEvent.getParticipants());
		testEvent.addBalanceChange(testTxn);
		BalanceChange testPayment = new BalanceChange("addPaymentTest", testEvent.getParticipants());
		testEvent.addBalanceChange(testPayment);
		
		testEvent.removeBalanceChange(testTxn);
		testEvent.removeBalanceChange(testPayment);
		
		Collection<BalanceChange> balanceChanges = testEvent.getBalanceChanges();
		for(BalanceChange b : balanceChanges){
			if(b == testTxn){
				txnResult = true;
			}
			if(b == testPayment){
				paymentResult = true;
			}
		}
		if(txnResult || paymentResult){
			fail("testRemoveBalanceChange did contain one of added BalanceChanges");
		}
	}

	@Test
	public void testGetBalanceChangesByDate() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetParticipantByName() {
		//test actual participant
		Participant ptpt = testEvent.getParticipantByName("Owner");
		if(ptpt == null){
			fail("Did not successfully retrieve Owner");
		}
		//test non-participant
		ptpt = testEvent.getParticipantByName("Bloopie");
		if(!(ptpt == null)){
			fail("Incorrectly recognized non-existant participant");
		}
	}

	@Test
	public void testGetParticipantByGID() {
		//test actual participant
		Participant ptpt = testEvent.getParticipantByGID("Owner");
		if(ptpt == null){
			fail("Did not successfully retrieve Owner");
		}
		//test non-participant
		ptpt = testEvent.getParticipantByName("Bloopie");
		if(!(ptpt == null)){
			fail("Incorrectly recognized non-existant participant");
		}
		
	}

	@Test
	public void testAddParticipant() {
		boolean ptptResult = false;
		Participant testPtpt = new Participant("AddedPtpt");
		testEvent.addParticipant(testPtpt);
		
		Collection<Participant> participants = testEvent.getParticipants();
		for(Participant p : participants){
			if(p == testPtpt){
				ptptResult = true;
			}
		}
		if(!ptptResult){
			fail("testRemoveParticipant did contain one of added Ptpts");
		}
		testEvent.removeParticipant(testPtpt);
	}

	@Test
	public void testRemoveParticipant() {
		boolean ptptResult = false;
		
		Participant testPtpt = new Participant("AddedPtpt");
		testEvent.addParticipant(testPtpt);
		testEvent.removeParticipant(testPtpt);
		
		Collection<Participant> participants = testEvent.getParticipants();
		for(Participant p : participants){
			if(p == testPtpt){
				ptptResult = true;
			}
		}
		if(ptptResult){
			fail("testRemoveParticipant did contain one of added Ptpts");
		}
	}

	@Test
	public void testSetName() {
		testEvent.setName("Bogus");
		if(!(testEvent.getName().equals("Bogus"))){
			fail("Set Name failed");
		}
	}

	@Test
	public void testUpdateBalances() {
		/*
		 * Measure the balances as a result of the set-up.
		 */
		this.testEvent.updateBalances();
		if(ptpt1.getBalance() != 23.00){
			fail("Update Balance failed");
		}
		if(ptpt2.getBalance() != -14.00){
			fail("Update Balance failed");
		}
		if(ptpt2.getBalance() != 19.00){
			fail("Update Balance failed");
		}
		if(ptpt4.getBalance() != -28.00){
			fail("Update Balance failed");
		}
		
	}

}
