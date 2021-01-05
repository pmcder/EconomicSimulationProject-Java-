package simulationPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import inventoryPackage.Item;
import inventoryPackage.Shop;
import javafx.application.Application;

public class HW4_Debugging2 {
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		HashMap<Integer, String> hm = new HashMap<>();
		ArrayList<String> list = new ArrayList<>();
		hm.put(1,"one");
		hm.put(2,"two");
		hm.put(3,"three");
		hm.put(4,"four");
		hm.put(5,"five");
		hm.put(6,"six");
		hm.put(7,"seven");
		
		ArrayList<String> myList = new ArrayList<>();
		myList.add("one");
		myList.add("two");
		myList.add("three");
		myList.add("four");
		myList.add("five");
		
		Object inventory = new Object();
		
		
		//Write object into the file
		try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("temp.dat"))){
			output.writeObject(hm);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Upload object from file into the program
		try (ObjectInputStream input = new ObjectInputStream(new FileInputStream("temp.dat"))){
			inventory = input.readObject();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (inventory instanceof HashMap<?, ?>) {
			for (String e: ((HashMap<Integer, String>) inventory).values())
				System.out.print(e + ", ");
		}
		
		
		File bookSource = new File("./src/resources/book.txt");
		File equipmentSource = new File("./src/resources/equipment.txt");
		File suppliesSource = new File("./src/resources/supplies.txt");
		
		Shop myShop = new Shop ();
		HashMap<Integer, Item> shopInventory = myShop.getInventory();
		Object inventoryFromFile = myShop.getInventory();
		
		LoadItems.loadFromFile(myShop, shopInventory, bookSource, equipmentSource, suppliesSource);
		
		//Write objects into the file
		try (ObjectOutputStream fileOutput = new ObjectOutputStream(new FileOutputStream("./src/resources/inventory.dat"))){
			fileOutput.writeObject(shopInventory);
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Upload object from file into the program
		try (ObjectInputStream fileInput = new ObjectInputStream(new FileInputStream("./src/resources/inventory.dat"))){
			inventoryFromFile = fileInput.readObject();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (inventoryFromFile instanceof HashMap<?, ?>) {
			for (Item e: ((HashMap<Integer, Item>) inventoryFromFile).values())
				System.out.println(e.getName());
		}
		
		
		
		
		
		
		
		
		
		
			
		
		
		
			

		
	

		
		
		
		
		
		
		
		
		
		
		
//		System.out.println("Starting JavaFX application");
//		//Application.launch(guiPackage.RunAgain.class, args);
//		
//		Shop shop = new Shop();
//		shop.incrementSales(850.00);
//		shop.incrementCosts(250.00);
//		
//		String message = "\nTOTAL SALES for the last 30 days: $" + shop.getSales()
//		+ "\nTOTAL PROFITS for the last 30 days: " 
//		+ "(total sales - items cost - fixed cost): " 
//		+ (shop.getSales() - shop.getCosts() - shop.getFixedCost());
//		
//		System.out.println(message);

	}

}
