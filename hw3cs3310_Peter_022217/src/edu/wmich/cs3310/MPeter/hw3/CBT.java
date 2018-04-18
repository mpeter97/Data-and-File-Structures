package edu.wmich.cs3310.MPeter.hw3;

/**
 * This class stores an array-based complete binary tree (CBT). 
 * Methods of findMin, deleteMin, and insert are used to read and 
 * alter the tree.
 * 
 * @author Matt Peter
 */
public class CBT implements ICBT {
	
	// Attributes
	private int[][] binTree;	// Stores the CBT
	private int numVals = 0;	// Keeps track of the number of values in the CBT
	
	/**
	 * This constructor instantiates the array and assigns 
	 * default values.
	 */
	public CBT() {
		binTree = new int[1][2];
		binTree[0][0] = 20001;
		binTree[0][1] = 20001;
	}
	
	/**
	 * This method returns the lowest key/value pair in the CBT, based 
	 * on the key (returns (-1, -1) if the array is empty).
	 * 
	 * @return Lowest key/value pair in CBT
	 */
	public int[] findMin() {
		if (numVals > 0) {
			return binTree[0];
		} else {
			int[] empty = {-1, -1};
			return empty;
		}
	}	
	
	/**
	 * This method removes the lowest key/value pair from the CBT.
	 */
	public void deleteMin() {
		if (numVals > 0) {
			// Decrement number of values in array
			numVals--;
			
			// Adjust size of binary tree if necessary
			if (numVals == binTree.length / 4 && numVals > 0) {
				int[][] temp = new int[numVals * 2][2];
				for (int i = 0; i < temp.length; i++) {
					for (int j = 0; j <= 1; j++) {
						temp[i][j] = binTree[i][j];
					}
				}
				binTree = new int[numVals * 2][2];
				for (int i = 0; i < temp.length; i++) {
					for (int j = 0; j <= 1; j++) {
						binTree[i][j] = temp[i][j];
					}
				}
			}
			
			// Declare integer to keep track of location in array
			int location = 0;
			
			// Move last value in array to root
			binTree[0][0] = binTree[numVals][0];
			binTree[0][1] = binTree[numVals][1];
			
			// Set last position in array to "empty" values
			binTree[numVals][0] = 20001;
			binTree[numVals][1] = 20001;
			
			// Heapify the tree
			while (((location * 2) + 1) < numVals) {
				if (binTree[location][0] > binTree[(location * 2) + 1][0]) {
					if (binTree[location][0] > binTree[(location * 2) + 2][0]) {
						if (binTree[(location * 2) + 1][0] > binTree[(location * 2) + 2][0]) {
							int[] temp = {binTree[location][0], binTree[location][1]};
							binTree[location][0] = binTree[(location * 2) + 2][0];
							binTree[location][1] = binTree[(location * 2) + 2][1];
							binTree[(location * 2) + 2][0] = temp[0];
							binTree[(location * 2) + 2][1] = temp[1];
							location = (location * 2) + 2;
						} else {
							int[] temp = {binTree[location][0], binTree[location][1]};
							binTree[location][0] = binTree[(location * 2) + 1][0];
							binTree[location][1] = binTree[(location * 2) + 1][1];
							binTree[(location * 2) + 1][0] = temp[0];
							binTree[(location * 2) + 1][1] = temp[1];
							location = (location * 2) + 1;
						}
					} else {
						int[] temp = {binTree[location][0], binTree[location][1]};
						binTree[location][0] = binTree[(location * 2) + 1][0];
						binTree[location][1] = binTree[(location * 2) + 1][1];
						binTree[(location * 2) + 1][0] = temp[0];
						binTree[(location * 2) + 1][1] = temp[1];
						location = (location * 2) + 1;
					}
				} else if (binTree[location][0] > binTree[(location * 2) + 2][0]) {
					int[] temp = {binTree[location][0], binTree[location][1]};
					binTree[location][0] = binTree[(location * 2) + 2][0];
					binTree[location][1] = binTree[(location * 2) + 2][1];
					binTree[(location * 2) + 2][0] = temp[0];
					binTree[(location * 2) + 2][1] = temp[1];
					location = (location * 2) + 2;
				} else {
					break;
				}
			}
		}
	}
	
	/**
	 * This method inserts the passed key/value pair into the CBT.
	 * 
	 * @param key Integer in range [1, 1000]
	 * @param value Integer in range [5000, 20000]
	 */
	public void insert(int key, int value) {
		// Adjust size of binary tree if necessary
		if (numVals == binTree.length) {
			int[][] temp = new int[numVals][2];
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j <= 1; j++) {
					temp[i][j] = binTree[i][j];
				}
			}
			binTree = new int[numVals * 2][2];
			for (int i = 0; i < temp.length; i++) {
				for (int j = 0; j <= 1; j++) {
					binTree[i][j] = temp[i][j];
				}
			}
			for (int i = temp.length; i < binTree.length; i++) {
				for (int j = 0; j <= 1; j++) {
					binTree[i][j] = 20001;
				}
			}
		}
		
		// Declare integer to keep track of location in array
		int location = numVals;
		
		// Assign passed key/value to first open position in array
		binTree[location][0] = key;
		binTree[location][1] = value;
		
		// Heapify the tree
		while (location != 0) {
			if (binTree[location][0] < binTree[(int)((location - 1) / 2.0f)][0]) {
				int[] temp = {binTree[location][0], binTree[location][1]};
				binTree[location][0] = binTree[(int)((location - 1) / 2.0f)][0];
				binTree[location][1] = binTree[(int)((location - 1) / 2.0f)][1];
				binTree[(int)((location - 1) / 2.0f)][0] = temp[0];
				binTree[(int)((location - 1) / 2.0f)][1] = temp[1];
				location = (int)((location - 1) / 2.0f);
			} else {
				break;
			}
		}
		
		// Increment number of values in array
		numVals++;
	}
	
	/**
	 * This method returns the entire CBT in ordered format (left to right 
	 * on each layer of the binary tree from top to bottom).
	 * 
	 * @return CBT in ordered format
	 */
	public int[][] getCBT() {
		int[][] tempBinTree = new int[31][2];
		for (int i = 0; i < 31; i++) {
			for (int j = 0; j <= 1; j++) {
				tempBinTree[i][j] = binTree[i][j];
			}
		}
		return tempBinTree;
	}
}
