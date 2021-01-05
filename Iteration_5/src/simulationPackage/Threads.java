/**MET CS 622
 * Assignment 5
 * class Threads
 * Driver class
 * by Iryna Chervachidze
 * June 13, 2020
 */

package simulationPackage;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import inventoryPackage.Shop;
import javafx.application.Application;

public class Threads {
	
	public static void main(String[] args) {
		//1. Precondition: inventory loads from .dat file
		//
		//
		//2. Postcondition 1: Create two shops
		//3. Post 2: Load and display the contents of the inventory for each shop
		//4. Post 3: Create a thread for each shop
		//5. Post 4: Run simulation in each thread
		//6. Post 5: Prompt the user to either quit or continue for another 30-day run 
		
		// Post 1
		Shop myShop1 = new Shop();
		Shop myShop2 = new Shop();
		int shopNumber1 = 1;
		int shopNumber2 = 2;
		int day = 1;
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
		
		System.out.println("\nSHOP SIMULATION FOR BOTH SHOPS STARTS HERE...");
		System.out.println("================================================");
		
		while(true) {
			//Post 3: create threads for each shop
			Thread thread1 = new Thread(new Simulation(myShop1, shopNumber1, key, day));
			Thread thread2 = new Thread(new Simulation(myShop2, shopNumber2, key, day));
			
			//Post 4: run simulation for each shop (thread)
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
			
			
			
			//runGui();
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
