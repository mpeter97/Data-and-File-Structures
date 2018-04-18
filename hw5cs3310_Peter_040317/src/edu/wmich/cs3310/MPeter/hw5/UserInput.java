package edu.wmich.cs3310.MPeter.hw5;

import java.io.*;
import java.util.*;

/**
 * This class contains methods that do all necessary generation of data, 
 * collection of information from the user, and printing of information to
 * the console.
 * 
 * @author Matt Peter
 */
public class UserInput {
	
	// Attributes
	private Scanner kbrd = new Scanner(System.in);		// Scans for input from the user
	private int bufferPoolSize = 1;						// Holds the current number of buffers in the buffer pool
	
	/**
	 * This method prints the main menu and collects the selection made
	 * by the user.  It also checks to make sure that the selection is
	 * valid.
	 * @return Menu selection made by user
	 */
	public char mainMenu() {
		// Declare and Instantiate local variables
		boolean validInput = false;
		char input = ' ';
		
		// Enter loop that continues until valid input is entered
		while (validInput == false) {
			try {
				// Print main menu
				System.out.println("Main Menu");
				System.out.println("----------");
				System.out.println("Enter g to generate a file");
				System.out.println("Enter s to sort a binary file that has already been generated");
				System.out.println("Enter r to resize the buffer pool");
				System.out.println("Enter q to quit");
				
				// Collect input
				input = kbrd.nextLine().charAt(0);
				
				// Check to see input that corresponds to one of the options was entered
				if ((input == 'g') || (input == 'G') || (input == 's') || (input == 'S') || (input == 'r') || (input == 'R') || (input == 'q') || (input == 'Q')) {
					validInput = true;
				} else {
					System.out.println("INVALID INPUT\n\n\n");
				}
			// Catch possible Exceptions
			} catch (Exception e) {
				System.out.println("INVALID INPUT\n\n\n");
			}
		}
		
		// Return the valid input
		return input;
	}
	
	/**
	 * This method collects the name of and number of records in a binary
	 * file that is then generated for the user.
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public void generateFile() throws IOException {
		// Declare and Instantiate a Random object
		Random rand = new Random();
		
		// Collect name of and number of records in the file from the user
		System.out.print("Please enter the name of the file: ");
		String fileName = kbrd.nextLine();
		fileName = fileName + ".dat";
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		System.out.print("Please enter the number of records of data you'd like generated: ");
		int numRecords = kbrd.nextInt();
		kbrd.nextLine();
		
		// Randomly generate records and write them in the file
		for (int i = 0; i < numRecords; i++) {
			short key = (short)((rand.nextInt(30000)) + 1);
			short value = (short)((rand.nextInt(30000)) + 1);
			file.writeShort(key);
			file.writeShort(value);
		}
		System.out.println("\n\n");
		
		// Close the file
		file.close();
	}
	
	/**
	 * This method collect the name of a binary file from the user that is then
	 * sorted using QuickSort objects and prints statistics relating to the
	 * process to a text file.
	 * @param fw FileWriter object that will be used to write statistics to a text file
	 * @throws IOException Handles Input/Output Exceptions
	 */
	public void sortFile(FileWriter fw) throws IOException {
		// Declare and Instantiate local variables
		long qsRecordsTime = 0;			// Keeps track of the total time used to sort file by records (4-byte)
		long qsBlocksTime = 0;			// Keeps track of the total time used to sort file by block (4096-byte)
		int numRecords = 0;				// Holds the number of records in the file being sorted	
		
		// Collect the name of the file being sorted from the user
		System.out.print("Please enter the name of the file: ");
		String fileName = kbrd.nextLine();
		fileName = fileName + ".dat";
		fw.write("File Name: " + fileName + "\r\n");
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		System.out.println("Sorting...");
		
		// Enter loop that counts the number the records in the file
		boolean hasNext = true;
		while (hasNext) {
			try {
				file.seek(numRecords * 4);
				file.readShort();
				numRecords++;
			} catch (Exception e) {
				hasNext = false;
			}
		}
		
		// Declare and Instantiate array that will store records in original unsorted order
		short[] unsortedRecords = new short[numRecords * 2];
		
		// Enter loop that reads and stores all records from file
		for (int i = 0; i < numRecords; i++) {
			file.seek(i * 4);
			unsortedRecords[i * 2] = file.readShort();
			file.seek((i * 4) + 2);
			unsortedRecords[(i * 2) + 1] = file.readShort();
		}
		
		// Declare two QuickSort objects; One for sorting by record; One for sorting by block
		QuickSort qsRecords = new QuickSort(file, numRecords, bufferPoolSize);
		QuickSort qsBlocks = new QuickSort(file, numRecords, bufferPoolSize);
		
		// Test how long it takes to sort by record
		if (numRecords != 0) {
			long startTime = System.currentTimeMillis();
			qsRecords.quickSortRecords(file, 0, numRecords - 1);
			long endTime = System.currentTimeMillis();
			qsRecordsTime = endTime - startTime;
		}
		
		// Rewrite the file back to its original state
		for (int i = 0; i < numRecords; i++) {
			file.seek(i * 4);
			file.writeShort(unsortedRecords[i * 2]);
			file.seek((i * 4) + 2);
			file.writeShort(unsortedRecords[(i * 2) + 1]);
		}
		
		// Test how long it takes to sort by block
		if (numRecords != 0) {
			long startTime = System.currentTimeMillis();
			qsBlocks.quickSortBlocks(file, 0, numRecords - 1, 0);
			long endTime = System.currentTimeMillis();
			qsBlocksTime = endTime - startTime;
		}
		
		// Print to the console all the records before they were sorted
		System.out.print("\nRecords Before Sorting: ");
		for (int i = 0; i < (numRecords * 2); i++) {
			System.out.print("(" + unsortedRecords[i] + ", " + 
							 unsortedRecords[++i] + ")");
			if (i != ((numRecords * 2) - 1)) {
				System.out.print(", ");
			}
		}
		
		// Print to the console all the records after they were sorted
		System.out.print("\nRecords After Sorting:  ");
		for (int i = 0; i < numRecords; i++) {
			file.seek(i * 4);
			System.out.print("(" + file.readShort() + ", ");
			file.seek((i * 4) + 2);
			System.out.print(file.readShort() + ")");
			if (i != numRecords - 1) {
				System.out.print(", ");
			}
		}
		
		System.out.println("\n\n");
		
		// Print the statistics to the text file
		qsBlocks.getReadsWrites(fw, "Cache Hits");
		qsRecords.getReadsWrites(fw, "Records");
		qsBlocks.getReadsWrites(fw, "Blocks");
		fw.write("QuickSort Records Time: " + qsRecordsTime + "\r\n");
		fw.write("QuickSort Blocks Time: " + qsBlocksTime + "\r\n\r\n");
		
		// If the file's name is "samplehw5.dat", print all the records before and after
		//   sorting to the text file as well
		if (fileName.equals("samplehw5.dat")) {
			fw.write("Records Before Sorting: ");
			for (int i = 0; i < (numRecords * 2); i++) {
				fw.write("(" + unsortedRecords[i] + ", " + 
								 unsortedRecords[++i] + ")");
				if (i != ((numRecords * 2) - 1)) {
					fw.write(", ");
				}
			}
			fw.write("\r\nRecords After Sorting:  ");
			for (int i = 0; i < numRecords; i++) {
				file.seek(i * 4);
				fw.write("(" + file.readShort() + ", ");
				file.seek((i * 4) + 2);
				fw.write(file.readShort() + ")");
				if (i != numRecords - 1) {
					fw.write(", ");
				}
			}
			fw.write("\r\n\r\n");
		}
		
		// Close the file
		file.close();
	}
	
	/**
	 * This method allows you the change the number of buffers that
	 * are used in the buffer pool.
	 */
	public void resizeBufferPool() {
		// Print directions and get user input
		System.out.print("Please enter the size of the buffer pool: ");
		bufferPoolSize = kbrd.nextInt();
		
		// Repeat while the user input is not in desired range
		while ((bufferPoolSize < 1) || (bufferPoolSize > 20)) {
			System.out.print("OUT OF RANGE.  Please enter the size of the buffer pool: ");
			bufferPoolSize = kbrd.nextInt();
		}
		kbrd.nextLine();
		System.out.println("\n\n");
	}
}
