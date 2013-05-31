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
