package simulationPackage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import customerPackage.Customer;
import inventoryPackage.Item;
import inventoryPackage.Shop;

public class HW4_Debugging1 {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		Shop myshop = new Shop();
		Customer<String> customer;
		HashMap<Integer, Item> inventoryFromFile = myshop.getInventory();
		
		
		try (ObjectInputStream fileInput = new ObjectInputStream(new FileInputStream("./src/resources/finalInventory.dat"))){
			inventoryFromFile = (HashMap<Integer, Item>) fileInput.readObject();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		myshop.setInventory(inventoryFromFile);
		
		for (Item e: myshop.getInventory().values())
			System.out.printf("%d: %s, %d, %.2f%n", e.getIdNumber(), 
					e.getName(), e.getQuantity(), e.getCost());
		
		
		System.out.println(inventoryFromFile.get(1).getClass().getName());
		//HashMap<Integer, Item> shopIntentory = (HashMap<Integer, Item>) inventoryFromFile;
		
		System.out.println("Math category");
		myshop.selectCategory("math");
		ArrayList<Item> selectedItems = myshop.getSelectedItems();
		System.out.println(myshop.getSelectedItems().size());
//		for (Item e : myshop.getSelectedItems())
//			System.out.println
		
//		Stream.of(selectedItems).limit(5).sorted((e1, e2) -> Integer.compare(e1.getQuantity(), e2.getQuantity())
//		.forEach(e -> System.out.print(e + " ")));
		
		selectedItems.stream().sorted(Comparator.comparing(e -> e.getQuantity())).forEach(e -> System.out.println(e + " "));
		System.out.println();
		System.out.println("Reverse order");
		System.out.println();
		List<Item> sortedItems = selectedItems.stream().sorted(Comparator.comparingInt(Item::getQuantity).reversed())
				.collect(Collectors.toList());
		sortedItems.stream().limit(5).forEach(e -> System.out.printf("%s: %d count%n", e.getName(), e.getQuantity()));
//			List<Item> reverseSortedItems = sortedItems.stream().sorted(Comparator.reverseOrder())
//					.collect(Collectors.toList());
//			reverseSortedItems.forEach(e -> System.out.println(e + " "));

		

	}

}
