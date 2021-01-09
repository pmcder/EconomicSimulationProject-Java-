package inventoryPackage;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class ShopTest {
	Shop shop = new Shop();
	HashMap<Integer, Item> shopInventory = shop.getInventory();
	
	
	@Before
	public void createShop() {
		
		shopInventory.put(1, new Book("Math 1", 10, 1, 12.50, "1", "math"));
		shopInventory.put(2, new Book("Math 2", 10, 2, 12.50, "2", "math"));
		shopInventory.put(3, new Book("Math 3", 10, 3, 12.50, "3", "math"));
		shopInventory.put(4, new Book("Math 4", 10, 4, 12.50, "4", "math"));
		shopInventory.put(5, new Book("Math 5", 10, 5, 12.50, "5", "math"));
		
		shopInventory.put(6, new Equipment("Microscope X", 7, 6, 120.99, "science"));
		shopInventory.put(7, new Equipment("Math Manipulatives", 7, 7, 145.99, "math"));
		shopInventory.put(8, new Equipment("Biology Experiments Kit", 7, 8, 115.99, "science"));
		shopInventory.put(9, new Equipment("Chemistry Experiments Kit", 7, 9, 125.99, "science"));
		shopInventory.put(10, new Equipment("Physics Experiments Kit", 7, 10, 119.99, "science"));
		
		shopInventory.put(11, new Supplies("Smiley Stickers", 20, 12, 0.50));
		shopInventory.put(12, new Supplies("Motivational Stickers", 20, 13, 0.60));
		shopInventory.put(13, new Supplies("Markers, set of 10", 20, 12, 6.99));
		shopInventory.put(14, new Supplies("Multiplication Table", 20, 12, 2.50));
		shopInventory.put(15, new Supplies("Ruler", 20, 12, 0.25));
	}


	@Test (expected = NegativeSalesException.class)
	public void testIncrementSales() {
		shop.incrementSales(-10.00);
	}

	@Test (expected = Exception.class)
	public void testIncrementCosts() {
		shop.incrementCosts(-10.00);
	}

	@Test
	public void testSelectCategory() {
		shop.selectCategory("science");
		assertFalse(shop.getSelectedItems().isEmpty());//selection is not empty
		
		for (Item item : shop.getSelectedItems()) {
			//check each item in the selection and assert that is of category science
			if (item instanceof Book)
				assertEquals("science", ((Book) item).getCategory());
			else if (item instanceof Equipment)
				assertEquals("science", ((Equipment) item).getCategory());
		}
	}

	@Test
	public void testSelectGrade() {
		shop.selectGrade("3");
		assertFalse(shop.getSelectedItems().isEmpty());
		
		for (Item item : shop.getSelectedItems()) {
			//check each item in the selection and assert that is of given grade
			if (item instanceof Book)
				assertEquals("3", ((Book) item).getGradeLevel());
		}
	}

	@Test
	public void testSelectBook() {
		shop.selectBook();
		assertFalse(shop.getSelectedItems().isEmpty());
		
		for (Item item: shop.getSelectedItems()) {
			//check each item in the selection and assert that is of class Book
			assertEquals("inventoryPackage.Book", item.getClass().getName());
		}
	}

	@Test
	public void testSelectSupplies() {
		shop.selectSupplies();
		assertFalse(shop.getSelectedItems().isEmpty());
		
		for (Item item: shop.getSelectedItems()) {
			//check each item in the selection and assert that is of class Supplies
			assertEquals("inventoryPackage.Supplies", item.getClass().getName());
		}
	}
}
