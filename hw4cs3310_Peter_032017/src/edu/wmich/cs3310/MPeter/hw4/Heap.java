package edu.wmich.cs3310.MPeter.hw4;

/**
 * This class creates a heap.
 * Different insert methods allow for the creation of
 * both min- and max-heaps.
 * 
 * @author Matt Peter
 */
public class Heap {
	/**
	 * This class inside Heap defines the nodes that make
	 * up the heap.  Each node has a reference to its left child,
	 * right child, and parent, as well as a key and count.
	 * 
	 * @author Matt Peter
	 */
	private class Node {
		// Attributes
		Node leftChild;		// Left child of current node
		Node rightChild;	// Right child of current node
		Node parent;		// Parent of current node
		int key;			// Integer in range [1, 20]
		int count;			// Number of occurrences of this key
		
		/**
		 * This constructor sets all node references to null, key to -1,
		 * and count to 0.
		 */
		public Node() {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = -1;
			count = 0;
		}
		
		/**
		 * This constructor sets all node references to null, key to
		 * the passed value, and count to 1.
		 * @param k Key assigned to node
		 */
		public Node(int k) {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = k;
			count = 1;
		}
	}
	
	// Attributes
	private Node root = new Node();		// Root node of tree
	private int nextPosition = 0;		// Position where next node will be inserted
	
	/**
	 * This method inserts a key value into a max-heap.  It increments the
	 * counter value of the node if the key already exists, or inserts a new
	 * node if no node that is already present has that key value.  After the
	 * new node is inserted or the counter value is incremented, the heap is
	 * heapified.
	 * @param keyValue Key value to be inserted into heap
	 */
	public void insertMaxHeap(int keyValue) {
		// If at least one node already exists...
		if (nextPosition != 0) {
			Node temp = root;
			// Find where the key exists (if it does)
			int location = findKey(root, keyValue, 0);
			
			// If the key already exists...
			if (location != -1) {
				int[] steps = {0, 0, 0, 0};
				// Figure out how to access it in the heap
				for (int j = 0; j < 4; j++) {
					if (location != 0) {
						if (location % 2 == 1) {
							steps[j] = 1;
							location = ((location - 1) / 2);
						} else {
							steps[j] = 2;
							location = ((location - 2) / 2);
						}
					}
				}
				// Access the node in the heap
				for (int j = 3; j >= 0; j--) {
					if (steps[j] == 1) {
						temp = temp.leftChild;
					} else if (steps[j] == 2) {
						temp = temp.rightChild;
					}
				}
				// Increment its count
				temp.count++;
				
				// Adjust the node's position in the heap if necessary
				while ((temp.parent != null) && (temp.count > temp.parent.count)) {
					int tempVal = temp.key;
					temp.key = temp.parent.key;
					temp.parent.key = tempVal;
					tempVal = temp.count;
					temp.count = temp.parent.count;
					temp.parent.count = tempVal;
					temp = temp.parent;
				}
			// Otherwise, if the key doesn't exist yet...
			} else {
				int tempPos = nextPosition;
				int[] steps = {0, 0, 0, 0};
				// Figure out where it should be inserted in the heap
				for (int j = 0; j < 4; j++) {
					if (tempPos != 0) {
						if (tempPos % 2 == 1) {
							steps[j] = 1;
							tempPos = ((tempPos - 1) / 2);
						} else {
							steps[j] = 2;
							tempPos = ((tempPos - 2) / 2);
						}
					}
				}
				// Access the parent of where it should be inserted
				for (int j = 3; j > 0; j--) {
					if (steps[j] == 1) {
						temp = temp.leftChild;
					} else if (steps[j] == 2) {
						temp = temp.rightChild;
					}
				}
				// Insert the node
				if (steps[0] == 1) {
					temp.leftChild = new Node(keyValue);
					temp.leftChild.parent = temp;
				} else {
					temp.rightChild = new Node(keyValue);
					temp.rightChild.parent = temp;
				}
				nextPosition++;
			}
		// Otherwise, if this is the first node, set it as the root
		} else {
			root.key = keyValue;
			root.count = 1;
			nextPosition++;
		}
	}
	
	/**
	 * This method inserts a key value into a min-heap.  It increments the
	 * counter value of the node if the key already exists, or inserts a new
	 * node if no node that is already present has that key value.  After the
	 * new node is inserted or the counter value is incremented, the heap is
	 * heapified.
	 * @param keyValue Key value to be inserted into heap
	 */
	public void insertMinHeap(int keyValue) {
		// If at least one node already exists...
		if (nextPosition != 0) {
			Node temp = root;
			// Find where the key exists (if it does)
			int location = findKey(root, keyValue, 0);
			
			// If the key already exists...
			if (location != -1) {
				int[] steps = {0, 0, 0, 0};
				// Figure out how to access it in the heap
				for (int j = 0; j < 4; j++) {
					if (location != 0) {
						if (location % 2 == 1) {
							steps[j] = 1;
							location = ((location - 1) / 2);
						} else {
							steps[j] = 2;
							location = ((location - 2) / 2);
						}
					}
				}
				// Access the node in the heap
				for (int j = 3; j >= 0; j--) {
					if (steps[j] == 1) {
						temp = temp.leftChild;
					} else if (steps[j] == 2) {
						temp = temp.rightChild;
					}
				}
				// Increment its count
				temp.count++;
				
				// Adjust the node's position in the heap if necessary
				while ((temp.leftChild != null) || (temp.rightChild != null)) {
					if ((temp.leftChild != null) && (temp.count > temp.leftChild.count)) {
						int tempVal = temp.key;
						temp.key = temp.leftChild.key;
						temp.leftChild.key = tempVal;
						tempVal = temp.count;
						temp.count = temp.leftChild.count;
						temp.leftChild.count = tempVal;
						temp = temp.leftChild;
					} else if ((temp.rightChild != null) && (temp.count > temp.rightChild.count)) {
						int tempVal = temp.key;
						temp.key = temp.rightChild.key;
						temp.rightChild.key = tempVal;
						tempVal = temp.count;
						temp.count = temp.rightChild.count;
						temp.rightChild.count = tempVal;
						temp = temp.rightChild;
					} else {
						break;
					}
				}
			// Otherwise, if the key doesn't exist yet...
			} else {
				int tempPos = nextPosition;
				int[] steps = {0, 0, 0, 0};
				// Figure out where it should be inserted in the heap
				for (int j = 0; j < 4; j++) {
					if (tempPos != 0) {
						if (tempPos % 2 == 1) {
							steps[j] = 1;
							tempPos = ((tempPos - 1) / 2);
						} else {
							steps[j] = 2;
							tempPos = ((tempPos - 2) / 2);
						}
					}
				}
				// Access the parent of where it should be inserted
				for (int j = 3; j > 0; j--) {
					if (steps[j] == 1) {
						temp = temp.leftChild;
					} else if (steps[j] == 2) {
						temp = temp.rightChild;
					}
				}
				// Insert the node
				if (steps[0] == 1) {
					temp.leftChild = new Node(keyValue);
					temp.leftChild.parent = temp;
					temp = temp.leftChild;
				} else {
					temp.rightChild = new Node(keyValue);
					temp.rightChild.parent = temp;
					temp = temp.rightChild;
				}
				// Adjust the node's position in the heap if necessary
				while ((temp.parent != null) && (temp.count < temp.parent.count)) {
					int tempVal = temp.key;
					temp.key = temp.parent.key;
					temp.parent.key = tempVal;
					tempVal = temp.count;
					temp.count = temp.parent.count;
					temp.parent.count = tempVal;
					temp = temp.parent;
				}
				nextPosition++;
			}
		// Otherwise, if this is the first node, set it as the root
		} else {
			root.key = keyValue;
			root.count = 1;
			nextPosition++;
		}
	}
	
	/**
	 * This method returns the root node's key value
	 * @return Root node's key value
	 */
	public int getRootKeyValue() {
		return root.key;
	}
	
	/**
	 * This method returns the root node's counter value
	 * @return Root node's counter value
	 */
	public int getRootCounterValue() {
		return root.count;
	}
	
	/**
	 * This method checks to see whether a node with the specified
	 * key exists in the heap.
	 * @param node Current node being checked
	 * @param key Key being looked for
	 * @param heapPos Current position in heap
	 * @return The position of the key in the heap (-1 if the key doesn't exist)
	 */
	private int findKey(Node node, int key, int heapPos) {
		int keyPos = -1;
		
		if ((node.leftChild != null) && (keyPos == -1)) {
			keyPos = findKey(node.leftChild, key, (2 * heapPos) + 1);
		}
		
		if (node.key == key) {
			return heapPos;
		}
		
		if ((node.rightChild != null) && (keyPos == -1)) {
			keyPos = findKey(node.rightChild, key, (2 * heapPos) + 2);
		}
		return keyPos;
	}
}
