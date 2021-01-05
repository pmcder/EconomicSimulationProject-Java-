/**MET CS 622
 * Assignment 5
 * class Threads
 * Driver class
 * by Iryna Chervachidze
 * June 13, 2020
 */

package simulationPackage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import databasePackage.InventoryDatabase;
import inventoryPackage.Shop;
import javafx.application.Application;

public class Threads {
	
	public static void main(String[] args) {
		//1. Precondition: inventory loads from .dat file
		//
		//
		//2. Postcondition 1: Create two shops
		//3. Post 2: Load and display the contents of the inventory for each shop
		//4. Post 3: Select and display items from tables Book and Equipment in category "science"
		//5. Post 4: Create a thread for each shop
		//6. Post 5: Run simulation in each thread
		//7. Post 6: Select and display top 10 sellers from the table Sold_items
		//8. Post 7: Post 7. Select and display combined sales by period 
		//9. Post 8: Prompt the user to either quit or continue for another 30-day run
		
		// Post 1
		Shop myShop1 = new Shop();
		Shop myShop2 = new Shop();
		int shopNumber1 = 1;
		int shopNumber2 = 2;
		int day = 1;
		int period = 0;
		Lock key = new ReentrantLock();
		
		//Precondition 1:
		String fileName = "./src/resources/inventory.dat";
		
		//Post 1: Load Data for shop 1
		System.out.println("LOADING INVENTORY FOR SHOP 1 ...");
		System.out.println("====================");
		
		try {
			LoadItems.loadBinary(myShop1, fileName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Post 1: Load Data for shop 2
		System.out.println("\n\nLOADING INVENTORY FOR SHOP 2 ...");
		System.out.println("====================");
		
		try {
			LoadItems.loadBinary(myShop2, fileName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Post 3: Select and display items from tables Book and Equipment in category "science"
		System.out.println("\nPERFORM SQL QUERY FROM TABLES BOOK AND EQUIPMENT...");
		System.out.println("selecting only items in category \"Science\"");
		System.out.println("====================================================");
		try {
			InventoryDatabase.getScience();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		System.out.println("\nSHOP SIMULATION FOR BOTH SHOPS STARTS HERE...");
		System.out.println("================================================");
		
		while(true) {
			//Post 4: create threads for each shop
			Thread thread1 = new Thread(new Simulation(myShop1, shopNumber1, key, day, period));
			Thread thread2 = new Thread(new Simulation(myShop2, shopNumber2, key, day, period));
			
			//Post 5: run simulation for each shop (thread)
			thread1.start();
			thread2.start();
			
			try {
				thread1.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				thread2.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		
			
			//Post 6: Select and display top 10 sellers from the table Sold_items
			System.out.printf("\nTOP 10 SELLERS FOR BOTH SHOPS BY QUANTITY SOLD: PERIOD %d\n", period);
			System.out.println("using Sold_items table from the database");
			System.out.println("============================================================");
			try {
				InventoryDatabase.getTopSellers(period);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			period++;//increment (30-day) simulation running period
			
			//Post 7. Select and display combined sales by period 
			System.out.println("\n\nCOMBINED SALES FROM BOTH SHOPS");
			System.out.println("using Sold_items table from the database");
			System.out.println("==========================================");
			try {
				InventoryDatabase.getSales();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			//Post 5
			System.out.println("\nWould you like to run the shop for another month? "
					+ "Press N to exit");
			java.util.Scanner input = new java.util.Scanner(System.in);
			String userAnswer = input.nextLine();
			if (userAnswer.equals("N") || userAnswer.equals("n")) {
				System.out.println("Exiting simulation...");
				//input.close();
				System.exit(0);
			}
		}
	}
	public static void runGui() {
		Application.launch(guiPackage.RunAgain.class);
	}
}//end of class
