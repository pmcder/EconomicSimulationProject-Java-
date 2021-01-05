/**MET CS 622
 * Assignment 1
 * by Iryna Chervachidze
 * May 19, 2020
 */
 
 This package contains an abstract class Item and concrete classes Book, 
 Equipment, Supplies that inherit from Item. Polymorphism happens in 
 toString() method.
 
 Class Main, method main() runs the program. 
 Upcasting happens in main() method when
 shopInventory is created using a HashMap. All items in the shopInventory
 are declared of type Item and later initialized using either Book, Equipment,
 or Supplies classes that inherit from Item.
