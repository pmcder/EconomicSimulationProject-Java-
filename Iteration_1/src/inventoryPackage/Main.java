/**MET CS 622
 * Assignment 1
 * class Main
 * Driver Program
 * by Iryna Chervachidze
 * May 19, 2020
 */

package inventoryPackage;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
		/*
		 * Postcondition 1: create a HashMap inventory and populate with items of
		 * 		classes Book, Equipment, and Supplies
		 * Postcondition 2: demonstrate polymorphism using toString() method
		 * Postcondition 3: calculate total cost of items in the inventory,
		 * 		projected revenues, and profits
		 */
		
		// Post 1: create a new empty inventory, integer is item id
		HashMap<Integer, Item> shopInventory = new HashMap<>();
		
		//Post 1: populate inventory
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
		
		//Post 2: Polymorphism
		System.out.println("Example of polymorphism (calling toString() methods of classes Book, Equipment, and Supplies):");
		System.out.print(shopInventory.get(1).toString() + "\n");
		System.out.print(shopInventory.get(6).toString() + "\n");
		System.out.print(shopInventory.get(11).toString() + "\n");
		
		
		//Post 3: Costs, revenues, and profits
		System.out.print("\nTotal number of items: ");
		int totalCount = 0;
		for (Item item : shopInventory.values())
			totalCount += item.getQuantity();
		System.out.println(totalCount);
		
		
		System.out.print("\nTotal cost of items: ");
		double totalCost = 0;
		for (Item item : shopInventory.values())
			totalCost += item.getCost()*item.getQuantity();
		System.out.println("$" + totalCost);
			
		
		System.out.print("Total projected sales revenue: ");
		double totalRevenue = 0;
		for (Item item : shopInventory.values())
			totalRevenue += item.getPrice()*item.getQuantity();
		System.out.printf("$%.2f%n", totalRevenue);
		
		
		System.out.print("Total projected sales profit: ");
		System.out.printf("$%.2f%n", (totalRevenue - totalCost));
		System.out.print("Average projected sales profit per item: ");
		System.out.printf("$%.2f",(totalRevenue - totalCost)/totalCount);
		
	}
}
