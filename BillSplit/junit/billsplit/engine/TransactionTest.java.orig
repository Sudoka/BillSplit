/**
 * 
 */
package billsplit.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Danga
 *
 */
public class TransactionTest {

	Transaction transaction;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		/* moved here since addParticipant not available for v1.0 */
		ArrayList<Participant> list = new ArrayList<Participant>();
		Participant p1 = new Participant("person1");
		list.add(p1);
		Participant p2 = new Participant("person2");
		list.add(p2);
		Participant p3 = new Participant("person3");
		list.add(p3);
		Participant p4 = new Participant("person4");
		list.add(p4);
		this.transaction = new Transaction("testTrans", list);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.transaction = null;
	}

	
	@Test
	public void testConstructor() {
		Participant tp1 = new Participant("Anonymous");
		Participant tp2 = new Participant("Anonymous");
		ArrayList<Participant> list = new ArrayList<Participant>();
		list.add(tp1);
		list.add(tp2);
		
		Transaction newtrans = new Transaction("newtrans",list);
		
		ArrayList<Participant> internalList = (ArrayList<Participant>)newtrans.getParticipants();//dacashman quick cast-fix  
		assertEquals(2, internalList.size()); //check participants added
		assertEquals("newtrans",newtrans.getName()); //check name set correctly
	}
	
	@Test
	public void testGetSetItems() {
		Item item = new Item("testitem",25.00);
		this.transaction.addItem(item);
		ArrayList<Item> items = this.transaction.getItems();
		
		assertEquals(1, items.size()); //check only 1 item returned
		assertEquals(item,items.get(0)); //check same item as we put in
		
		Item item2 = new Item("item2",50.00);
		this.transaction.addItem(item2);
		items = this.transaction.getItems();
		assertEquals(2, items.size()); //check 2 items returned
		assertEquals(item2,items.get(1)); //check same item as we put in
	}
	
	/*
	@Test
	 adding and removign participants removed for v1.0
	public void testGetSetParticipants() {
		Participant p = new Participant("Anonymous");
		this.transaction.addParticipant(p);
		ArrayList<Participant> ps = this.transaction.getParticipants();
		
		assertEquals(1, ps.size());
		assertEquals(p,ps.get(0));
	} */
	
	@Test
	public void testDebtInitialValues() {
		setUpForDebtTest();
		//check that everyone has no debt to start
		ArrayList<Participant> plist = (ArrayList<Participant>)transaction.getParticipants();//dacashman quick cast-fix
		for (Participant p : transaction.getParticipants()) {
			double amt = transaction.debtGetTotalAmountParticipant(p);
			assertEquals("Nobody should have debt at start",0.0, amt, 0.0);
		}
	}
	
	@Test
	public void testDebtAllEvenly() {
		setUpForDebtTest();
		transaction.debtAllEvenly();
		//now check that everyone has the same, correct amount of debt
		// 25+10+9+0.5 = 44.5
		// 44.5 / 4 = 11.125
		for (Participant p : transaction.getParticipants()) {
			double amt = transaction.debtGetTotalAmountParticipant(p);
			assertEquals("Everyone should equally have $11.125 debt", 11.125, amt, 0.0);
		}
	}
	
	@Test
	public void testDebtItemEvenly() {
		setUpForDebtTest();
		Participant p1 = ((ArrayList<Participant>)transaction.getParticipants()).get(0); //dacashman quick cast-fix
		Participant p2 = ((ArrayList<Participant>)transaction.getParticipants()).get(1);//dacashman quick cast-fix
		ArrayList<Participant> ps = new ArrayList<Participant>();
		ps.add(p1);
		ps.add(p2);
		Item item = transaction.getItems().get(0);
		transaction.debtItemEvenly(item, ps);
		//now check that everyone has the same, correct amount of debt
		// 25 / 4 = 6.25
		double correctAmt = 25.00 / 2;
		
		double p1amt = transaction.debtGetItemAmountParticipant(p1, item);
		double p2amt = transaction.debtGetItemAmountParticipant(p2, item);
		
		assertEquals("person 1 should have $6.25 debt.", correctAmt, p1amt, 0.0);
		assertEquals("person 2 should have $6.25 debt.", correctAmt, p2amt, 0.0);
		
		Participant p3 = ((ArrayList<Participant>)transaction.getParticipants()).get(2); //dacashman quick cast-fix
		Participant p4 = ((ArrayList<Participant>)transaction.getParticipants()).get(3); //dacashman quick cast-fix
		double p3amt = transaction.debtGetItemAmountParticipant(p3, item);
		double p4amt = transaction.debtGetItemAmountParticipant(p4, item);
		assertEquals("person 3 should have $0 debt.", 0.0, p3amt, 0.0);
		assertEquals("person 4 should have $0 debt.", 0.0, p4amt, 0.0);
	}
	
	private void setUpForDebtTest() {
		Item item1 = new Item("item1",25.00);
		Item item2 = new Item("item2",10.00);
		Item item3 = new Item("item3",9.00);
		Item item4 = new Item("item4",0.50);
		
		this.transaction.addItem(item1);
		this.transaction.addItem(item2);
		this.transaction.addItem(item3);
		this.transaction.addItem(item4);
		
		
		
		/* addParticipant not for v1.0
		this.transaction.addParticipant(p1);
		this.transaction.addParticipant(p2);
		this.transaction.addParticipant(p3);
		this.transaction.addParticipant(p4); */
	}
	
	private void setUpForPayTest() {
		setUpForDebtTest();
		transaction.debtAllEvenly();
		transaction.payEvenly();
		Participant p1 = ((ArrayList<Participant>)transaction.getParticipants()).get(0);  //dacashman quick cast-fix
		Participant p2 = ((ArrayList<Participant>)transaction.getParticipants()).get(1);  //dacashman quick cast-fix
		Participant p3 = ((ArrayList<Participant>)transaction.getParticipants()).get(2);  //dacashman quick cast-fix
		Participant p4 = ((ArrayList<Participant>)transaction.getParticipants()).get(3);  //dacashman quick cast-fix
		Item item = transaction.getItems().get(0);
		transaction.debtResetItem(item);
		transaction.debtAddParticipant(item, p3); //now user 3 debted for entire item
	}
	
	@Test
	public void testPayFairly() {
		setUpForPayTest();
		Participant p1 = ((ArrayList<Participant>)transaction.getParticipants()).get(0);  //dacashman quick cast-fix
		Participant p2 = ((ArrayList<Participant>)transaction.getParticipants()).get(1);  //dacashman quick cast-fix
		Participant p3 = ((ArrayList<Participant>)transaction.getParticipants()).get(2);  //dacashman quick cast-fix
		Participant p4 = ((ArrayList<Participant>)transaction.getParticipants()).get(3);  //dacashman quick cast-fix
		
		transaction.payFairly();
		
		double p1amt = transaction.getAmount(p1);
		double p2amt = transaction.getAmount(p2);
		double p3amt = transaction.getAmount(p3);
		double p4amt = transaction.getAmount(p4);
		
		assertEquals(4.875,transaction.getCredit(p1),0.0);
		assertEquals(4.875,transaction.getCredit(p2),0.0);
		assertEquals(29.875,transaction.getCredit(p3),0.0);
		assertEquals(4.875,transaction.getCredit(p4),0.0);
		
		assertEquals(0, p1amt, 0.0);
		assertEquals(0, p2amt, 0.0);
		assertEquals(0, p3amt, 0.0);
		assertEquals(0, p4amt, 0.0);
	}
	
	@Test
	public void testPayEvenly() {
		setUpForDebtTest();
		
		transaction.debtAllEvenly();
		//debt is $11.125/person
		for (Participant p : transaction.getParticipants()) assertEquals(11.125,transaction.debtGetTotalAmountParticipant(p),0.0);
		
		transaction.payEvenly();
		//payment is $11.125/person
		for (Participant p : transaction.getParticipants()) assertEquals(11.125,transaction.getCredit(p),0.0);
		
		HashMap<Participant,Double> amts = transaction.getAmounts();
		//make sure all payment amounts are 0 (debt is even, payment is even, so net should be 0)
		for (Participant p : amts.keySet()) {
			double amt = amts.get(p);
			assertEquals(0.0,amt,0.0);
		}
		
		// now test if debt is uneven. Same even payment, but now one user pays entirely for a $9 item
		Participant p1 = ((ArrayList<Participant>)transaction.getParticipants()).get(0);  //dacashman quick cast-fix
		Participant p2 = ((ArrayList<Participant>)transaction.getParticipants()).get(1);  //dacashman quick cast-fix
		Participant p3 = ((ArrayList<Participant>)transaction.getParticipants()).get(2);  //dacashman quick cast-fix
		Participant p4 = ((ArrayList<Participant>)transaction.getParticipants()).get(3);  //dacashman quick cast-fix
		
		
		Item item = transaction.getItems().get(0);
		transaction.debtResetItem(item);
		transaction.debtAddParticipant(item, p3); //now user 3 debted for entire item
		
		double p1debt = transaction.debtGetTotalAmountParticipant(p1);
		double p2debt = transaction.debtGetTotalAmountParticipant(p2);
		double p3debt = transaction.debtGetTotalAmountParticipant(p3);
		double p4debt = transaction.debtGetTotalAmountParticipant(p4);
		
		assertEquals(4.875, p1debt,0.0);
		assertEquals(4.875, p2debt,0.0);
		assertEquals(29.875, p3debt,0.0);
		assertEquals(4.875, p4debt,0.0);
		
		double p1amt = transaction.getAmount(p1);
		double p2amt = transaction.getAmount(p2);
		double p3amt = transaction.getAmount(p3);
		double p4amt = transaction.getAmount(p4);
		
		assertEquals(6.25, p1amt, 0.0);
		assertEquals(6.25, p2amt, 0.0);
		assertEquals(-18.75, p3amt, 0.0);
		assertEquals(6.25, p4amt, 0.0);
		
		// make sure pay amt doesnt change running it again, just for fun.
		transaction.payEvenly();
		//payment is $11.125/person
		for (Participant p : transaction.getParticipants()) assertEquals(11.125,transaction.getCredit(p),0.0);
	}

}
