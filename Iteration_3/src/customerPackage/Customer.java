/**MET CS 622
 * Assignment 3
 * class Customer
 * by Iryna Chervachidze
 * June 1, 2020
 */
package customerPackage;

import inventoryPackage.Book;
import inventoryPackage.Equipment;
import inventoryPackage.Supplies;

public class Customer<E> {
	private E preference;
	private double budget;
	
	public Customer(E preference, double budget) {
		this.preference = preference;
		this.budget = budget;
	}
	
	//getters
	public E getPreference() {return this.preference;}
	public String getStringPreference() {
		if (this.preference instanceof Book) return "Book class";
		else if (this.preference instanceof Equipment) return "Equipment class";
		else if (this.preference instanceof Supplies) return "Supplies class";
		return (String) preference;
	}
	public double getBudget() {return this.budget;}
	
	//setters
	public void setPreference(E preference) {this.preference = preference;}
	public void setBudget(double budget) {this.budget = budget;}
	
	/** Simulates customer spending his budget. Reduces customer's 
	 * budget by the given amount.
	 * 
	 * @param decrement(double): the amount by which the budget needs to be reduced
	 * @returns (boolean): true if budget is decremented, false otherwise
	 * NOTE: this method will not throw exception if the budget becomes negative
	 */
	public boolean spendBudget(double decrement) {
		if (this.budget > decrement) {
			this.budget -= decrement;
			return true;
		}
		else return false;
	}
}


