/**
 * 
 */
package billsplit.engine;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		this.transaction = new Transaction("testTrans", new ArrayList<Participant>());
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
		Participant tp1 = new Participant();
		Participant tp2 = new Participant();
		ArrayList<Participant> list = new ArrayList<Participant>();
		list.add(tp1);
		list.add(tp2);
		
		Transaction newtrans = new Transaction("newtrans",list);
		
		ArrayList<Participant> internalList = newtrans.getParticipants();
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
	
	@Test
	public void testGetSetParticipants() {
		Participant p = new Participant();
		this.transaction.addParticipant(p);
		ArrayList<Participant> ps = this.transaction.getParticipants();
		
		assertEquals(1, ps.size());
		assertEquals(p,ps.get(0));
	}
	
	@Test
	public void testDebtInitialValues() {
		setUpForDebtTest();
		//check that everyone has no debt to start
		ArrayList<Participant> plist = transaction.getParticipants();
		System.out.println(plist);
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
	
	private void setUpForDebtTest() {
		Item item1 = new Item("item1",25.00);
		Item item2 = new Item("item2",10.00);
		Item item3 = new Item("item3",9.00);
		Item item4 = new Item("item4",0.50);
		
		this.transaction.addItem(item1);
		this.transaction.addItem(item2);
		this.transaction.addItem(item3);
		this.transaction.addItem(item4);
		
		Participant p1 = new Participant("person1");
		Participant p2 = new Participant("person2");
		Participant p3 = new Participant("person3");
		Participant p4 = new Participant("person4");
		
		this.transaction.addParticipant(p1);
		this.transaction.addParticipant(p2);
		this.transaction.addParticipant(p3);
		this.transaction.addParticipant(p4);
	}

}
