package inventoryPackage;

import static org.junit.Assert.*;

import org.junit.Test;

public class EquipmentTest {
	Equipment testEquipment = new Equipment("Equipment", 2, 1, 59.99, "history");


	@Test
	public void testReStockWarning() {
		String message = "\n\tRestocking Warning:\n\tItem name: Equipment"
				+ "\n\tItem id: 1"
				+ "\n\tRestocking quantity: 5\n";
		assertEquals(message, testEquipment.reStockWarning());
	}


	@Test (expected = Exception.class)
	public void testSetMinAllowable() {
		testEquipment.setMinAllowable(-10);
	}

}
