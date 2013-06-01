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
		System.out.println(internalList);
		assertEquals(2, internalList.size());
		
	}
	
	@Test
	public void testGetSetItems() {
		Item item = new Item("testitem",25.00);
		this.transaction.addItem(item);
		ArrayList<Item> items = this.transaction.getItems();
		
		assertEquals(1, items.size());
		
		assertEquals(item,items.get(0));
	}
	
	@Test
	public void testGetSetParticipants() {
		Participant p = new Participant();
		this.transaction.addParticipant(p);
		ArrayList<Participant> ps = this.transaction.getParticipants();
		
		assertEquals(1, ps.size());
		
		assertEquals(p,ps.get(0));
	}

}
