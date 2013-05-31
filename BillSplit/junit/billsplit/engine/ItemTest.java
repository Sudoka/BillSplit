/**
 * 
 */
package billsplit.engine;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Danga
 *
 */
public class ItemTest {
	
	Item item;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.item = new Item("testItem",50.0);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		this.item = null;
	}

	/**
	 * Test method for {@link billsplit.engine.Item#getCost()}.
	 */
	@Test
	public final void testGetCost() {
		assertEquals(50.0,item.getCost(),10);
	}

	/**
	 * Test method for {@link billsplit.engine.Item#setCost(double)}.
	 */
	@Test
	public final void testSetCost() {
		item.setCost(100.0);
		assertEquals(100.0,item.getCost(),10);
	}

	/**
	 * Test method for {@link billsplit.engine.Item#getName()}.
	 */
	@Test
	public final void testGetName() {
		assertEquals("testItem",item.getName());
	}

	/**
	 * Test method for {@link billsplit.engine.Item#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName() {
		item.setName("newname");
		assertEquals("newname",item.getName());
	}

	/**
	 * Test method for {@link billsplit.engine.Item#getCategory()}.
	 */
	@Test
	public final void testGetCategory() {
		assertEquals("",item.getCategory());
	}

	/**
	 * Test method for {@link billsplit.engine.Item#setCategory(java.lang.String)}.
	 */
	@Test
	public final void testSetCategory() {
		item.setCategory("testCategory");
		assertEquals("testCategory",item.getCategory());
	}

	/**
	 * Test method for {@link billsplit.engine.Item#getDetails()}.
	 */
	@Test
	public final void testGetDetails() {
		assertEquals("",item.getDetails());
	}

	/**
	 * Test method for {@link billsplit.engine.Item#setDetails(java.lang.String)}.
	 */
	@Test
	public final void testSetDetails() {
		item.setDetails("testDetails");
		assertEquals("testDetails",item.getDetails());
	}

}
