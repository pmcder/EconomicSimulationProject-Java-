/**MET CS 622
 * Assignment 3
 * Abstract class Item
 * by Iryna Chervachidze
 * June 1, 2020
 */
package inventoryPackage;


public abstract class Item {
	private static double DEFAULT_MARGIN_VALUE = 0.3;//default margin over the cost to determine the selling price
	private static double LOWEST_MARGIN_VALUE = 0;//price margin over the cost cannot be lower than 0%
	private static double HIGHEST_MARGIN_VALUE = 2;//price margin over the cost cannot be greater than 200%
	private static double margin = DEFAULT_MARGIN_VALUE; 
	
	private String name;
	private int quantity;//total quantity of a particular item available items in the inventory
	private int idNumber;
	private double price;
	private double cost;
	private double discount = 0;//as a decimal, e.g. 10% will be entered as 0.10
	protected int minAllowable; //if total inventory quantity falls to this level, re-order
	protected int reOrderQuantity;
	
	
	//constructors
	public Item() {}
	
	public Item(String name, int quantity, int idNumber, double cost) {
		this.name = name;
		this.quantity = quantity;
		this.idNumber = idNumber;
		this.cost = cost;
		setPrice();
	}
	
	//getters
	public String getName() {return this.name;}
	public int getQuantity() {return this.quantity;}
	public int getIdNumber() {return this.idNumber;}
	public double getPrice() {return this.price;}
	public double getCost() {return this.cost;}
	public double getDiscount() {return this.discount;}
	public static double getMargin() {return margin;}
	public double getProfit() {return price - cost;} //returns amount in dollars
	
	//setters
	public void setName(String name) {this.name = name;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	public void setIdNumber(int idNumber) {this.idNumber = idNumber;}
	protected void setPrice() {
		this.price = cost*(1 + margin - discount);
	}
	
	/** Sets the margin to determine the selling price
	 * 
	 * @param margin: double values in the range from 0 to 2 (e.g. 0.4)
	 */
	public static void setMargin(double margin) {
		if (margin < LOWEST_MARGIN_VALUE || margin > HIGHEST_MARGIN_VALUE) throw new IllegalArgumentException();
		else Item.margin = margin;
		}
	
	public void setCost(double cost) {
		if (cost < 0) throw new IllegalArgumentException();
		else this.cost = cost;
		}
	
	//discount may not be greater than margin
	public void setDiscount(double discount) {
		if (discount < 0 || discount > margin) throw new IllegalArgumentException();
		else
			this.discount = discount;
		}
	
	//sell one item
	public void sellItem() throws ZeroStockException {
		if (this.quantity <= 0) throw new ZeroStockException();
		else this.quantity --;
	}
	
	public String reStockWarning() {
		String message = String.format("\n\tRestocking Warning:\n\tItem name: "
				+ "%s\n\tItem id: %d", this.getName(), this.getIdNumber());
		return message;
	}
	
	public void reStock() {
		if (this.quantity <= minAllowable) {
			System.out.println(reStockWarning());
			quantity = reOrderQuantity;
		}
	}
	
	public String toString() {
		return "\nItem name: " + this.name +
				"\nid number: " + this.idNumber +
				"\nprice: " + this.price +
				"\ncost: " + this.cost +
				"\ndiscount: " + this.discount*100 +"%" +
				"\nreorder threshold: " + this.minAllowable;
	}
}
