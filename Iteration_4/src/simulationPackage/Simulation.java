/**MET CS 622
 * Assignment 4
 * class Simulation
 * by Iryna Chervachidze
 * June 8, 2020
 */

package simulationPackage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import customerPackage.Customer;
import inventoryPackage.*;
import javafx.application.Application;

/*
 *To Do List:
 *1. ???create a separate class with all setup info, such as file names, restocking constants, margin, etc.
 *2. Single out the probability of having a customer
 *3. If system cannot find one file: Quitting for now. Try to ask to provide legal file?
 *4. Re-factor!!!
 *5. customer preferences do not include grades or Equipment class
 *6. collect stats in Shop class (or separate Metrics class?) to produce a graph?
 *7. validate user's input
 *8. 
 *9. 
 *10.
 * 
 */
public class Simulation {

	public static void main(String[] args) {
		/*
		 * Precondition 1: Inventory items are populated from "inventory.dat" source file
		 * 
		 * Postcondition 1: Create a shop with inventory
		 * Post 2: Populate shop's inventory with items from source files
		 * Post 3: Display the contents of the inventory
		 * Post 4: Create a list of potential customers
		 * Post 5: Run simulation
		 * Post 6: Run javaFX application to prompt the user to quit/run again
		 * 
		 */
		
		//    CREATE SUPPLY SIDE: CREATE SHOP AND LOAD INVENTORY
		//==========================================================
		
		//Post 1: create a shop
		Shop shop = new Shop();
		HashMap<Integer, Item> shopInventory = shop.getInventory();
		
		//Post 2 and Post 3: load from file
		System.out.println("LOADING INVENTORY...");
		System.out.println("====================");
		
		try {
			LoadItems.loadBinary(shop);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//      CREATE DEMAND SIDE: CREATE POTENTIAL CUSTOMERS
		//======================================================
		Random random = new Random();
		int day = 1; // keeps track of day numbers
		
		//Post 4:
		//Create a list of potential customers with varying buying preferences
		ArrayList<Customer> customerOptions = new ArrayList<>();
		populateRandomCustomers(customerOptions);
		
		
        //      RUN SIMULATION: CUSTOMERS AND SHOP INTERACT
		//======================================================
		
		System.out.println("\nSHOP SIMULATION STARTS HERE...");
		System.out.println("================================");
		
		//Post 5:
		while (true) {//start simulation loop
			runSimulation(day, random, customerOptions, shop);
			
			//record inventory to .dat file
			try {
				LoadItems.writeBinary(shop);
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Post 6:
			//Application.launch(guiPackage.RunAgain.class, args);
			runGui();
			day += 30;//increment day count
			
		}//end while
	}//end of main
	
	public static void runGui() {
		Application.launch(guiPackage.RunAgain.class);
	}
	
	
	/**Runs shop simulation
	 * 
	 * Postconditions:
	 * 1. Randomly select a customer from list of customers with varying preferences
	 * 2. Display the preferences and budget of the customer on console
	 * 3. Display the items that the customer purchases
	 * 4. At the end of 30-day run, display the total sales/profits
	 * 
	 * @param day (int): keeps count of day number
	 * @param random (Random): random variable
	 * @param customerOptions (ArrayList): list of customers with varying preferences
	 * @param shop (Shop): the shop
	 */
	public static void runSimulation(int day, Random random, 
			ArrayList<Customer> customerOptions, Shop shop) {
		int randomCustomerIndex; //
		int count = 0; //simulation runs 30 days and then breaks for user input
		int NUMBER_OF_ATTEMPTS = 10;//max number of attempts for the customer
		//to complete purchase; necessary to avoid possible infinite loops
		double budget;
		
		
		while (count < 30) {//run simulation for 30 days (a month) at a time
			//If the probability of having a customer is greater than 50%, 
			// randomly select a customer
			if (random.nextDouble() >= 0.5) {
				
				//Post 1:
				randomCustomerIndex = random.nextInt(customerOptions.size());
				Customer customer = customerOptions.get(randomCustomerIndex);
				
				//randomly assign budget for selected customer to spend 
				budget = 300.0*random.nextDouble() + 100.0; //max up to 400
				customer.setBudget(budget); //set customer's budget
				
				//make selection of items according to customer's preference
				selectPreferredItems (customer, shop);
				ArrayList<Item> selectedItems = shop.getSelectedItems(); // retrieve selected items
				
				//Post 2:
				System.out.printf("%nDAY %d Customer created:%n\tpreference: "
						+ "%s %n\tbudget: %.2f%n", day, customer.getStringPreference(), customer.getBudget());
				
				// each customer will only have limited attempts to complete his
				//purchase to avoid program stalling in infinite loop
				int numberOfAttempts = 0;
				while (customer.getBudget() > 0 && numberOfAttempts < NUMBER_OF_ATTEMPTS) {
					
					//randomly select index of the item from selected items
					int itemIndex = random.nextInt(selectedItems.size());
					//System.out.println("index of the item " + itemIndex);
					
					//Get the selected item
					Item item = selectedItems.get(itemIndex);
					double itemPrice = item.getPrice();
					double itemCost = item.getCost();
					
					//Carry out the sale between the customer and the shop
					//Post 3. Display what the customer buys
					carryOutSale(customer, item, itemPrice, itemCost, 
							selectedItems, itemIndex, shop);
					numberOfAttempts++;
				}
				
				//check if items need restocking
				for (Item item : selectedItems)
					item.reStock();
				
				//clear the selection so that next customer can select items 
				//according to his preferences
				shop.clearSelection();
			}//end of if (end of day)
			
			else
				System.out.printf("\nDAY %d", day);
			
			System.out.printf("\t Sales month-to-today: %.2f%n", shop.getSales());
			count++;
			day++;
	}//end of while
		
		//Post 4:
		System.out.println(displayStats(shop));
		
		displayTopSellers(shop.getSoldItems());
		
		//set sales and costs to zero to prepare for another possible run
		shop.setSalesToZero();
		shop.setCostsToZero();
		//clear all sold items to prepare for the next run
		shop.clearSoldItems();
		
		
		
	}
	/**Sorts sold items in accordance to the quantity sold and displays the top
	 * 5 sellers
	 * 
	 * @param selectedItems(List<Item>) the list of sold items, stored in Shop class
	 */
	public static void displayTopSellers(List<Item> selectedItems) {
		System.out.println("\nTop sellers are:");
		System.out.println("===================");
		
		//Use stream() to sort and print items
		List<Item> sortedItems = selectedItems.stream().sorted(Comparator.comparingInt(Item::getQuantitySold)
				.reversed()).collect(Collectors.toList());
		sortedItems.stream().limit(5).forEach(e -> System.out.printf("%s: %d count%n", e.getName(), e.getQuantitySold()));
		
	}
	
	/**Displays statistics at the end of 30 day period
	 * 
	 * @param shop (Shop): the shop in the simulation
	 */
	public static String displayStats(Shop shop) {
		String stats = String.format("\nTOTAL SALES for the last 30 days: $%.2f"
				+ "\nTOTAL PROFITS for the last 30 days: "
				+ "(total sales - items cost - fixed cost): $%.2f", shop.getSales(),
				(shop.getSales() - shop.getCosts() - shop.getFixedCost()));
		return stats;
	}
	
	/**
	 * @param customer(Customer): a customer in the shop on that day
	 * @param item (Item): item that the customer is buying
	 * @param itemPrice (double): price of that item
	 * @param itemCost (double): cost of that item
	 * @param selectedItems (ArrayList<Item>): a list of items selected in 
	 * 			accordance with given customer preference
	 * @param itemIndex (int): randomly selected index
	 * @param totalDaySales (double): sales for the day
	 * @param totalDayCosts (double): costs for the day
	 */
	public static void carryOutSale(Customer customer, Item item, 
			double itemPrice, double itemCost, ArrayList<Item> selectedItems, 
			int itemIndex, Shop shop) {
		
		//the sale happens only if the customer's budget is enough to pay for it
		if (customer.spendBudget(itemPrice)) {
			//Post 3:
			System.out.printf("\t\tbuying item: %s at price: %.2f%n", item.getName(), itemPrice);
			System.out.printf("\t\tbudget now: %.2f%n", customer.getBudget());
			
			//decrement quantity of available selected items
			try {
				selectedItems.get(itemIndex).sellItem();
			} catch (ZeroStockException e) {
				System.out.println(e.getMessage());
			}
			//increment total sales for the day
			shop.incrementSales(itemPrice);
			shop.incrementCosts(itemCost);
			//update sold quantity
			shop.updateSoldItems(item);
		}
	}
	
	
	/** Creates a list of potential customers with varying preferences.
	 * Customers are hard coded for now.
	 * 
	 * @param customerOptions: storage for potential customers, empty at first
	 */
	public static void populateRandomCustomers(ArrayList<Customer> customerOptions) {
		customerOptions.add(new Customer<String> ("math", 0.0)); //customer with preference in math
		customerOptions.add(new Customer<String> ("science", 0.0));
		customerOptions.add(new Customer<String> ("language arts", 0.0));
		customerOptions.add(new Customer<String> ("history", 0.0));
		customerOptions.add(new Customer<Item> (new Book(), 0.0)); //customer with preference in Book items of any category
		customerOptions.add(new Customer<Item> (new Supplies(), 0.0));//customer with preference in Supplies items
	}
	
	
	/**Makes a selection of items for the customer to purchase according to his preference
	 * 
	 * @param customer (Customer): the customer in the shop
	 * @param shop (Shop): the shop in the simulation
	 */
	public static void selectPreferredItems(Customer customer, Shop shop) {
		//if the customer has preference in Book items, call selectBook() in 
		//Shop class to select only Book items
		if (customer.getPreference() instanceof Book) shop.selectBook();
		
		//if the customer has preference in Supplies items, call selectSupplies() in 
		//Shop class to select only Supplies items
		else if (customer.getPreference() instanceof Supplies) shop.selectSupplies();
		
		//if the customer has preference in items of a particular category, 
		//such as science, math, history, or language arts, call method  
		//selectCategory(category) in Shop class to select only items of that category
		else {
			String category = customer.getStringPreference();
			shop.selectCategory(category);
		}
	}
}
	
