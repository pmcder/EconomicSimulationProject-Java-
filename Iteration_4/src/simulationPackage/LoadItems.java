/**MET CS 622
 * Assignment 4
 * class LoadItems
 * by Iryna Chervachidze
 * June 8, 2020
 */

package simulationPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import inventoryPackage.Book;
import inventoryPackage.Equipment;
import inventoryPackage.Item;
import inventoryPackage.Shop;
import inventoryPackage.Supplies;


/*
 * To Do List
 * If one of the files is not found, the program quits! Acceptable??
 * 
 */
 


public class LoadItems {
	
	public static void loadBinary(Shop myShop) 
							throws IOException, ClassNotFoundException {
		HashMap<Integer, Item> inventoryFromFile = myShop.getInventory();
		
		//Load inventory from .dat file
		ObjectInputStream fileInput = new ObjectInputStream(new FileInputStream("./src/resources/inventory.dat"));
			inventoryFromFile = (HashMap<Integer, Item>) fileInput.readObject();
		
		myShop.setInventory(inventoryFromFile);
		
		//Print inventory
		if (inventoryFromFile instanceof HashMap<?, ?>) {
			for (Item e: ((HashMap<Integer, Item>) inventoryFromFile).values())
				System.out.printf("%d: %s, %d, %.2f%n", e.getIdNumber(), 
						e.getName(), e.getQuantity(), e.getPrice());
		}
		fileInput.close();
	}
	
	public static void writeBinary(Shop myShop) 
		throws IOException, ClassNotFoundException {
		HashMap<Integer, Item> inventory = myShop.getInventory();
		
		//Write inventory object to the file
		ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("./src/resources/finalInventory.dat"));
			output.writeObject(inventory);
	}
	
	
	public static void loadFromFile(Shop shop, HashMap<Integer, Item> shopInventory, 
			File bookSource, File equipmentSource, File suppliesSource) {
		/*
		 * Precondition 1: Accept assigned files for loading items, as 
		 * 		specified in loadFromFile signature
		 *  
		 * Postcondition 1: 
		 * Post 2: Populate shop's inventory with items from source files
		 * Post 2: Display the contents of the inventory
		 * 
		 */
		
		int idNumber = 1;// first items has id number = 1
		
		//Post 2: Inventory contents
		//Populate inventory from the source files
		//Book items
		System.out.println("\nBooks table:"); // print header
		System.out.println("=====================\n");
		try (Scanner bookFile = new Scanner(bookSource)) {
			populateBookItems(shopInventory, bookFile, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println("Source file was not found. Program is exiting...");
			System.exit(1);
		}
		
		//Equipment items
		System.out.println("\nEquipment table:"); //print header
		System.out.println("=====================\n");
		idNumber = shopInventory.size() + 1;
		try (Scanner equipmentFile = new Scanner(equipmentSource)) {
			populateEquipmentItems(shopInventory, equipmentFile, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println("Source file was not found. Program is exiting...");
			System.exit(1);
		}
				
		//Supplies items
		System.out.println("\nSupplies table:");
		System.out.println("=====================\n");
		idNumber = shopInventory.size() + 1;
		try (Scanner suppliesFile = new Scanner(suppliesSource)) {
			populateSuppliesItems(shopInventory, suppliesFile, idNumber);
		}
		catch(FileNotFoundException e) {
			System.out.println("Source file was not found. Program is exiting...");
			System.exit(1);
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
			Scanner bookFile, int idNumber) throws FileNotFoundException {
		
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
			}
			idNumber++;
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
			Scanner equipmentFile, int idNumber) throws FileNotFoundException {
		
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
			
			}
			idNumber++;
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
			Scanner suppliesFile, int idNumber) throws FileNotFoundException {
	
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
