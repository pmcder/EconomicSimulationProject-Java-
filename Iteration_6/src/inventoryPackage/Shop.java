/**MET CS 622
 * Assignment 4
 * class Shop
 * by Iryna Chervachidze
 * June 8, 2020
 */

package inventoryPackage;

import java.util.ArrayList;
import java.util.HashMap;


public class Shop {
	private double fixedCost = 200.00; //cost payable per period (such as month, or 30 days)
	private HashMap<Integer, Item> shopInventory;//empty inventory
	private ArrayList<Item> selectedItems = new ArrayList<>();//empty selection
	private ArrayList<Item> soldItems = new ArrayList<>();//empty selection
	private double sales = 0;
	private double costs = 0;

	//constructor
	public Shop() {shopInventory = new HashMap<>();}
	
	//getters
	public double getFixedCost() {return this.fixedCost;}
	public HashMap<Integer, Item> getInventory() {return this.shopInventory;}
	public ArrayList<Item> getSelectedItems() {return this.selectedItems;}
	public double getSales() {return this.sales;}
	public double getCosts() {return this.costs;}
	public ArrayList<Item> getSoldItems() {return this.soldItems;}

	//setters
	public void setFixedCost(double fixedCost) {this.fixedCost = fixedCost;}
	public void setSalesToZero() {this.sales = 0.0;}
	public void setCostsToZero() {this.costs = 0.0;}
	public void setInventory(HashMap<Integer, Item> shopInventory) {this.shopInventory = shopInventory;}
	
	//Sets selection of items to empty
	public void clearSelection() {selectedItems = new ArrayList<>();}
	
	//Increments sales by given amount
	public void incrementSales(double increment) {
		if (increment < 0) throw new IllegalArgumentException();
		else this.sales += increment;
		}
	
	//Increments costs by given amount
	public void incrementCosts(double increment) {
		if (increment < 0) throw new IllegalArgumentException();
		else this.costs += increment;}
	
	/**Makes a selection of items from the inventory. Only items that are of
	 * given category will be selected and saved in selectedItems field.
	 * 
	 * @param category(String): category of choice (such as math, science, history, or language arts)
	 */
	public void selectCategory(String category) {
		for (Item item: shopInventory.values()) {
			if ((item instanceof Book && ((Book)item).getCategory().equals(category)
				|| item instanceof Equipment && ((Equipment) item).getCategory().equals(category))) {
				selectedItems.add(item);
			}
		}
	}
	
	/**Makes a selection of items from the inventory. Only items that are of
	 * given grade level will be selected and saved in selectedItems field.
	 * 
	 * @param category(String): grade Level of choice (such K to 12)
	 */
	public void selectGrade(String gradeLevel) {
		for (Item item: shopInventory.values()) {
			if ((item instanceof Book && ((Book)item).getGradeLevel().equals(gradeLevel))) {
				selectedItems.add(item);
			}
		}
	}
	
	/**Makes a selection of items from the inventory. Only items that are of
	 * class Book will be selected and saved in selectedItems field.
	 */
	public void selectBook() {
		for (Item item: shopInventory.values()) {
			if (item instanceof Book) {
				selectedItems.add(item);
			}
		}
	}
	
	/**Makes a selection of items from the inventory. Only items that are of
	 * class Supplies will be selected and saved in selectedItems field.
	 */
	public void selectSupplies() {
		for (Item item: shopInventory.values()) {
			if (item instanceof Supplies) {
				selectedItems.add(item);
			}
		}
	}
	
	/** Updates the list of sold items
	 * 
	 * @param item (Item): the item to add to the list
	 */
	public void updateSoldItems(Item item) {
		//get id number of the item
		int id = item.getIdNumber();
		//if the item is not in the list, add it
		if (!soldItems.contains(item)) {
			this.soldItems.add(item);
			item.incrementQuantitySold();
		}
		//if the item is already in the list, update the quantity of sold items
		else
			item.incrementQuantitySold();
	}
	
	/**Sets the sold quantity of each item in the sold items list to zero.
	 * Clears the list of sold items (replaces it with a new empty list).
	 * 
	 */
	public void clearSoldItems() {
		for (Item item: soldItems) {
			item.setQuantitySoldToZero();
			soldItems = new ArrayList<>();
		}
	}
	
	public String getItemType(Item item) {
		if (item instanceof Book) return "Book class";
		else if (item instanceof Equipment) return "Equipment class";
		else return "Supplies class";
	}
	
	
}//end of class

