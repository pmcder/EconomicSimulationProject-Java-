# School Supplies Store Simulation (in Java)
**This project simulates economic activity of a small school supply store.**

## Brief Description
Supply side is represented by the class Shop that controls the shop’s inventory. Initially inventory items are stored in .txt files and loaded at the start of the program. The demand side of the simulation is represented by the generic class Customer. Customer’s buying preferences may vary from items belonging to a particular class (such as Book or Supplies) to items of certain category (math, science, language arts, history). To introduce some uncertainty into the simulation, customer preferences are chosen at random.
<br>
<br>
This project was completed in several iterations. In the first several iterations, all inventory items are loaded into a hashmap structure that maps a key to a every unique item in the inventory. Customer's participation is determined by a random variable between 0 and 1. If the value of that variable is greater than .5 for a given (logical day) than the program creates an instance of a Customer with a randomly assigned buying preference and budget. Customer's buying preference determines what set of items the customer is going to spend his budget on. Buying choices within that set are also determined randomly. The assumption is that the Customer is willing to spend as much of his budget as possible without going over the budget.
<br>
<br>
Simulation runs for 30 logical days and then halts. The user can then choose to continue for another 30 days or quit. If the customer was created the program specifies what (random) preference and budget were assigned to that customer. Then the program lists the number of items that the customer bought in the store. 
To experiment with multiple threads, I created two shops that run concurrently, each in its own thread. Simulation still runs for 30 logical days and then prints out the report about the sales, profits and top ten items most in demand for that period. I used locks to print out the reports so that results of the simulation are displayed together for each shop.
For the last iteration, I experimented with JDBC and SQLLite. I created a small database that contained the inventory items and ran several SQL queries.

## How to Run and Techniques Used:
- Iteration 1: Inventory Package, main method in Main.java runs the program. Abstract and concrete classes, inheritance, polymorphism, and upcasting added.
- Iteration 2: Simulation Package, main method in Simulation.java runs the program. File I/O and exception handling, JUnit tests added.
- Iteration 3: Simulation Package, main method in Simulation.java runs the program. Generics added.
- Iteration 4: Simulation Package, main method in Simulation.java runs the program. Lambdas, streams, object saving and retrieval, a bit of JavaFX.
- Iteration 5: Simulation Package, main method in Threads.java runs the program. Concurrent threads.
- Iteration 6: Main method in class Threads, simulationPackage, runs the program. url for the database connection can be found in Setup class, simulationPackage. JDBC and SQLite.


