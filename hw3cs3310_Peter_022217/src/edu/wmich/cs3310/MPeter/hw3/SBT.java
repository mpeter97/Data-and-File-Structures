package edu.wmich.cs3310.MPeter.hw3;

/**
 * This class stores a size-balanced binary tree (SBT). 
 * Methods of findMin, deleteMin, and insert are used to read and 
 * alter the tree.
 * 
 * @author Matt Peter
 */
public class SBT implements ISBT {
	/**
	 * This class inside SBT defines the nodes that are used to store 
	 * the data.  Each node has a reference to its left child, right child,
	 * and parent, as well as a key, value, and size.
	 * 
	 * @author Matt
	 */
	private class Node {
		// Attributes
		Node leftChild;		// Left child of current node
		Node rightChild;	// Right child of current node
		Node parent;		// Parent of current node
		int key;			// Integer in range [1, 1000]
		int value;			// Integer in range [5000, 20000]
		int size;			// Number of nodes below current node + current node
		
		/**
		 * This constructor sets all node references to null, key and value 
		 * to -1, and size to 0.
		 */
		public Node() {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = -1;
			value = -1;
			size = 0;
		}
		
		/**
		 * This constructor sets all node references to null, key and value to
		 * the two passed integers, and size to 0.
		 * 
		 * @param k Key assigned to node
		 * @param v Value assigned to node
		 */
		public Node(int k, int v) {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = k;
			value = v;
			size = 0;
		}
	}
	
	// Attributes
	private Node root = new Node();		// Root node of the SBT
	private int position = 0;			// Numeric position in the binary tree
	
	/**
	 * This method returns the lowest key/value/size set in the SBT, based 
	 * on the key (returns (-1, -1, 0) if the tree is empty).
	 * 
	 * @return Lowest key/value/size set in SBT
	 */
	public int[] findMin() {
		int[] min = {root.key, root.value, root.size};
		return min;
	}
	
	/**
	 * This method removes the lowest key/value/size set from the SBT.
	 */
	public void deleteMin() {
		if (root.size > 1) {
			// Create a temporary node, decrement the size value of the node, and
			//   declare a variable to keep track of which child could be deleted
			Node temp = root;
			temp.size--;
			char child = ' ';
			
			// Follow the path with the higher size value (or go to right by default),
			//   decreasing size value of each node as you go
			while ((temp.leftChild != null) || (temp.rightChild != null)) {
				if ((temp.leftChild != null) && (temp.rightChild != null)) {
					if (temp.leftChild.size > temp.rightChild.size) {
						temp = temp.leftChild;
						child = 'l';
					} else {
						temp = temp.rightChild;
						child = 'r';
					}
				} else if (temp.leftChild != null) {
					temp = temp.leftChild;
					child = 'l';
				} else {
					temp = temp.rightChild;
					child = 'r';
				}
				temp.size--;
			}
			
			// Move temp information to root
			root.key = temp.key;
			root.value = temp.value;
			
			temp = temp.parent;
			
			// Delete temp node
			if (child == 'l') {
				temp.leftChild = null;
			} else if (child == 'r') {
				temp.rightChild = null;
			}
			
			temp = root;
			
			// Heapify the tree
			while ((temp.leftChild != null) || (temp.rightChild != null)) {
				if ((temp.leftChild != null) && (temp.key > temp.leftChild.key)) {
					if ((temp.rightChild != null) && (temp.key > temp.rightChild.key)) {
						if (temp.leftChild.key > temp.rightChild.key) {
							int tempKey = temp.key;
							int tempVal = temp.value;
							temp.key = temp.rightChild.key;
							temp.value = temp.rightChild.value;
							temp.rightChild.key = tempKey;
							temp.rightChild.value = tempVal;
							temp = temp.rightChild;
						} else {
							int tempKey = temp.key;
							int tempVal = temp.value;
							temp.key = temp.leftChild.key;
							temp.value = temp.leftChild.value;
							temp.leftChild.key = tempKey;
							temp.leftChild.value = tempVal;
							temp = temp.leftChild;
						}
					} else {
						int tempKey = temp.key;
						int tempVal = temp.value;
						temp.key = temp.leftChild.key;
						temp.value = temp.leftChild.value;
						temp.leftChild.key = tempKey;
						temp.leftChild.value = tempVal;
						temp = temp.leftChild;
					}
				} else if ((temp.rightChild != null) && (temp.key > temp.rightChild.key)) {
					int tempKey = temp.key;
					int tempVal = temp.value;
					temp.key = temp.rightChild.key;
					temp.value = temp.rightChild.value;
					temp.rightChild.key = tempKey;
					temp.rightChild.value = tempVal;
					temp = temp.rightChild;
				} else {
					break;
				}
			}
		} else if (root.size == 1) {
			// If the tree only has 1 node, set that node to "empty" values
			root.key = -1;
			root.value = -1;
			root.size = 0;
		}
	}
	
	/**
	 * This method inserts the passed key/value pair into the SBT, determining 
	 * the size in the process.
	 * 
	 * @param key Integer in range [1, 1000]
	 * @param value Integer in range [5000, 20000]
	 */
	public void insert(int key, int value) {
		if (root.size != 0) {
			// Create a temporary node and increment the size value of the node
			Node temp = root;
			root.size++;
			
			// Follow the path with the lower size value (or go to left by default),
			//   increasing size value of each node as you go
			while ((temp.leftChild != null) && (temp.rightChild != null)) {
				if (temp.rightChild.size < temp.leftChild.size) {
					temp = temp.rightChild;
				} else {
					temp = temp.leftChild;
				}
				temp.size++;
			}
			
			// Create new node
			if (temp.leftChild == null) {
				temp.leftChild = new Node(key, value);
				temp.leftChild.parent = temp;
				temp = temp.leftChild;
			} else {
				temp.rightChild = new Node(key, value);
				temp.rightChild.parent = temp;
				temp = temp.rightChild;
			}
			temp.size++;
			
			// Heapify the tree
			while (temp.parent != null) {
				if (temp.key < temp.parent.key) {
					int tempKey = temp.key;
					int tempVal = temp.value;
					temp.key = temp.parent.key;
					temp.value = temp.parent.value;
					temp.parent.key = tempKey;
					temp.parent.value = tempVal;
					temp = temp.parent;
				} else {
					break;
				}
			}
			
		} else {
			// If the tree is empty, assign the information to the root node
			root.key = key;
			root.value = value;
			root.size = 1;
		}
	}
	
	/**
	 * This method returns the entire SBT in a format based on the String
	 * argument (preorder format, inorder format, or in the order the values
	 * were inserted into the tree).
	 * 
	 * @param type Order format (preorder, inorder, or insertion order)
	 * @return SBT in specified format
	 */
	public int[][] getSBT(String type) {
		int[][] binTree = new int[31][3];
		if (type.equals("PreOrder")) {
			preOrderGetSBT(binTree, root);
		} else if (type.equals("InOrder")) {
			inOrderGetSBT(binTree, root);
		} else if (type.equals("Insertion Order")) {
			for (position = 0; position < binTree.length; position++) {
				insertionOrderGetSBT(binTree, root, position);
			}
		}
		position = 0;
		return binTree;
	}
	
	/**
	 * Creates an array that contains the nodes from the tree in preorder format.
	 * 
	 * @param data Array containing data in preorder
	 * @param node Current node in tree being used
	 */
	private void preOrderGetSBT(int[][] data, Node node) {
		data[position][0] = node.key;
		data[position][1] = node.value;
		data[position][2] = node.size;
		position++;
		if (node.leftChild != null) {
			preOrderGetSBT(data, node.leftChild);
		}
		if (node.rightChild != null) {
			preOrderGetSBT(data, node.rightChild);
		}
	}
	
	/**
	 * Creates an array that contains the nodes from the tree in inorder format.
	 * 
	 * @param data Array containing data in inorder
	 * @param node Current node in tree being used
	 */
	private void inOrderGetSBT(int[][] data, Node node) {
		if (node.leftChild != null) {
			inOrderGetSBT(data, node.leftChild);
		}
		data[position][0] = node.key;
		data[position][1] = node.value;
		data[position][2] = node.size;
		position++;
		if (node.rightChild != null) {
			inOrderGetSBT(data, node.rightChild);
		}
	}
	
	/**
	 * Creates an array that contains the nodes from the tree in the order they
	 * were inserted.
	 * 
	 * @param data Array containing data in order inserted
	 * @param node Current node in tree being used
	 * @param pos Current position in array being used
	 */
	private void insertionOrderGetSBT(int[][] data, Node node, int pos) {
		if (pos == 0) {
			data[position][0] = node.key;
			data[position][1] = node.value;
			data[position][2] = node.size;
		} else if ((pos % 2 == 0) && (node.rightChild != null)) {
			insertionOrderGetSBT(data, node.rightChild, ((pos / 2) - 1));
		} else if ((pos % 2 == 1) && (node.leftChild != null)){
			insertionOrderGetSBT(data, node.leftChild, ((pos - 1) / 2));
		}
	}
}
