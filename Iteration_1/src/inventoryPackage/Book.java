/**MET CS 622
 * Assignment 1
 * class Book
 * by Iryna Chervachidze
 * May 19, 2020
 */
package inventoryPackage;

import java.util.ArrayList;


public class Book extends Item {
	private static int REORDER_THRESHOLD_DEFAULT = 4;
	private static int REORDER_QUANTITY_DEFAULT = 12;
	private String gradeLevel;
	private String category;//such as math, language arts, science, etc.
	private ArrayList<String> grades = defineGrades();
	
	//constructors
	public Book() {}
	
	public Book(String name, int quantity, int idNumber, 
			double cost, String gradeLevel,  String category) { 
		super(name, quantity, idNumber, cost);
		setGradeLevel(gradeLevel);
		this.category = category;
		this.minAllowable = REORDER_THRESHOLD_DEFAULT;
		this.reOrderQuantity = REORDER_QUANTITY_DEFAULT;
	}
	
	//getters
	 public String getGradeLevel() {return this.gradeLevel;}
	 public String getCategory() {return this.category;}
	 public int getMinAllowable() {return this.minAllowable;}
	 public ArrayList<String> getAllowableGrades() {return this.grades;}
	 
	 
	 //setters
	public void setCategory(String category) {this.category = category;}
	public void setMinAllowable(int numberOfItems) {
		if (numberOfItems < 0) throw new IllegalArgumentException();
		else this.minAllowable = numberOfItems;	
	}
	
	/**Sets the grade level 
	 * @param gradeLevel: only K or 1-12 allowed
	 */
	public void setGradeLevel(String gradeLevel) {
		if (grades.contains(gradeLevel))
			this.gradeLevel = gradeLevel;
		else throw new IllegalArgumentException();
		}
	
	private ArrayList<String> defineGrades() {		
		ArrayList<String> acceptableGrades = new ArrayList<>();
		acceptableGrades.add("K");
		for (int i = 1; i <= 12; i++) {
			acceptableGrades.add(i + "");
		}
		return acceptableGrades;
	}
	
	public String toString() {
		 return super.toString() + "\nGrade level: " + gradeLevel +
				 "\nCategory: " + category;
	}
}
