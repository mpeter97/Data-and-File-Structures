This program was made in Eclipse

If you're using Eclipse, simply go to File -> Import -> General -> Existing Projects into Workspace, and select the folder after it has been extracted from the .zip file

This program uses an external QuickSort sorting method to sort data stored in a binary file.

-------------------------------------------------------------------------
THE EXTRA CREDIT IMPLEMENTATION OF A BUFFER POOL IS USED IN THIS PROGRAM.
-------------------------------------------------------------------------

Throughout the program, you will see the following text printed in the console:

Main Menu
----------
Enter g to generate a file
Enter s to sort a binary file that has already been generated
Enter r to resize the buffer pool
Enter q to quit


Entering one of these four characters (uppercase or lowercase) will lead to a specific action being executed:
g - Asks the user to enter some information needed to generate a binary file
	1. File Name - What the file will be called (DO NOT include ".dat" in the name.  It will automatically be added)
	2. File Size - This is the number of 4-byte records that will be randomly generated and stored in the file

s - Sorts a previously generated binary file
	1. File Name - What file you want sorted (DO NOT include ".dat" in the name.  It will automatically be added)

r - Resizes the buffer pool
	1. Buffer Pool Size - Number of the buffers in the buffer pool (Only values 1 to 20 will be accepted)

q - Terminates the program



Whenever the sort command is run, additional stats will be generated in addition to the binary file being sorted
IMPORTANT: IN ORDER FOR THE STATS TO BE PRINTED TO THE TEXT FILE, YOU MUST ENTER q TO TERMINATE THE PROGRAM
The following is the layout for stats printed to the text file:

File Name: <Name of Data file being sorted>
Cache Hits: <Number of cache hits when doing operations on blocks of size 4096 bytes>
Records Read: <Number of disk reads with blocks 4 bytes in size>
Records Written: <Number of disk writes with blocks 4 bytes in size>
Blocks Read: <Number of disk reads with blocks 4096 bytes in size>
Blocks Written: <Number of disk writes with blocks 4096 bytes in size>
QuickSort Records Time: <Time it took to execute QuickSort with blocks 4 bytes in size>
QuickSort Blocks Time: <Time it took to execute QuickSort with blocks 4096 bytes in size>

ADDITIONAL STATS IN THE SPECIAL CASE OF samplehw5.dat:
Records Before Sorting: <Key/Value pairs before QuickSort is executed>
Records After Sorting:  <Key/Value pairs after QuickSort is executed>


(These last two stats will be printed in the console as well in all cases)


Notes regarding data found in hw5stat.txt:

For testhw5.dat, the data was sorted using a buffer pool with 20 buffers
For samplehw5.dat, the data was sorted using a buffer pool with 1 buffer
For myData1hw5.dat, the data was sorted using a buffer pool with 5 buffers
For myData2hw5.dat, the data was sorted using a buffer pool with 10 buffers
For myData3hw5.dat, the data was sorted using a buffer pool with 15 buffers
For myData4hw5.dat, the data was sorted using a buffer pool with 20 buffers
For myData5hw5.dat, the data was sorted using a buffer pool with 10 buffers
For myData6hw5.dat, the data was sorted using a buffer pool with 10 buffers
For myData7hw5.dat, the data was sorted using a buffer pool with 10 buffers
For myData8hw5.dat, the data was sorted using a buffer pool with 10 buffers
