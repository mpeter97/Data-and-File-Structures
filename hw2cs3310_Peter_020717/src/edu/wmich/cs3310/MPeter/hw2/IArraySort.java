package edu.wmich.cs3310.MPeter.hw2;

/**
 * This interface contains all of the different sorting
 * methods to be implemented for sorting arrays.
 *
 * @param <T> Type of array to be sorted
 */
public interface IArraySort<T extends Comparable<T>> {
	public void bubbleSort(T[] values);
	public void mergeSort(T[] values, int first, int last);
	public void quickSort(T[] values, int first, int last);
	public void insertionSort(T[] values);
	public void selectionSort(T[] values);
}
