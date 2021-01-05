package simulationPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import customerPackage.Customer;
import inventoryPackage.Shop;

public class SimulationTest {
	Simulation sim = new Simulation();

	@Test
	public void testDisplayStats() {
		Shop shop = new Shop();
		shop.incrementSales(850.00);
		shop.incrementCosts(250.00);
		
		String message = String.format("\nTOTAL SALES for SHOP %d in the last 30 days: $%.2f"
							+ "\nTOTAL PROFITS for SHOP %d in the last 30 days: " 
							+ "(total sales - items cost - fixed cost): $%.2f", sim.getShopNumber(), shop.getSales(), 
							sim.getShopNumber(), (shop.getSales() - shop.getCosts() - shop.getFixedCost()));
		assertEquals(message, sim.displayStats(shop));
	}


	@Test
	public void testPopulateRandomCustomers() {
		ArrayList<Customer> customerOptions = new ArrayList<>();
		sim.populateRandomCustomers(customerOptions);
		assertEquals("math", customerOptions.get(0).getStringPreference());
		assertEquals("science", customerOptions.get(1).getStringPreference());
		assertEquals("language arts", customerOptions.get(2).getStringPreference());
		assertEquals("history", customerOptions.get(3).getStringPreference());
		assertEquals("Book class", customerOptions.get(4).getStringPreference());
		assertEquals("Supplies class", customerOptions.get(5).getStringPreference());
		
	}
}
