/**MET CS 622
 * Assignment 4
 * class Supplies
 * by Iryna Chervachidze
 * June 8, 2020
 */
package inventoryPackage;


public class Supplies extends Item {
	private static int REORDER_THRESHOLD_DEFAULT = 10;
	private static int REORDER_QUANTITY_DEFAULT = 25;
	
	//constructor
	public Supplies() {}
	public Supplies(String name, int quantity, int idNumber, 
			double cost) { 
		super(name, quantity, idNumber, cost);
		this.minAllowable = REORDER_THRESHOLD_DEFAULT;
		this.reOrderQuantity = REORDER_QUANTITY_DEFAULT;
	}
	
	//getters
	 public int getMinAllowable() {return this.minAllowable;}
	 
	 
	 //setters
	public void setMinAllowable(int numberOfItems) {
		if (numberOfItems < 0) throw new IllegalArgumentException();
		else this.minAllowable = numberOfItems;	
	}
	
	public String reStockWarning() {
		String message = String.format("\n\tRestocking "
				+ "quantity: %d\n", this.reOrderQuantity);
		return super.reStockWarning() + message;
	}
	
		
	public String toString() {
		 return super.toString();
	}

}
