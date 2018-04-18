package edu.wmich.cs3310.MPeter.hw2;

import java.util.*;

/**
 * 
 * AUTHOR: Matt Peter
 * CLASS: CS 3310
 * DATE: 2/19/17
 * I give permission to the instructor to share my solution(s) with the class
 * 
 * This program randomly generates n floating point numbers in the range of
 * [1, m) (with n and m being specified by the user).  It then sorts the array
 * using a variety of sorting methods, printing and resetting the array of
 * each sort.
 * 
 * @author Matt Peter
 *
 */
public class Hw2Main {

	/**
	 * This method gathers input from the user regarding the set of random
	 * numbers to be generated, stores the data in an array and linked list,
	 * and then sorts the array and linked list with 6 different sorting methods,
	 * printing out at most the first 100 sorted values.
	 * @param args Stuff entered by the user in the console
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		// Declare and Instantiate several objects and variables
		Scanner kbrd = new Scanner(System.in);		// Gathers input from user
		Random rand = new Random();					// Generates random values
		
		
		// Arrays and linked lists to store and reset data
		Float[] numbersGeneratedArray;
		Float[] numbersSortedArray;
		LinkedList<Float> numbersGeneratedList = new LinkedList<Float>();
		LinkedList<Float> numbersSortedList = new LinkedList<Float>();
		
		int n = 0;		// Number of values user wants
		int m = 0;		// Upper bound of values generated
		
		// Gather input from user
		System.out.print("Please enter the amount of numbers you'd like generated: ");
		n = kbrd.nextInt();
		
		System.out.print("Please enter the upper bound you'd like for the numbers generated: ");
		m = kbrd.nextInt();
		
		// Create sorting objects
		ArraySort as = new ArraySort(n);
		LinkedListSort lls = new LinkedListSort();
		
		// Set size of arrays
		numbersGeneratedArray = new Float[n];
		numbersSortedArray = new Float[n];
		
		// Fill arrays and linked lists with randomly generated values
		for (int i = 0; i < n; i++) {
			float temp = (rand.nextFloat() * (m - 1.0f)) + 1.0f;
			numbersSortedArray[i] = temp;
			numbersGeneratedArray[i] = temp;
			numbersGeneratedList.add(temp);
			numbersSortedList.add(temp);
		}
		
		// Set number of values to be printed
		int nPrint = 0;
		if (n >= 100) {
			nPrint = 100;
		} else {
			nPrint = n;
		}
		

		// Print results
		System.out.println("\n****************");
		System.out.println("*    Arrays    *");
		System.out.println("****************");
		
		System.out.print("\nInitial Values: \t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersGeneratedArray[i]);
		}
		
		as.bubbleSort(numbersSortedArray);
		System.out.print("\nAfter Bubble-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		for (int i = 0; i < n; i++) {
			numbersSortedArray[i] = numbersGeneratedArray[i];
		}
		
		as.mergeSort(numbersSortedArray, 0, n - 1);
		System.out.print("\nAfter Merge-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		for (int i = 0; i < n; i++) {
			numbersSortedArray[i] = numbersGeneratedArray[i];
		}
		
		as.quickSort(numbersSortedArray, 0, n - 1);
		System.out.print("\nAfter Quick-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		for (int i = 0; i < n; i++) {
			numbersSortedArray[i] = numbersGeneratedArray[i];
		}
		
		as.insertionSort(numbersSortedArray);
		System.out.print("\nAfter Insertion-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		for (int i = 0; i < n; i++) {
			numbersSortedArray[i] = numbersGeneratedArray[i];
		}
		
		Arrays.sort(numbersSortedArray);
		System.out.print("\nAfter Built-In-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		for (int i = 0; i < n; i++) {
			numbersSortedArray[i] = numbersGeneratedArray[i];
		}
		
		as.selectionSort(numbersSortedArray);
		System.out.print("\nAfter Selection-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedArray[i]);
		}
		
		
		
		
		System.out.println("\n\n\n****************");
		System.out.println("* Linked Lists *");
		System.out.println("****************");
		
		System.out.print("\nInitial Values: \t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersGeneratedList.get(i));
		}
		
		lls.bubbleSort(numbersSortedList);
		System.out.print("\nAfter Bubble-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		for (int i = 0; i < n; i++) {
			numbersSortedList.set(i, numbersGeneratedList.get(i));
		}
		
		lls.mergeSort(numbersSortedList, 0, n - 1);
		System.out.print("\nAfter Merge-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		for (int i = 0; i < n; i++) {
			numbersSortedList.set(i, numbersGeneratedList.get(i));
		}
		
		lls.quickSort(numbersSortedList, 0, n - 1);
		System.out.print("\nAfter Quick-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		for (int i = 0; i < n; i++) {
			numbersSortedList.set(i, numbersGeneratedList.get(i));
		}
		
		lls.insertionSort(numbersSortedList);
		System.out.print("\nAfter Insertion-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		for (int i = 0; i < n; i++) {
			numbersSortedList.set(i, numbersGeneratedList.get(i));
		}
		
		Collections.sort(numbersSortedList);
		System.out.print("\nAfter Built-In-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		for (int i = 0; i < n; i++) {
			numbersSortedList.set(i, numbersGeneratedList.get(i));
		}
		
		lls.selectionSort(numbersSortedList);
		System.out.print("\nAfter Selection-Sort:\t");
		for (int i = 0; i < nPrint; i++) {
			System.out.printf("%.3f, ", numbersSortedList.get(i));
		}
		
		System.out.println("\n\n\n\nMy answers are:");
		System.out.println("a. Arrays");
		System.out.println("b. Linked Lists");
		System.out.println("c. Arrays (since they allow you store more data in less space compared to linked lists)");
		
		// Close Scanner object
		kbrd.close();
	}

}
