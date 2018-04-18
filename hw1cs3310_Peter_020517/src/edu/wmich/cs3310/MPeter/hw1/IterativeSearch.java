package edu.wmich.cs3310.MPeter.hw1;

/**
 * 
 * This class contains several methods that are used to search
 * for values in the array using iterative implementations (loops).
 * 
 * @author Matt Peter
 *
 */
public class IterativeSearch {
	/**
	 * This method uses a iterative binary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into halves until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int binarySearch(float[] nums, float searchVal) {
		// Declare and Instantiate variables to hold the first, last, and middle positions of the array
		int first = 0;
		int last = nums.length - 1;
		int middle = 0;
		
		// Calculate the position of the middle of the array
		// Check if the value is at the calculated location
		// Otherwise, adjust first or last accordingly
		while (first <= last) {
			middle = (int)((first + last) / 2.0f);
			if (searchVal == nums[middle]) {
				return middle;
			} else if (searchVal < nums[middle]) {
				last = middle - 1;
			} else {
				first = middle + 1;
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses a iterative ternary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into thirds until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int ternarySearch(float[] nums, float searchVal) {
		// Declare and Instantiate variables to hold the first, last, and two middle positions of the array
		int first = 0;
		int last = nums.length - 1;
		int firstMid = 0;
		int secondMid = 0;
		
		// Calculate the 1/3 and 2/3 positions of the array
		// Check if the value is at one of the calculated locations
		// Otherwise, adjust first and/or last accordingly
		while (first <= last) {
			firstMid = (int)((first * 2.0f + last) / 3.0f);
			secondMid = (int)((first + last * 2.0f) / 3.0f);
			if (searchVal == nums[firstMid]) {
				return firstMid;
			} else if (searchVal == nums[secondMid]) {
				return secondMid;
			} else if (searchVal < nums[firstMid]) {
				last = firstMid - 1;
			} else if (searchVal > nums[secondMid]) {
				first = secondMid + 1;
			} else {
				first = firstMid + 1;
				last = secondMid - 1;
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses a iterative quad search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into fourths until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int quadSearch(float[] nums, float searchVal) {
		// Declare and Instantiate variables to hold the first, last, and three middle positions of the array
		int first = 0;
		int last = nums.length - 1;
		int firstMid = 0;
		int secondMid = 0;
		int thirdMid = 0;
		
		// Calculate the 1/4, 1/2, and 3/4 positions of the array
		// Check if the value is at one of the calculated locations
		// Otherwise, adjust first and/or last accordingly
		while (first <= last) {
			firstMid = (int)((first * 3.0f + last) / 4.0f);
			secondMid = (int)((first + last) / 2.0f);
			thirdMid = (int)((first + last * 3.0f) / 4.0f);
			if (searchVal == nums[firstMid]) {
				return firstMid;
			} else if (searchVal == nums[secondMid]) {
				return secondMid;
			} else if (searchVal == nums[thirdMid]) {
				return thirdMid;
			} else if (searchVal < nums[firstMid]) {
				last = firstMid - 1;
			} else if (searchVal > nums[thirdMid]) {
				first = thirdMid + 1;
			} else if (searchVal > nums[firstMid] && searchVal < nums[secondMid]) {
				first = firstMid + 1;
				last = secondMid - 1;
			} else {
				first = secondMid + 1;
				last = thirdMid - 1;
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses a binary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into halves until
	 * the value is found.  If the value exists, the position of that value is
	 * stored as a bound.  Otherwise, the position of the closest value greater
	 * than (lower bound) or less than (upper bound) is stored instead.  The
	 * number of values within the bounds (inclusive) is then calculated and returned.
	 * @param nums Array of floating point values
	 * @param searchVal1 Lower bound value being searched for in array
	 * @param searchVal2 Upper bound value being searched for in array
	 * @return Number of values within bounds of searchVal1 and searchVal2 (inclusive)
	 */
	public int rangeQuery(float[] nums, float searchVal1, float searchVal2) {
		// Declare and Instantiate variables to hold the first, last, and middle positions of the array
		//   as well as the upper and lower bounds
		int lowerBound = 0;
		int upperBound = 0;
		int first = 0;
		int last = nums.length - 1;
		int middle = 0;
		
		// Calculate the position of the middle of the array
		// Check if the value is at the calculated location
		// Otherwise, adjust first or last accordingly
		while (first <= last) {
			middle = (int)((first + last) / 2.0f);
			if (searchVal1 == nums[middle]) {
				lowerBound = middle;
				break;
			} else if (searchVal1 < nums[middle]) {
				last = middle - 1;
			} else {
				first = middle + 1;
			}
		}
		// If value does not exist, set the lower bound to first
		if (first > last) {
			lowerBound = first;
		}
		
		// Reset the values of first and last
		first = 0;
		last = nums.length - 1;
		
		// Calculate the position of the middle of the array
		// Check if the value is at the calculated location
		// Otherwise, adjust first or last accordingly
		while (first <= last) {
			middle = (int)((first + last) / 2.0f);
			if (searchVal2 == nums[middle]) {
				upperBound = middle;
				break;
			} else if (searchVal2 < nums[middle]) {
				last = middle - 1;
			} else {
				first = middle + 1;
			}
		}
		// If value does not exist, set upper bound to last
		if (first > last) {
			upperBound = last;
		}
		
		// Calculate and return the number of values within the bounds (inclusive)
		return (upperBound - lowerBound + 1);
	}
}
