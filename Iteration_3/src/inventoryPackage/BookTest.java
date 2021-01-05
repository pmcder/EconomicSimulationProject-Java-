package inventoryPackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class BookTest {
	Book newBook = new Book("My Book", 2, 1, 5.99, "3", "history");

	@Test
	public void testReStockWarning() {
		String message = "\n\tRestocking Warning:\n\tItem name: My Book"
				+ "\n\tItem id: 1"
				+ "\n\tRestocking quantity: 12\n";
		assertEquals(message, newBook.reStockWarning());
	}
	

	@Test (expected = Exception.class)
	public void testSetMinAllowable() {
		newBook.setMinAllowable(-2);
		
	}

	@Test (expected = Exception.class)
	public void testSetGradeLevel() {
		newBook.getAllowableGrades();
		newBook.setGradeLevel("U");
	}

}
