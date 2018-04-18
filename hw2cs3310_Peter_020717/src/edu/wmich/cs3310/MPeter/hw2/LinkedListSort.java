package edu.wmich.cs3310.MPeter.hw2;

import java.util.*;

/**
 * This class is used to sort linked lists with a variety of
 * sorting methods, including: Bubble-Sort, Merge-Sort, Quick-Sort,
 * Insertion-Sort, Built-In-Sort, and Selection-Sort.
 *
 * @param <T> Type of linked list to be sorted
 */
public class LinkedListSort<T extends Comparable<T>> implements ILinkedListSort<T> {
	/**
	 * This method sorts a passed linked list using a bubble-sort
	 * sorting method.
	 * @param values Linked list of values of type T
	 */
	public void bubbleSort(LinkedList<T> values) {
		for (int i = (values.size() - 1); i > 0; i--) {
			for (int j = 0; j < i; j++) {
				// If one value is greater than the next, swap them
				if (values.get(j).compareTo(values.get(j + 1)) > 0) {
					T temp = values.get(j);
					values.set(j, values.get(j + 1));
					values.set(j + 1, temp);
				}
			}
		}
	}
	
	/**
	 * This method sorts a passed linked list using a merge-sort
	 * sorting method.
	 * @param values Linked list of values of type T
	 * @param first Position of first value being sorted
	 * @param last Position of last value being sorted
	 */
	@SuppressWarnings("unchecked")
	public void mergeSort(LinkedList<T> values, int first, int last) {
		// If more than one value exists in this section of the linked list...
		if (first < last) {
			int middle = (first + last) / 2;
			
			// Recursively call mergeSort for first and second half
			mergeSort(values, first, middle);
			mergeSort(values, (middle + 1), last);
			
			int leftPos = first;
			int rightPos = middle + 1;
			
			// Put values in a temporary linked list in the correct order
			LinkedList<Comparable<T>> tempList = new LinkedList<Comparable<T>>();
			while ((leftPos <= middle) && (rightPos <= last)) {
				if (values.get(leftPos).compareTo(values.get(rightPos)) < 0) {
					tempList.add(values.get(leftPos++));
				} else {
					tempList.add(values.get(rightPos++));
				}
			}
			
			while (leftPos <= middle) {
				tempList.add(values.get(leftPos++));
			}
			
			while (rightPos <= last) {
				tempList.add(values.get(rightPos++));
			}
			
			// Transfer sorted values back to original linked list
			for (int i = first, j = 0; i <= last; i++, j++) {
				values.set(i, (T)tempList.get(j));
			}
		}
	}
	
	/**
	 * This method sorts a passed linked list using a quick-sort
	 * sorting method.
	 * @param values Linked list of values of type T
	 * @param first Position of first value being sorted
	 * @param last Position of last value being sorted
	 */
	public void quickSort(LinkedList<T> values, int first, int last) {
		int lower = first;
		int upper = last;
		
		T pivot = values.get((first + last) / 2);
		// Find two values that are on wrong sides of pivot and swap them
		while (lower <= upper) {
			while (values.get(lower).compareTo(pivot) < 0) {
				lower++;
			}
			
			while (values.get(upper).compareTo(pivot) > 0) {
				upper--;
			}
			
			if (lower <= upper) {
				T temp = values.get(lower);
				values.set(lower, values.get(upper));
				values.set(upper, temp);
				lower++;
				upper--;
			}
		}
		
		// Recursively call quickSort for sections above and below pivot value
		if (first < upper) {
			quickSort(values, first, upper);
		}
		if (lower < last) {
			quickSort(values, lower, last);
		}
	}
	
	/**
	 * This method sorts a passed linked list using an insertion-sort
	 * sorting method.
	 * @param values Linked list of values of type T
	 */
	public void insertionSort(LinkedList<T> values) {
		for (int i = 1; i < values.size(); i++) {
			for (int j = i; j > 0; j--) {
				// If one value is less than the previous, swap them
				if (values.get(j).compareTo(values.get(j - 1)) < 0) {
					T temp = values.get(j);
					values.set(j, values.get(j - 1));
					values.set(j - 1, temp);
				} else {
				// Break once correct position for value is found
					break;
				}
			}
		}
	}
	
	/**
	 * This method sorts a passed linked list using a selection-sort
	 * sorting method.
	 * @param values Linked list of values of type T
	 */
	public void selectionSort(LinkedList<T> values) {
		for (int i = 0; i < (values.size() - 1); i++) {
			T lowest = values.get(i);
			int lowestPos = i;
			for (int j = i; j < values.size(); j++) {
				// If lower value than current lowest is found, replace it
				if (values.get(j).compareTo(lowest) < 0) {
					lowest = values.get(j);
					lowestPos = j;
				}
			}
			// Put the lowest value found in its proper position
			T temp = values.get(i);
			values.set(i, lowest);
			values.set(lowestPos, temp);
		}
	}
}
