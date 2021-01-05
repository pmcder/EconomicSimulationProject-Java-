/**MET CS 622
 * Assignment 4
 * by Iryna Chervachidze
 * June 8, 2020
 */
 
 
 Main method in class Simulation, simulationPackage, runs the program.
 
 ==============================================
 For JavaFX, I used e(fx)clipse plug-ins.
 Please disregard HW4_Debugging1 and HW4_Debugging2 in simulationPackage. Those 
 files are for testing/debugging purposes only.
 
 JavaFX gui situation:
 The idea is that at the end of the 30-day run, the simulation starts gui 
 application: a window with two buttons, one to continue with the simulation 
 for another 30 days and another button to quit. The quit button works very well.
 The run-again button works after the first run, but when the simulation call 
 on the javaFX application again, it crashes. I use Platform.exit() to terminate
 gui application (RunAgain class, guiPackage), but it does not seem to work.