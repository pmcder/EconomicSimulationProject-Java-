/**MET CS 622
 * Assignment 2
 * class Simulation
 * by Iryna Chervachidze
 * May 23, 2020
 */

package simulationPackage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import customerPackage.Customer;
import inventoryPackage.*;

/*
 *To Do List:
 *1. ???create a separate class with all setup info, such as file names, restocking constants, margin, etc.
 *2. Single out the probability of having a customer
 *3. If system cannot find one file: Quitting for now. Try to ask to provide legal file?
 *4. Re-factor!!!
 *5. customer preferences do not include grades or Equipment class
 *6. collect stats in Shop class (or separate Metrics class?) to produce a graph?
 *7. validate user's input
 *8. scanner input is not closed!!!
 *9. 
 *10.
 * 
 */
public class Simulation {

	public static void main(String[] args) {
		/*
		 * Precondition 1: Book items are populated from "book.txt" source file
		 * Precond 2: Equipment items are populated from "equipment.txt" source file
		 * Precond 3: Supplies items are populated from "supplies.txt" source file
		 * 
		 * Postcondition 1: Create a shop with inventory
		 * Post 2: Populate shop's inventory with items from source files
		 * Post 3: Display the contents of the inventory
		 * Post 4: Create a list of potential customers
		 * Post 5: Select a customer and display:
		 * 		a. customer's budget
		 * 		b. customer's selected preference
		 * 		c. what items the customer purchases
		 * Post 6: Display stats for 30-day period
		 * Post 7: Prompt the user to continue/quit
		 * 
		 */
		
		//    CREATE SUPPLY SIDE: CREATE SHOP AND LOAD INVENTORY
		//==========================================================
		
		//Precondition 1, 2, 3: source files for inventory
		File bookSource = new File("./src/resources/book.txt");
		File equipmentSource = new File("./src/resources/equipment.txt");
		File suppliesSource = new File("./src/resources/supplies.txt");
		
		//Post 1: create a shop
		Shop shop = new Shop();
		HashMap<Integer, Item> shopInventory = shop.getInventory();
		
		//Post 2 and Post 3: load from file
		System.out.println("LOADING INVENTORY...");
		System.out.println("====================");
		LoadItems.loadFromFile(shop, shopInventory, bookSource, equipmentSource, suppliesSource);
		
		
		//      CREATE DEMAND SIDE: CREATE POTENTIAL CUSTOMERS
		//======================================================
		Random random = new Random();
		int randomCustomerIndex; 
		//String category; //category of preference for a random customer
		double budget; // customer's budget
		int day = 1; // keeps track of day numbers
		int count = 0;//count up to 30 days for one run of the simulation
		
		//Post 4:
		//Create a list of potential customers with varying buying preferences
		ArrayList<Customer> customerOptions = new ArrayList<>();
		populateRandomCustomers(customerOptions);
		
		
        //      RUN SIMULATION: CUSTOMERS AND SHOP INTERACT
		//======================================================
		
		System.out.println("\nSHOP SIMULATION STARTS HERE...");
		System.out.println("================================");
		
		while (true) {//start simulation loop
			while (count < 30) {//run simulation for 30 days (a month) at a time
				double totalDaySales = 0;
				double totalDayCosts = 0;
				int NUMBER_OF_ATTEMPTS = 10;//max number of attempts for the customer
				//to complete purchase; necessary to avoid possible infinite loops
				
				
				//If the probability of having a customer is greater than 50%, 
				// randomly select a customer
				if (random.nextDouble() >= 0.5) {
					
					//Post 5:
					//randomly select a customer from customer options
					randomCustomerIndex = random.nextInt(customerOptions.size());
					Customer customer = customerOptions.get(randomCustomerIndex);
					
					//randomly assign budget for selected customer to spend 
					budget = 300.0*random.nextDouble() + 100.0; //max up to 400
					customer.setBudget(budget); //set customer's budget
					
					//make selection of items according to customer's preference
					selectPreferredItems (customer, shop);
					ArrayList<Item> selectedItems = shop.getSelectedItems(); // retrieve selected items
					
					//Post 5a and 5b
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
						
						//decrement customer budget by the price of the item
						if (customer.spendBudget(itemPrice)) {
							
							//Post 5c:
							System.out.printf("\t\tbuying item: %s at price: %.2f%n", item.getName(), itemPrice);
							System.out.printf("\t\tbudget now: %.2f%n", customer.getBudget());
							//decrement quantity of available selected items
							
							try {
								selectedItems.get(itemIndex).sellItem();
							} catch (ZeroStockException e) {
								System.out.println(e.getMessage());
							}
							//increment total sales for the day
							totalDaySales += itemPrice;
							totalDayCosts += itemCost;
						}
						numberOfAttempts++;
					}
					//check if items need restocking
					for (Item item : selectedItems)
						item.reStock();
					shop.clearSelection();
				}//end of if (end of day)
				else
					System.out.printf("\nDAY %d", day);
				
				System.out.printf("\t Sales: %.2f%n", totalDaySales);
				shop.incrementSales(totalDaySales);
				shop.incrementCosts(totalDayCosts);
				count++;
				day++;
		}//end of while
			
			//Post 6:
			System.out.printf("\nTOTAL SALES for the last 30 days: $%.2f%n", shop.getSales());
			System.out.printf("TOTAL PROFITS for the last 30 days " 
					+ "(total sales - items cost - fixed cost): $%.2f",
					(shop.getSales() - shop.getCosts() - shop.getFixedCost()));
			shop.setSalesToZero();
			shop.setCostsToZero();
			
			//Post 7:
			System.out.println("\nWould you like to run the shop for another month? "
					+ "Press N to exit");
			java.util.Scanner input = new java.util.Scanner(System.in);
			String userAnswer = input.nextLine();
			if (userAnswer.equals("N") || userAnswer.equals("n")) {
				System.out.println("Exiting simulation...");
				System.exit(0);
				
			}
			else count = 0;//reset the count
			
		}//end of first while
	}//end of main
	
	
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
	 * @param customer: given Customer
	 * @param shop: the shop 
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
	
