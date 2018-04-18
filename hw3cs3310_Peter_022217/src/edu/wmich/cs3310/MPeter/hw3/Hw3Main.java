package edu.wmich.cs3310.MPeter.hw3;

import java.util.*;

import java.io.*;

/**
 * 
 * AUTHOR: Matt Peter
 * CLASS: CS 3310
 * DATE: 3/16/17
 * I give permission to the instructor to share my solution(s) with the class
 * 
 * This program implements priority queues using heaps in their explicit and
 * implicit representations.  It performs operations of insert, findMin, and 
 * deleteMin on a size-balanced binary tree (SBT) in its explicit representation 
 * and an array-based complete binary tree (CBT) in its implicit representation,
 * both in min-heap form. An example of each operation's affects on the binary tree
 * occurs when the size of the trees reaches 30.
 * 
 * @author Matt Peter
 *
 */
public class Hw3Main {
	/**
	 * This method performs a randomly generated sequence of operations (insert, 
	 * findMin, and deleteMin) in which the number of operations is specified by 
	 * the user.  When the size of the trees reaches 30, an example of each operation
	 * being performed (both before and after the operation) is shown.
	 * 
	 * @param args Stuff entered by the user in the console
	 */
	public static void main(String[] args) throws IOException {
		// Declare and Instantiate all objects and variables
		Scanner kbrd = new Scanner(System.in);
		Random rand = new Random();
		
		SBT mainSBT = new SBT();
		CBT mainCBT = new CBT();
		
		int length = 0;					// Number of operations to perform
		int currentSize = 0;			// Size of trees
		boolean statesPrinted = false;	// Tells whether example operations have been performed
		
		// Obtain the sequence length from the user
		System.out.print("Please enter a sequence length: ");
		length = kbrd.nextInt();

		
		// Enter a loop that will perform operations
		for (int i = 1; i <= length; i++) {
			int operation = (rand.nextInt(10000) + 1);
			if (operation <= 2000) {
				int key = (rand.nextInt(1000) + 1);
				int value = (rand.nextInt(15000) + 5000);
				mainSBT.insert(key, value);
				mainCBT.insert(key, value);
				currentSize++;
			} else if (operation >= 4001) {
				mainSBT.findMin();
				mainCBT.findMin();
			} else {
				mainSBT.deleteMin();
				mainCBT.deleteMin();
				if (currentSize > 0) {
					currentSize--;
				}
			}
			
			// Perform example operations when size reaches 30 for the first time
			if ((currentSize == 30) && (statesPrinted == false)) {
				printCBT(mainCBT);
				printPreOrderSBT(mainSBT);
				printInOrderSBT(mainSBT);
				statesPrinted = true;
			}
		}
		
		// Close the Scanner object
		kbrd.close();
	}
	
	/**
	 * This method makes a copy of the CBT and performs each operation on it, 
	 * printing the state of the CBT before and after each operation.
	 * 
	 * @param mainCBT Main CBT that is copied for testing
	 */
	private static void printCBT(CBT mainCBT) {
		// Declare and Instantiate Random object
		Random rand = new Random();
		
		// Declare and Instantiate test CBT and store values from main CBT in array
		CBT testCBT = new CBT();
		int[][] tempCBTArray = new int[31][2];
		tempCBTArray = mainCBT.getCBT();
		
		// Print out values in order stored in array
		System.out.println("CBT");
		System.out.println("----");
		System.out.print("Before Insert: ");
		printCBTArray(tempCBTArray, 30);
		System.out.println();
	
		// Add a value to array
		tempCBTArray[30][0] = (rand.nextInt(1000) + 1);
		tempCBTArray[30][1] = (rand.nextInt(15000) + 5000);

		// Create new CBT and collect values
		for (int j = 0; j < 31; j++) {
			testCBT.insert(tempCBTArray[j][0], tempCBTArray[j][1]);
		}
		tempCBTArray = testCBT.getCBT();
		
		// Print out values after new value is inserted
		System.out.print("After Insert: ");
		printCBTArray(tempCBTArray, 31);
		System.out.println();
		
		
		// Reset array to original values and print
		tempCBTArray = mainCBT.getCBT();
		System.out.print("Before DeleteMin: ");
		printCBTArray(tempCBTArray, 30);
		System.out.println();
		
		// Insert values into CBT, delete smallest value, and put new order of values in array
		testCBT = new CBT();
		for (int j = 0; j < 30; j++) {
			testCBT.insert(tempCBTArray[j][0], tempCBTArray[j][1]);
		}
		testCBT.deleteMin();
		tempCBTArray = testCBT.getCBT();
		
		// Print out values after smallest value is deleted
		System.out.print("After DeleteMin: ");
		printCBTArray(tempCBTArray, 29);
		System.out.println();
		
		
		// Reset array to original values and print
		tempCBTArray = mainCBT.getCBT();
		System.out.print("Before FindMin: ");
		printCBTArray(tempCBTArray, 30);
		System.out.println();
		
		// Print out minimum value in array
		System.out.println("Min: <" + mainCBT.findMin()[0] + ", " + mainCBT.findMin()[1] + ">");
		
		// Print out array after minimum value is printed
		System.out.print("After FindMin: ");
		printCBTArray(tempCBTArray, 30);
		System.out.println("\n\n");
	}
	
	/**
	 * This method makes a copy of the SBT and performs each operation on it, 
	 * printing the state of the SBT before and after each operation in preorder
	 * format.
	 * 
	 * @param mainSBT Main SBT that is copied for testing
	 */
	private static void printPreOrderSBT(SBT mainSBT) {
		// Declare and Instantiate Random object
		Random rand = new Random();
		
		// Declare and Instantiate test SBT and store values from main SBT in preorder format
		System.out.println("SBT Preorder");
		System.out.println("-------------");
		SBT testSBT = new SBT();
		int[][] tempSBTArray = new int[31][3];
		tempSBTArray = mainSBT.getSBT("PreOrder");

		// Print out values in preorder format
		System.out.print("Before Insert: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Get values in order they were inserted and add another value
		tempSBTArray = mainSBT.getSBT("Insertion Order");
		tempSBTArray[30][0] = (rand.nextInt(1000) + 1);
		tempSBTArray[30][1] = (rand.nextInt(15000) + 5000);

		// Create new SBT and collect values
		for (int j = 0; j < 31; j++) {
			testSBT.insert(tempSBTArray[j][0], tempSBTArray[j][1]);
		}
		tempSBTArray = testSBT.getSBT("PreOrder");
		
		// Print out values after new value is inserted
		System.out.print("After Insert: ");
		printSBTArray(tempSBTArray, 31);
		System.out.println();
		
		
		// Reset nodes to original values and print
		tempSBTArray = mainSBT.getSBT("PreOrder");
		System.out.print("Before DeleteMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Insert values into SBT, delete smallest value, and store values in preorder format
		tempSBTArray = mainSBT.getSBT("Insertion Order");
		testSBT = new SBT();
		for (int j = 0; j < 30; j++) {
			testSBT.insert(tempSBTArray[j][0], tempSBTArray[j][1]);
		}
		testSBT.deleteMin();
		tempSBTArray = testSBT.getSBT("PreOrder");
		
		// Print out values after smallest value is deleted
		System.out.print("After DeleteMin: ");
		printSBTArray(tempSBTArray, 29);
		System.out.println();
		
		
		// Reset nodes to original values and print
		tempSBTArray = mainSBT.getSBT("PreOrder");
		System.out.print("Before FindMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Print out minimum node value in tree
		System.out.println("Min: [<" + mainSBT.findMin()[0] + ", " + mainSBT.findMin()[1] + ">, " + mainSBT.findMin()[2] + "]");
		
		// Print out node values after minimum value is printed
		System.out.print("After FindMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println("\n\n");
	}
	
	/**
	 * This method makes a copy of the SBT and performs each operation on it, 
	 * printing the state of the SBT before and after each operation in inorder
	 * format.
	 * 
	 * @param mainSBT Main SBT that is copied for testing
	 */
	private static void printInOrderSBT(SBT mainSBT) {
		// Declare and Instantiate Random object
		Random rand = new Random();
		
		// Declare and Instantiate test SBT and store values from main SBT in inorder format
		SBT testSBT = new SBT();
		int[][] tempSBTArray = new int[31][3];
		tempSBTArray = mainSBT.getSBT("InOrder");
		
		// Print out values in inorder format
		System.out.println("SBT Inorder");
		System.out.println("------------");
		System.out.print("Before Insert: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Get values in order they were inserted and add another value
		tempSBTArray = mainSBT.getSBT("Insertion Order");
		tempSBTArray[30][0] = (rand.nextInt(1000) + 1);
		tempSBTArray[30][1] = (rand.nextInt(15000) + 5000);

		// Create new SBT and collect values
		for (int j = 0; j < 31; j++) {
			testSBT.insert(tempSBTArray[j][0], tempSBTArray[j][1]);
		}
		tempSBTArray = testSBT.getSBT("InOrder");
		
		// Print out values after new value is inserted
		System.out.print("After Insert: ");
		printSBTArray(tempSBTArray, 31);
		System.out.println();
		
		
		// Reset nodes to original values and print
		tempSBTArray = mainSBT.getSBT("InOrder");
		System.out.print("Before DeleteMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Insert values into SBT, delete smallest value, and store values in inorder format
		tempSBTArray = mainSBT.getSBT("Insertion Order");
		testSBT = new SBT();
		for (int j = 0; j < 30; j++) {
			testSBT.insert(tempSBTArray[j][0], tempSBTArray[j][1]);
		}
		testSBT.deleteMin();
		tempSBTArray = testSBT.getSBT("InOrder");
		
		// Print out values after smallest value is deleted
		System.out.print("After DeleteMin: ");
		printSBTArray(tempSBTArray, 29);
		System.out.println();
		
		
		// Reset nodes to original values and print
		tempSBTArray = mainSBT.getSBT("InOrder");
		System.out.print("Before FindMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
		
		// Print out minimum node value in tree
		System.out.println("Min: [<" + mainSBT.findMin()[0] + ", " + mainSBT.findMin()[1] + ">, " + mainSBT.findMin()[2] + "]");
		
		// Print out node values after minimum value is printed
		System.out.print("After FindMin: ");
		printSBTArray(tempSBTArray, 30);
		System.out.println();
	}
	
	/**
	 * This method prints the passed array in the format of a CBT.
	 * 
	 * @param arr Array containing CBT
	 * @param size Size of CBT
	 */
	private static void printCBTArray(int[][] arr, int size) {
		for (int i = 0; i < size; i++) {
			System.out.print("<" + arr[i][0] + ", " + arr[i][1] + ">");
			if (i != (size - 1)) {
				System.out.print(", ");
			}
		}
	}
	
	/**
	 * This method prints the passed array in the format of an SBT.
	 * 
	 * @param arr Array containing SBT
	 * @param size Size of SBT
	 */
	private static void printSBTArray(int[][] arr, int size) {
		for (int i = 0; i < size; i++) {
			System.out.print("[<" + arr[i][0] + ", " + arr[i][1] + ">, " + arr[i][2] + "]");
			if (i != (size - 1)) {
				System.out.print(", ");
			}
		}
	}

}
