package edu.wmich.cs3310.MPeter.hw4;

import java.util.*;

/**
 * AUTHOR: Matt Peter
 * CLASS: CS 3310
 * DATE: 4/2/17
 * I give permission to the instructor to share my solution(s) with the class
 * EXTRA CREDIT HEAP ON COUNTER VALUES IS USED
 * This program creates a binary search tree, performs and prints several types
 * of operations on it, and prints the final tree in preorder, postorder, and
 * inorder format.
 * 
 * @author Matt Peter
 */
public class Hw4Main {
	/**
	 * This method runs 20 experiments involving binary search trees, performing
	 * a variety of operations in the process.  It starts by randomly generating
	 * 100 values in the range of [1, 20] and inserting them all in the tree.  This
	 * is followed by it finding the largest and smallest counter values and deleting
	 * their respective nodes.  Another node is then inserted that causes the sum of
	 * the key values and the sum of the count values to be equal.  Finally, after
	 * the final experiment is finished, the maximum and minimum heights out of all
	 * the trees that were generated is printed as well as the nodes in the final tree
	 * in preorder, postorder, and inorder format.
	 * @param args Stuff entered by the user in the console
	 */
	public static void main(String[] args) {
		// Declare and Instantiate a Random object
		Random rand = new Random();
		
		// Declare and Instantiate minimum and maximum heights
		int minHeight = 19;
		int maxHeight = 0;
		
		// Enter a for loop that will run 20 experiments
		for (int i = 1; i <= 20; i++) {
			// Declare and Instantiate BST and Heap objects
			BST bst = new BST();
			Heap maxHeap = new Heap();
			Heap minHeap = new Heap();
			
			// Generate 100 values in range [1, 20] and insert them
			for (int j = 1; j <= 100; j++) {
				int val = rand.nextInt(20) + 1;
				bst.insert(val);
				maxHeap.insertMaxHeap(val);
				minHeap.insertMinHeap(val);
				bst.updateTree();
			}
			// Print the nodes with the largest and smallest counter values
			System.out.println("BST " + i);
			System.out.println("-------");
			System.out.println("Largest Counter Value: Key Value: " + maxHeap.getRootKeyValue() + "   Counter Value: " + maxHeap.getRootCounterValue());
			System.out.println("Smallest Counter Value: Key Value: " + minHeap.getRootKeyValue() + "   Counter Value: " + minHeap.getRootCounterValue());
			
			// Delete the nodes with the largest and smallest counter values
			bst.delete(maxHeap.getRootKeyValue());
			bst.updateTree();
			bst.delete(minHeap.getRootKeyValue());
			bst.updateTree();
			
			// Find the sums of the keys and counters and insert a node that makes these equal
			int sumOfKeys = bst.getSumKeys();
			int sumOfCounts = bst.getSumCounts();
			int equalKey = rand.nextInt(10) + 21;
			int equalCount = (sumOfKeys + equalKey - sumOfCounts);
			System.out.println("Equalizing Key and Count: Key: " + equalKey + "   Count: " + equalCount);
			for (int j = 1; j <= equalCount; j++) {
				bst.insert(equalKey);
				bst.updateTree();
			}
			
			// Find the 5th, 20th, 30th, and 70th smallest keys
			bst.findKeys();
			System.out.println("\n\n");
			
			// Get the height of the tree and see if it sets a new record for the maximum or minimum tree height
			int height = bst.getHeight();
			if (height > maxHeight) {
				maxHeight = height;
			}
			if (height < minHeight) {
				minHeight = height;
			}
			
			// If this is the final tree, print the maximum and minimum heights and traverse 
			//   this last tree in preorder, postorder, and inorder formats
			if (i == 20) {
				System.out.println("Minimum Height: " + minHeight);
				System.out.println("Maximum Height: " + maxHeight);
				bst.printOrders();
			}
		}
		
	}

}
