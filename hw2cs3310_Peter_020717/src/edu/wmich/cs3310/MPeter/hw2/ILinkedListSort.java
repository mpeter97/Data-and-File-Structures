package edu.wmich.cs3310.MPeter.hw2;

import java.util.*;

/**
 * This interface contains all of the different sorting
 * methods to be implemented for sorting linked lists.
 *
 * @param <T> Type of linked list to be sorted
 */
public interface ILinkedListSort<T extends Comparable<T>> {
	public void bubbleSort(LinkedList<T> values);
	public void mergeSort(LinkedList<T> values, int first, int last);
	public void quickSort(LinkedList<T> values, int first, int last);
	public void insertionSort(LinkedList<T> values);
	public void selectionSort(LinkedList<T> values);
}
