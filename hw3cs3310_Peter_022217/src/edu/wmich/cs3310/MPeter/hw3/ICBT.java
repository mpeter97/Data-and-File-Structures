package edu.wmich.cs3310.MPeter.hw3;

/**
 * This interface contains all of the methods to be used with
 * the complete binary tree (CBT).
 */
public interface ICBT {
	public int[] findMin();
	public void deleteMin();
	public void insert(int key, int value);
	public int[][] getCBT();
}
