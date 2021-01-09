/**MET CS 622
 * Assignment 3
 * class Shop
 * by Iryna Chervachidze
 * June 1, 2020
 */

package inventoryPackage;

import java.util.ArrayList;
import java.util.HashMap;


public class Shop {
	private double fixedCost = 200.00; //cost payable per period (such as month, or 30 days)
	private HashMap<Integer, Item> shopInventory;//empty inventory
	private ArrayList<Item> selectedItems = new ArrayList<>();//empty selection
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

	//setters
	public void setFixedCost(double fixedCost) {this.fixedCost = fixedCost;}
	public void setSalesToZero() {this.sales = 0.0;}
	public void setCostsToZero() {this.costs = 0.0;}
	
	//Sets selection of items to empty
	public void clearSelection() {selectedItems = new ArrayList<>();}
	
	/**
	 * Increments this shop object's sales 
	 * by the value of the argument passed. 
	 * Throws new NegativeSalesException if the argument passed is less than 0.
	 * @param increment value as a double
	 */
	public void incrementSales(double increment) {
		if (increment < 0) throw new NegativeSalesException();
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
	
	
}

