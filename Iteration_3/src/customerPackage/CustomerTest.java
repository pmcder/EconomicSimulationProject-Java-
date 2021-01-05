package customerPackage;

import static org.junit.Assert.*;

import org.junit.Test;

import inventoryPackage.Book;

public class CustomerTest {
	Book book = new Book();
	Customer<Book> bookCustomer = new Customer<>(book, 20.00);
	Customer<String> scienceCustomer = new Customer<>("science", 20.00);

	@Test
	public void testGetStringPreference() {
		assertEquals("Book class", bookCustomer.getStringPreference());
		assertEquals("science", scienceCustomer.getStringPreference());
	}

	@Test
	public void testSpendBudget() {
		assertTrue("spending 15.00 when budget is 20.00 is OK", bookCustomer.spendBudget(15.00));
		assertFalse("spending 55.00 when budget is 20.00 is not OK", bookCustomer.spendBudget(15.00));
	}

}
