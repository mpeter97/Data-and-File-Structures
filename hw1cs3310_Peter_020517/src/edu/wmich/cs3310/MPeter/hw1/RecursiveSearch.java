package edu.wmich.cs3310.MPeter.hw1;

/**
 * 
 * This class contains several methods that are used to search
 * for values in the array using recursive implementations.
 * 
 * @author Matt Peter
 *
 */
public class RecursiveSearch {
	
	/**
	 * This method uses a recursive binary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into halves until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @param first Position of first value in subset of values in array
	 * @param last Position of last value in subset of values in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int binarySearch(float[] nums, float searchVal, int first, int last) {
		// Calculate the position of the middle of the array
		int middle = (int)((first + last) / 2.0f);
		
		// Check if the value is at the calculated location
		// Otherwise, recursively call this method with new boundaries
		if (first <= last) {
			if (searchVal == nums[middle]) {
				return middle;
			} else if (searchVal < nums[middle]) {
				return binarySearch(nums, searchVal, first, middle - 1);
			} else {
				return binarySearch(nums, searchVal, middle + 1, last);
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses a recursive ternary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into thirds until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @param first Position of first value in subset of values in array
	 * @param last Position of last value in subset of values in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int ternarySearch(float[] nums, float searchVal, int first, int last) {
		// Calculate the positions of the 1/3 and 2/3 positions in the array
		int firstMid = (int)((first * 2.0f + last) / 3.0f);
		int secondMid = (int)((first + last * 2.0f) / 3.0f);
		
		// Check if the value is at one of the calculated locations
		// Otherwise, recursively call this method with new boundaries
		if (first <= last) {
			if (searchVal == nums[firstMid]) {
				return firstMid;
			} else if (searchVal == nums[secondMid]) {
				return secondMid;
			} else if (searchVal < nums[firstMid]) {
				return ternarySearch(nums, searchVal, first, firstMid - 1);
			} else if (searchVal > nums[secondMid]) {
				return ternarySearch(nums, searchVal, secondMid + 1, last);
			} else {
				return ternarySearch(nums, searchVal, firstMid + 1, secondMid - 1);
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses a recursive quad search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into fourths until
	 * the value is found.  If the value doesn't exist in the array, the length
	 * of the array is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @param first Position of first value in subset of values in array
	 * @param last Position of last value in subset of values in array
	 * @return Position of searchVal in array if found; Size of array if not
	 */
	public int quadSearch(float[] nums, float searchVal, int first, int last) {
		// Calculate the positions of the 1/4, 1/2, and 3/4 positions in the array
		int firstMid = (int)((first * 3.0f + last) / 4.0f);
		int secondMid = (int)((first + last) / 2.0f);
		int thirdMid = (int)((first + last * 3.0f) / 4.0f);
		
		// Check if the value is at one of the calculated locations
		// Otherwise, recursively call this method with new boundaries
		if (first <= last) {
			if (searchVal == nums[firstMid]) {
				return firstMid;
			} else if (searchVal == nums[secondMid]) {
				return secondMid;
			} else if (searchVal == nums[thirdMid]) {
				return thirdMid;
			} else if (searchVal < nums[firstMid]) {
				return quadSearch(nums, searchVal, first, firstMid - 1);
			} else if (searchVal > nums[thirdMid]) {
				return quadSearch(nums, searchVal, thirdMid + 1, last);
			} else if (searchVal > nums[firstMid] && searchVal < nums[secondMid]) {
				return quadSearch(nums, searchVal, firstMid + 1, secondMid - 1);
			} else {
				return quadSearch(nums, searchVal, secondMid + 1, thirdMid - 1);
			}
		}
		
		return nums.length;
	}
	
	/**
	 * This method uses the rangeQueryFindBound method to find the locations
	 * in the array of the searchVal1 and searchVal2 arguments.  It then returns
	 * the number of values that are within these bounds (inclusive).
	 * @param nums Array of floating point values
	 * @param searchVal1 Lower bound value being searched for in array
	 * @param searchVal2 Upper bound value being searched for in array
	 * @param first Position of first value in subset of values in array
	 * @param last Position of last value in subset of values in array
	 * @return Number of values within bounds of searchVal1 and searchVal2 (inclusive)
	 */
	public int rangeQuery(float[] nums, float searchVal1, float searchVal2, int first, int last) {
		int lowerBound = rangeQueryFindBound(nums, searchVal1, first, last, true);
		int upperBound = rangeQueryFindBound(nums, searchVal2, first, last, false);
		return (upperBound - lowerBound + 1);
	}
	
	/**
	 * This method uses a binary search format to see if a specified value
	 * exists in an array, repeatedly splitting the array into halves until
	 * the value is found.  If the value exists, the position of that value is
	 * returned.  Otherwise, the position of the closest value greater than
	 * (lower bound) or less than (upper bound) is returned instead.
	 * @param nums Array of floating point values
	 * @param searchVal Value being searched for in array
	 * @param first Position of first value in subset of values in array
	 * @param last Position of last value in subset of values in array
	 * @param findingLower Searching for lower bound if true; Upper bound if false
	 * @return Position of bounding value in array
	 */
	private int rangeQueryFindBound(float[] nums, float searchVal, int first, int last, boolean findingLower) {
		// Calculate the position of the middle of the array
		int middle = (int)((first + last) / 2.0f);
		
		// Check if the value is at the calculated location
		// Otherwise, recursively call this method with new boundaries
		if (first <= last) {
			if (searchVal == nums[middle]) {
				return middle;
			} else if (searchVal < nums[middle]) {
				return rangeQueryFindBound(nums, searchVal, first, middle - 1, findingLower);
			} else {
				return rangeQueryFindBound(nums, searchVal, middle + 1, last, findingLower);
			}
		// If value does not exist, return respective boundary position
		} else {
			if (findingLower) {
				return first;
			}
		}
		return last;
	}
}
