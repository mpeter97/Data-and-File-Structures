package edu.wmich.cs3310.MPeter.hw5;

import java.io.*;
import java.util.*;

/**
 * AUTHOR: Matt Peter
 * CLASS: CS 3310
 * DATE: 4/21/17
 * I give permission to the instructor to share my solution(s) with the class
 * EXTRA CREDIT BUFFER POOL IS USED
 * This program generates and sorts binary files with and without the
 * use of buffer pools and stores statistics collected from the sorting
 * process in a text file.
 * 
 * @author Matt Peter
 */
public class Hw5Main {
	
	/**
	 * This method calls methods from the UserInput class and uses the
	 * returned values to decide what will be executed next.
	 * @param args Stuff entered by the user in the console
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public static void main(String[] args) throws IOException {
		// Declare and Instantiate Scanner and UserInput objects
		Scanner kbrd = new Scanner(System.in);
		UserInput ui = new UserInput();
		
		// Declare and Instantiate File and FileWriter objects
		File file = new File("hw5stat.txt");
		FileWriter fw = new FileWriter(file, true);

		// Enter loop that continues until the user wants to quit
		boolean quit = false;
		while (quit == false) {
			// Get user input
			char mainMenuInput = ui.mainMenu();
			
			// Run the appropriate method using UserInput object or quit
			if ((mainMenuInput == 'g') || (mainMenuInput == 'G')) {
				ui.generateFile();
			} else if ((mainMenuInput == 's') || (mainMenuInput == 'S')) {
				ui.sortFile(fw);
			} else if ((mainMenuInput == 'r') || (mainMenuInput == 'R')) {
				ui.resizeBufferPool();
			} else if ((mainMenuInput == 'q') || (mainMenuInput == 'Q')) {
				quit = true;
			}
		}
		
		// Close FileWriter and Scanner objects
		fw.close();
		kbrd.close();
	}
}
