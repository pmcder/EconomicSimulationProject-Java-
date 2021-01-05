package inventoryPackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class SuppliesTest {
	Supplies supplies = new Supplies("Supplies", 2, 1, 5.99);

	@Test
	public void testReStockWarning() {
		String message = "\n\tRestocking Warning:\n\tItem name: Supplies"
				+ "\n\tItem id: 1"
				+ "\n\tRestocking quantity: 25\n";
		assertEquals(message, supplies.reStockWarning());
	}

	@Test (expected = Exception.class)
	public void testSetMinAllowable() {
		supplies.setMinAllowable(-3);
	}

}
