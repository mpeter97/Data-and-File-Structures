package edu.wmich.cs3310.MPeter.hw3;

/**
 * This interface contains all of the methods to be used with
 * the size-balanced binary tree (SBT).
 */
public interface ISBT {
	public int[] findMin();
	public void deleteMin();
	public void insert(int key, int value);
	public int[][] getSBT(String type);
}
