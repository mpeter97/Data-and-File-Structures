package edu.wmich.cs3310.MPeter.hw5;

import java.io.*;

/**
 * This class contains methods that sort a binary file using
 * quicksort by records (4 bytes) and quicksort by blocks
 * (4096 bytes) and generates statistics from the sorting processes.
 * 
 * @author Matt Peter
 */
public class QuickSort {
	
	// Attributes
	private short[][] dataBlocks;		// Holds blocks of data currently in the buffer pool
	private int[][] bufferPoolLRU;		// Orders the data based on Least Recently Used
	private int bufferPoolSize;			// Size of buffer pool
	private int numRecords;				// Total number of records
	private int cacheHits = 0;			// Tracks cache hits
	private int recordsRead = 0;		// Tracks number of individual records read
	private int recordsWritten = 0;		// Tracks number of individual records written
	private int blocksRead = 0;			// Tracks number of blocks read
	private int blocksWritten = 0;		// Tracks number of blocks written
	
	/**
	 * This constructor assigns passed values to attributes and assigns values
	 * to bufferPoolLRU array.
	 * @param f RandomAccessFile object being used to access binary files
	 * @param n Number of records in file
	 * @param s Number of buffers in buffer pool
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public QuickSort(RandomAccessFile f, int n, int s) throws IOException {
		numRecords = n;
		bufferPoolSize = s;
		dataBlocks = new short[s][2048];
		bufferPoolLRU = new int[s][2];
		for (int i = 0; i < s; i++) {
			bufferPoolLRU[i][0] = -1;
			bufferPoolLRU[i][1] = (s - i) - 1;
		}
	}
	
	/**
	 * This method quicksorts a binary file of records by accessing only
	 * the individual records (4 bytes each).
	 * @param f RandomAccessFile object being used to access binary files
	 * @param first Position of first record in section being sorted
	 * @param last Position of last record in section being sorted
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public void quickSortRecords(RandomAccessFile f, int first, int last) throws IOException {
		// Find the lower, upper, and pivot keys
		f.seek(first * 4);
		short lowerKey = f.readShort();
		int lowerPos = first;
		f.seek(last * 4);
		short upperKey = f.readShort();
		int upperPos = last;
		f.seek(((int)((first + last) / 2)) * 4);
		short pivotKey = f.readShort();
		recordsRead += 3;
		
		// Find two values that are on wrong sides of pivot and swap them
		while (lowerPos <= upperPos) {
			while (lowerKey < pivotKey) {
				lowerPos++;
				f.seek(lowerPos * 4);
				lowerKey = f.readShort();
				recordsRead++;
			}
			
			while (upperKey > pivotKey) {
				upperPos--;
				f.seek(upperPos * 4);
				upperKey = f.readShort();
				recordsRead++;
			}

			if (lowerPos <= upperPos) {
				f.seek((lowerPos * 4) + 2);
				short lowerVal = f.readShort();
				f.seek((upperPos * 4) + 2);
				short upperVal = f.readShort();
				f.seek(lowerPos * 4);
				f.writeShort(upperKey);
				f.seek((lowerPos * 4) + 2);
				f.writeShort(upperVal);
				f.seek(upperPos * 4);
				f.writeShort(lowerKey);
				f.seek((upperPos * 4) + 2);
				f.writeShort(lowerVal);
				recordsRead += 2;
				recordsWritten += 4;
				lowerPos++;
				if (lowerPos <= last) {
					f.seek(lowerPos * 4);
					lowerKey = f.readShort();
					recordsRead++;
				}
				upperPos--;
				if (upperPos >= first) {
					f.seek(upperPos * 4);
					upperKey = f.readShort();
					recordsRead++;
				}
			}
		}
		
		// Recursively call quickSort for sections above and below pivot value
		if (first < upperPos) {
			quickSortRecords(f, first, upperPos);
		}
		
		if (lowerPos < last) {
			quickSortRecords(f, lowerPos, last);
		}
	}
	
	/**
	 * This method quicksorts a binary file of records by accessing blocks
	 * of records (4096 bytes each).
	 * @param f RandomAccessFile object being used to access binary files
	 * @param first Position of first record in section being sorted
	 * @param last Position of last record in section being sorted
	 * @param level Tracks when the block should be written back to the binary file
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public void quickSortBlocks(RandomAccessFile f, int first, int last, int level) throws IOException {
		// Find the upper, lower, and pivot keys
		int firstBlock = (int)(first / 1024);
		int lastBlock = (int)(last / 1024);
		getBlock(f, firstBlock);
		short lowerKey = dataBlocks[bufferPoolLRU[0][1]][(first % 1024) * 2];
		int lowerPos = first % 1024;
		getBlock(f, lastBlock);
		short upperKey = dataBlocks[bufferPoolLRU[0][1]][(last % 1024) * 2];
		int upperPos = last % 1024;
		getBlock(f, (int)((first + last) / 2048));
		short pivotKey = dataBlocks[bufferPoolLRU[0][1]][((int)(((first + last) / 2) % 1024)) * 2];

		// Find two key/value pairs that are on wrong sides of pivot and swap them
		while ((lowerPos + (firstBlock * 1024)) <= (upperPos + (lastBlock * 1024))) {
			// Find a lower key to swap
			getBlock(f, firstBlock);
			while (lowerKey < pivotKey) {
				lowerPos++;
				if (lowerPos == 1024) {
					getBlock(f, ++firstBlock);
					lowerPos = 0;
				}
				lowerKey = dataBlocks[bufferPoolLRU[0][1]][lowerPos * 2];
			}
			// Find an upper key to swap
			getBlock(f, lastBlock);
			while (upperKey > pivotKey) {
				upperPos--;
				if (upperPos == -1) {
					getBlock(f, --lastBlock);
					upperPos = 1023;
				}
				upperKey = dataBlocks[bufferPoolLRU[0][1]][upperPos * 2];
			}
			// Swap the key/value pairs
			if ((lowerPos + (firstBlock * 1024)) <= (upperPos + (lastBlock * 1024))) {
				getBlock(f, firstBlock);
				short lowerVal = dataBlocks[bufferPoolLRU[0][1]][(lowerPos * 2) + 1];
				getBlock(f, lastBlock);
				short upperVal = dataBlocks[bufferPoolLRU[0][1]][(upperPos * 2) + 1];
				getBlock(f, firstBlock);
				dataBlocks[bufferPoolLRU[0][1]][lowerPos * 2] = upperKey;
				dataBlocks[bufferPoolLRU[0][1]][(lowerPos * 2) + 1] = upperVal;
				getBlock(f, lastBlock);
				dataBlocks[bufferPoolLRU[0][1]][upperPos * 2] = lowerKey;
				dataBlocks[bufferPoolLRU[0][1]][(upperPos * 2) + 1] = lowerVal;
				getBlock(f, firstBlock);
				lowerPos++;
				if ((lowerPos + (firstBlock * 1024)) <= last) {
					if (lowerPos == 1024) {
						getBlock(f, ++firstBlock);
						lowerPos = 0;
					}
					lowerKey = dataBlocks[bufferPoolLRU[0][1]][lowerPos * 2];
				}
				getBlock(f, lastBlock);
				upperPos--;
				if ((upperPos + (lastBlock * 1024)) >= first) {
					if (upperPos == -1) {
						getBlock(f, --lastBlock);
						upperPos = 1023;
					}
					upperKey = dataBlocks[bufferPoolLRU[0][1]][upperPos * 2];
				}
			}
		}
		
		// Recursively call quickSort for sections above and below pivot value
		if (first < (upperPos + (lastBlock * 1024))) {
			quickSortBlocks(f, first, (upperPos + (lastBlock * 1024)), level + 1);
		}
		
		if ((lowerPos + (firstBlock * 1024)) < last) {
			quickSortBlocks(f, (lowerPos + (firstBlock * 1024)), last, level + 1);
		}
		
		// Write the blocks back to the binary file
		if (level == 0) {
			for (int i = 0; i < bufferPoolSize; i++) {
				if (bufferPoolLRU[i][0] != -1) {
					for (int j = bufferPoolLRU[i][0] * 4096, k = 0; (j < (bufferPoolLRU[i][0] + 1) * 4096) && (j < numRecords * 4); j += 2, k++) {
						f.seek(j);
						f.writeShort(dataBlocks[bufferPoolLRU[i][1]][k]);
					}
					blocksWritten++;
				}
			}
		}
	}
	
	/**
	 * This method reads the requested block of data from the binary
	 * file and stores it in an array.  If a block is already in the buffer
	 * pool, it is not read again.  Otherwise, it is read into the buffer pool
	 * and data already in the buffer pool is removed if necessary.
	 * @param f RandomAccessFile object being used to access binary files
	 * @param blockNum Number associated with block being requested
	 * @throws IOException Handles Input/Output Exceptions
	 */
	private void getBlock(RandomAccessFile f, int blockNum) throws IOException {
		// Stores the position of the data in the buffer pool (-1 if nonexistant)
		int position = -1;
		
		// Checks if the block already exists in the buffer pool
		for (int i = 0; i < bufferPoolSize; i++) {
			if (bufferPoolLRU[i][0] == blockNum) {
				position = i;
			}
		}
		
		// If the block exists...
		if (position != -1) {
			// Temporarily store the respective block's LRU information
			int currentBlockNum = bufferPoolLRU[position][0];
			int dataArrayPos = bufferPoolLRU[position][1];
			
			// Shift all information above that block in the LRU array down
			for (int i = position; i > 0; i--) {
				bufferPoolLRU[i][0] = bufferPoolLRU[i - 1][0];
				bufferPoolLRU[i][1] = bufferPoolLRU[i - 1][1];
			}
			
			// Place the respective block's LRU information at the top of the array
			bufferPoolLRU[0][0] = currentBlockNum;
			bufferPoolLRU[0][1] = dataArrayPos;
			
			// Increment number of cache hits
			cacheHits++;
		// If the block doesn't exist...
		} else {
			// Temporarily store the LRU information for the block that will be deleted
			int prevBlockNum = bufferPoolLRU[bufferPoolSize - 1][0];
			int prevBlockArrayPos = bufferPoolLRU[bufferPoolSize - 1][1];
			
			// Shift all information above that block in the LRU array down
			for (int i = bufferPoolSize - 1; i > 0; i--) {
				bufferPoolLRU[i][0] = bufferPoolLRU[i - 1][0];
				bufferPoolLRU[i][1] = bufferPoolLRU[i - 1][1];
			}
			
			// Place the new block's LRU information at the top of the array
			bufferPoolLRU[0][0] = blockNum;
			bufferPoolLRU[0][1] = prevBlockArrayPos;
			
			// If the deleted block position in the array wasn't empty...
			if (prevBlockNum != -1) {
				// Write it back to the binary file
				for (int i = prevBlockNum * 4096, j = 0; (i < (prevBlockNum + 1) * 4096) && (i < numRecords * 4); i += 2, j++) {
					f.seek(i);
					f.writeShort(dataBlocks[prevBlockArrayPos][j]);
				}
				blocksWritten++;
			}
			
			// Read in the new data from the binary file
			for (int i = blockNum * 4096, j = 0; (i < (blockNum + 1) * 4096) && (i < numRecords * 4); i += 2, j++) {
				f.seek(i);
				dataBlocks[prevBlockArrayPos][j] = f.readShort();
			}
			blocksRead++;
		}
	}
	
	/**
	 * This method writes the statistics collected from sorting the binary
	 * files to a text file.
	 * @param fw RandomAccessFile object being used to access binary files
	 * @param type Specifies the data that should be written depending on what type of sorting was done
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public void getReadsWrites(FileWriter fw, String type) throws IOException {
		if (type.equals("Records")) {
			fw.write("Records Read: " + recordsRead + "\r\n");
			fw.write("Records Written: " + recordsWritten + "\r\n");
		} else if (type.equals("Blocks")) {
			fw.write("Blocks Read: " + blocksRead + "\r\n");
			fw.write("Blocks Written: " + blocksWritten + "\r\n");
		} else if (type.equals("Cache Hits")) {
			fw.write("Cache Hits: " + cacheHits + "\r\n");
		}
	}
}
