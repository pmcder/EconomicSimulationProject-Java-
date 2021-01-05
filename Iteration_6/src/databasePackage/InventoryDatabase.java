package databasePackage;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import inventoryPackage.Item;
import inventoryPackage.Shop;
import simulationPackage.Setup;
import simulationPackage.Simulation;

public class InventoryDatabase {
	
	/**Reads data from a .txt file and inserts it into the database table.
	 * 
	 * @param conn (Connection) SQLLite connection
	 * @throws SQLException
	 * @throws FileNotFoundException
	 */
	private static void insert(Connection conn) throws SQLException, FileNotFoundException {
		//SQL command
		String sql = "INSERT INTO Supplies(name, quantity, cost) VALUES (?, ?, ?)";
		
		String name = "";
		int quantity = 0;
		double cost = 0.0;
		
		//======= READ DATA FROM .txt FILE AND INSERT INTO TABLE Supplies ======== 
		Scanner inFile = new Scanner(new File("./src/resources/supplies.txt"));
		String readline =  inFile.nextLine(); //read fist line, do not need it
			
		while (inFile.hasNext()) {
			readline =  inFile.nextLine();//read one line
			
			//parse one line into tokens
			Scanner tokens = new Scanner(readline).useDelimiter(",\\s*");
			name = tokens.next();
			quantity = tokens.nextInt();
			cost = tokens.nextDouble();
			tokens.close();
			
			//insert into the Table Supplies in Inventory Database
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			pstmt.setInt(2, quantity);
			pstmt.setDouble(3, cost);
			pstmt.executeUpdate();
			
			}
		}
	}
	
	/**Creates a new table Supplies in the inventory database
	 * 
	 * @param conn (Connection): SQLLite Connection
	 * @throws SQLException
	 */
	public static void createSuppliesTable(Connection conn) throws SQLException {
		//SQL command
		String sql = "CREATE TABLE IF NOT EXISTS Supplies (\n"
                + "	suppl_id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + " quantity INTEGER NOT NULL, \n"
                + "	cost TEXT NOT NULL\n"
                + ");";
		
		//execute the command
		try (Statement stmt = conn.createStatement()) {
			// create a new table
            stmt.execute(sql);
		}
	}
	
	/** Performs a query from the inventory database, Supplies table
	 * 
	 * @param (Connection): SQLLite Connection
	 * @throws SQLException
	 */
	private static void query(Connection conn) throws SQLException {
		//SQL command
		String sql = "SELECT suppl_id, name, quantity, cost FROM Supplies";
		
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//Print out the results
			while (rs.next()) {
				System.out.printf("%d\t%-20s\t%-7d\t%.2f%n",
						rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getDouble(4));
		}
	}
}
	
	public static void insertSold_ItemsTable(Connection conn, Shop shop, int salePeriod, int shopNumber) throws SQLException {
		String sql = "INSERT INTO Sold_Items(period, item, cost, price, quantity, type, shop_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
		ArrayList<Item> soldItems = shop.getSoldItems();
		int period = salePeriod;
		String item = "";
		double cost = 0;
		double price = 0;
		int quantity = 0;
		String type = "";
		int shopNum= shopNumber;
		
		
		for (int i = 0; i < soldItems.size(); i++) {
			if (!soldItems.get(i).equals(null)) {
			item = soldItems.get(i).getName();
			cost = soldItems.get(i).getCost();
			price = soldItems.get(i).getPrice();
			quantity = soldItems.get(i).getQuantitySold();
			type = shop.getItemType(soldItems.get(i));
		
	
			try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
				pstmt.setInt(1, period);
				pstmt.setString(2, item);
				pstmt.setDouble(3, cost);
				pstmt.setDouble(4, price);
				pstmt.setInt(5, quantity);
				pstmt.setString(6, type);
				pstmt.setInt(7, shopNum);
				pstmt.executeUpdate();
				}
			}
		}
	}
	
	public static void updateSold_ItemsTable(Shop shop, int period, int shopNumber) throws SQLException {
		String url = Setup.url;
		try (Connection conn = DriverManager.getConnection(url)) {
			insertSold_ItemsTable(conn, shop, period, shopNumber);
		}
	}
	
	public static void getTopSellers(int period) throws SQLException {
		String url = Setup.url;
		try (Connection conn = DriverManager.getConnection(url)){
			queryTopSoldItems(conn, period);
		}
	}
	
	public static void queryTopSoldItems(Connection conn, int period) throws SQLException {
	String sql = String.format("SELECT item, price, quantity, shop_number\n" + 
			"FROM Sold_Items\n" + 
			"WHERE period = %d\n" + 
			"ORDER BY quantity DESC\n" + 
			"LIMIT 10;", period);
	try (Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql)) {
		//Print out the results
		System.out.printf("%-45s\t%-20s\t%-10s\t%s%n",
				"item", "price", "quantity", "shop number");
		while (rs.next()) {
			System.out.printf("%-45s\t%-20.2f\t%-10d\t%d%n",
					rs.getString(1), rs.getDouble(2), rs.getInt(3), rs.getInt(4));
			}
		}
	}
	
	public static void selectScience(Connection conn) throws SQLException {
		String sql = "SELECT name, quantity, cost, category \n" + 
				"FROM Book \n" + 
				"where Book.category ='science' \n" + 
				"UNION \n" + 
				"SELECT name, quantity, cost, category \n" +
				"FROM Equipment \n" +
				"WHERE category ='science';";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//Print out the results
			System.out.printf("%-45s\t%-20s\t%-10s\t%s%n",
					"name", "quantity", "cost", "category");
			while (rs.next()) {
				System.out.printf("%-45s\t%-20d\t%-10.2f\t%s%n",
						rs.getString(1), rs.getInt(2), rs.getDouble(3), rs.getString(4));
				}
			}
		}
	
	public static void getScience() throws SQLException {
		String url = Setup.url;
		try (Connection conn = DriverManager.getConnection(url)) {
			selectScience(conn);
		}
	}
	
	public static void selectSales(Connection conn) throws SQLException {
		String sql = "SELECT period, SUM(price*quantity) AS sales \n" + 
				"FROM Sold_Items \n" + 
				"GROUP BY period;";
		try (Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {
			//Print out the results
			System.out.printf("%-15s\t%-20s%n",
					"period", "sales");
			while (rs.next()) {
				System.out.printf("%-15d\t%-20.2f%n",
						rs.getInt(1), rs.getFloat(2));
					}
				}
			}
	
	public static void getSales() throws SQLException {
		String url = Setup.url;
		try (Connection conn = DriverManager.getConnection(url)) {
			selectSales(conn);
		}
	}

	public static void main(String[] args) throws SQLException, FileNotFoundException {
		String url = Setup.url;
		try (Connection conn = DriverManager.getConnection(url)) {
			//createSuppliesTable(conn); //please do not run this again as table has been already created
			//insert(conn);//please do not run this again as all values have been updates
			query(conn);
			
		}
	}
}
