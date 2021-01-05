/**MET CS 622
 * Assignment 2
 * class Main
 * by Iryna Chervachidze
 * May 23, 2020
 */

package inventoryPackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 * This class is for testing purposes only
 *
 */
public class Main {

	public static void main(String[] args) {
		/*
		 * Precondition 1: Book items are populated from "book.txt" source file
		 * Precond 2: Equipment items are populated from "equipment.txt" source file
		 * Precond 3: Supplies items are populated from "supplies.txt" source file
		 * 
		 * Postcondition 1: Create a shop 
		 * Post 2: Populate shop's inventory with items from source files
		 * Post 2: Display the contents of the inventory
		 * Post 3: Use selection method selectCategory() from Shop class and 
		 * 		select only items from selected category. Display them.
		 */
		
		//Precondition 1, 2, 3: source files for inventory
		File bookSource = new File("./src/resources/book.txt");
		File equipmentSource = new File("./src/resources/equipment.txt");
		File suppliesSource = new File("./src/resources/supplies.txt");
		
		//Post 1: create a shop
		Shop shop = new Shop();
		HashMap<Integer, Item> shopInventory = shop.getInventory();
		int idNumber = 1;// first items has id number = 1
		
		//Post 2: Inventory contents
		//Print header
		System.out.println("Inventory Contents");
		System.out.println("=====================");
		
		//Populate inventory from the source files
		//Book items
		System.out.println("\nBooks table:"); // print header
		System.out.println("=====================\n");
		try {
			populateBookItems(shopInventory, bookSource, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		//Equipment items
		System.out.println("\nEquipment table:"); //print header
		System.out.println("=====================\n");
		idNumber = shopInventory.size() + 1;
		try {
			populateEquipmentItems(shopInventory, equipmentSource, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
				
		//Supplies items
		System.out.println("\nSupplies table:");
		System.out.println("=====================\n");
		idNumber = shopInventory.size() + 1;
		try {
			populateSuppliesItems(shopInventory, suppliesSource, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		
		//Post 3: select only items from selected category (i.e. "math")
		String category = "math";
		System.out.println("\nItems selected from " + category + " category:");
		System.out.println("=====================================");
		shop.selectCategory(category);
		System.out.println("\nNumber of " + category + " items: " + shop.getSelectedItems().size());
		for (Item item: shop.getSelectedItems()) {
			System.out.println(item);
		}
	}

	/**Populates shop's inventory with Book items from source file
	 * 
	 * @param shopInventory: HashMap<Integer, Item> that contains inventory items
	 * @param bookSource: .txt source file for Book items
	 * @param idNumber: integer id number for the first Book item
	 * @throws FileNotFoundException:
	 */
	public static void populateBookItems(HashMap<Integer, Item> shopInventory,
			File bookSource, int idNumber) throws FileNotFoundException {
		
		Scanner bookFile = new Scanner(bookSource);
		String readline = bookFile.nextLine(); //read the first line, do not need it
		//System.out.println(readline);
		
		while (bookFile.hasNext()) {
			readline = bookFile.nextLine(); //read next line
			Scanner tokens = new Scanner(readline).useDelimiter(",\\s*");
			
			//read all fields (name, quantity, cost, gradeLevel, category)
			String name = tokens.next();
			int quantity = tokens.nextInt();
			double cost = tokens.nextDouble();
			String gradeLevel = tokens.next();
			String category = tokens.next();
			
			//create and add a new instance of Book to the inventory
			shopInventory.put(idNumber, (new Book(name, quantity, idNumber,
							cost, gradeLevel, category)));
			System.out.print(shopInventory.get(idNumber).getIdNumber() + ": ");			
			System.out.print(shopInventory.get(idNumber).getName() + ", ");
			
			System.out.print(shopInventory.get(idNumber).getQuantity() + ", ");
			System.out.print(shopInventory.get(idNumber).getCost() + ", ");
			if (shopInventory.get(idNumber) instanceof Book) {
				System.out.print(((Book) shopInventory.get(idNumber)).getGradeLevel() + ", ");
				System.out.println(((Book) shopInventory.get(idNumber)).getCategory());
			idNumber++;
			}
			tokens.close();
		}
		bookFile.close();
	}
	
	/**Populates shop's inventory with Equipment items from source file
	 * 
	 * @param shopInventory: HashMap<Integer, Item> that contains inventory items
	 * @param bookSource: .txt source file for Equipment items
	 * @param idNumber: integer id number for the first Equipment item
	 * @throws FileNotFoundException:
	 */
	public static void populateEquipmentItems(HashMap<Integer, Item> shopInventory,
			File equipmentSource, int idNumber) throws FileNotFoundException {
		
		Scanner equipmentFile = new Scanner(equipmentSource);
		String readline = equipmentFile.nextLine(); //read the first line, do not need it
		//System.out.println(readline);
		
		while (equipmentFile.hasNext()) {
			readline = equipmentFile.nextLine(); //read next line
			Scanner tokens = new Scanner(readline).useDelimiter(",\\s*");
			
			//read all fields (name, quantity, cost, gradeLevel, category)
			String name = tokens.next();
			int quantity = tokens.nextInt();
			double cost = tokens.nextDouble();
			String category = tokens.next();
			
			//create and add a new instance of Book to the inventory
			shopInventory.put(idNumber, (new Equipment(name, quantity, idNumber,
							cost, category)));
			System.out.print(shopInventory.get(idNumber).getIdNumber() + ": ");			
			System.out.print(shopInventory.get(idNumber).getName() + ", ");
			
			System.out.print(shopInventory.get(idNumber).getQuantity() + ", ");
			System.out.print(shopInventory.get(idNumber).getCost() + ", ");
			if (shopInventory.get(idNumber) instanceof Equipment) {
				System.out.println(((Equipment) shopInventory.get(idNumber)).getCategory());
			idNumber++;
			}
			tokens.close();
		}
		equipmentFile.close();
	}
	
	/**Populates shop's inventory with Supplies items from source file
	 * 
	 * @param shopInventory: HashMap<Integer, Item> that contains inventory items
	 * @param bookSource: .txt source file for Supplies items
	 * @param idNumber: integer id number for the first Supplies item
	 * @throws FileNotFoundException:
	 */
	public static void populateSuppliesItems(HashMap<Integer, Item> shopInventory,
			File suppliesSource, int idNumber) throws FileNotFoundException {
		
		Scanner suppliesFile = new Scanner(suppliesSource);
		String readline = suppliesFile.nextLine(); //read the first line, do not need it
		//System.out.println(readline);
		
		while (suppliesFile.hasNext()) {
			readline = suppliesFile.nextLine(); //read next line
			Scanner tokens = new Scanner(readline).useDelimiter(",\\s*");
			
			//read all fields (name, quantity, cost, gradeLevel, category)
			String name = tokens.next();
			int quantity = tokens.nextInt();
			double cost = tokens.nextDouble();
			
			//create and add a new instance of Book to the inventory
			shopInventory.put(idNumber, (new Supplies(name, quantity, idNumber,
							cost)));
			System.out.print(shopInventory.get(idNumber).getIdNumber() + ": ");			
			System.out.print(shopInventory.get(idNumber).getName() + ", ");
			
			System.out.print(shopInventory.get(idNumber).getQuantity() + ", ");
			System.out.println(shopInventory.get(idNumber).getCost());
			idNumber++;
			tokens.close();
		}
		suppliesFile.close();
	}
	
}
	
		
			
		
