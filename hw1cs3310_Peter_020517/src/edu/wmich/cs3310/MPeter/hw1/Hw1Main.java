package edu.wmich.cs3310.MPeter.hw1;

import java.util.*;

/**
 * 
 * AUTHOR: Matt Peter
 * CLASS: CS 3310
 * DATE: 2/5/17
 * I give permission to the instructor to share my solution(s) with the class
 * 
 * This program randomly generates n floating point numbers in the
 * range of [1, m) (with n and m being specified by the user). It sorts
 * the array and then uses several different types of searching to
 * see if a randomly generated value exists in the array.  It then randomly
 * generates two values (x and y) within the range and computes the number of values
 * in the array that fall in the range of [x, y].
 * 
 * @author Matt Peter
 *
 */
public class Hw1Main {
	
	/**
	 * This method gathers input from the user regarding the set of random numbers
	 * to be generated.  It then stores them in an array, sorts them using the
	 * Arrays.sort() method, and then runs several methods from the IterativeSearch
	 * and RecursiveSearch classes to collect information about the array.
	 * @param args Stuff entered by the user in the console
	 */
	public static void main(String[] args) {
		// Declare and Instantiate several objects and variables
		Scanner kbrd = new Scanner(System.in);			// Gathers input from user
		Random rand = new Random();						// Generates random values
		
		IterativeSearch is = new IterativeSearch();		// Runs search methods in iterative format
		RecursiveSearch rs = new RecursiveSearch();		// Runs search methods in recursive format
		
		float[] numbers;								// Randomly generated floating point values
		int n = 0;										// Number of values user wants generated
		int m = 0;										// Upper bound of values generated
		
		
		// Gather input from user
		System.out.print("Please enter the amount of numbers you'd like generated: ");
		n = kbrd.nextInt();
		
		System.out.print("Please enter the upper bound you'd like for the numbers generated: ");
		m = kbrd.nextInt();
		
		
		// Generate random values and adjust to fit range
		numbers = new float[n];
		for (int i = 0; i < n; i++) {
			numbers[i] = (rand.nextFloat() * (m - 1.0f)) + 1.0f;
		}
		
		// Sort array
		Arrays.sort(numbers);
		

		// Generate random values to search for in array
		float searchValue1 = (rand.nextFloat() * (m - 1.0f)) + 1.0f;
		float searchValue2 = (rand.nextFloat() * (m - 1.0f)) + 1.0f;
		float searchValue3 = (rand.nextFloat() * (m - 1.0f)) + 1.0f;
		
		// Adjust range values if necessary
		if (searchValue2 > searchValue3) {
			float temp = searchValue2;
			searchValue2 = searchValue3;
			searchValue3 = temp;
		}

		
		// Print array information
		System.out.println("\nIterative Binary Search");
		System.out.println("------------------------");
		System.out.println(is.binarySearch(numbers, searchValue1));
		
		System.out.println("\nRecursive Binary Search");
		System.out.println("------------------------");
		System.out.println(rs.binarySearch(numbers, searchValue1, 0, n - 1));
		
		System.out.println("\nIterative Ternary Search");
		System.out.println("-------------------------");
		System.out.println(is.ternarySearch(numbers, searchValue1));
		
		System.out.println("\nRecursive Ternary Search");
		System.out.println("-------------------------");
		System.out.println(rs.ternarySearch(numbers, searchValue1, 0, n - 1));
		
		System.out.println("\nIterative Quad Search");
		System.out.println("----------------------");
		System.out.println(is.quadSearch(numbers, searchValue1));
		
		System.out.println("\nRecursive Quad Search");
		System.out.println("----------------------");
		System.out.println(rs.quadSearch(numbers, searchValue1, 0, n - 1));
		
		System.out.println("\nIterative Range Query");
		System.out.println("----------------------");
		System.out.println(is.rangeQuery(numbers, searchValue2, searchValue3));
		
		System.out.println("\nRecursive Range Query");
		System.out.println("----------------------");
		System.out.println(rs.rangeQuery(numbers, searchValue2, searchValue3, 0, n - 1));
		
		// Close Scanner object
		kbrd.close();
	}

}
