package edu.wmich.cs3310.MPeter.hw4;

/**
 * This class creates a binary search tree (BST).
 * Methods of insert and delete can be used to alter the nodes
 * in the tree.  A variety of other methods can then be used to
 * gather and print information regarding the tree.
 * 
 * @author Matt Peter
 */
public class BST {
	/**
	 * This class inside BST defines the nodes that make
	 * up the binary search tree.  Each node has a reference
	 * to its left child, right child, and parent, as well as
	 * a key, count, sizeOfSubtree, and subtreeHeight.
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
		int sizeOfSubtree;  // Size of subtree starting with this node
		int subtreeHeight;  // Height of subtree starting with this node
		
		/**
		 * This constructor sets all node references to null, key to -1,
		 * count to 0, sizeOfSubtree to 0, and subtreeHeight to -1.
		 */
		public Node() {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = -1;
			count = 0;
			sizeOfSubtree = 0;
			subtreeHeight = -1;
		}
		
		/**
		 * This constructor sets all node references to null, key to the
		 * passed value, count to 1, sizeOfSubtree to 1, and subtreeHeight to 0.
		 * @param k Key assigned to node
		 */
		public Node(int k) {
			leftChild = null;
			rightChild = null;
			parent = null;
			key = k;
			count = 1;
			sizeOfSubtree = 1;
			subtreeHeight = 0;
		}
	}
	
	// Attributes
	private Node root = new Node();		// Root node of a tree
	private int countTotal = 0;			// Tracks number of values counted (Used for finding kth key)
	
	/**
	 * This method inserts a node or increments the count value
	 * of a node in the BST based on the passed key.  If there isn't
	 * a node with the passed value, a new node is created.  If there is
	 * already a node with the passed value, that node's count attribute
	 * is incremented.
	 * @param keyValue Key of node to be inserted or incremented
	 */
	public void insert(int keyValue) {
		// If at least one node already exists...
		if (root.sizeOfSubtree != 0) {
			Node temp = root;
			
			// Find the node where the key should exist in the tree
			while ((temp.leftChild != null) || (temp.rightChild != null)) {
				if ((keyValue < temp.key) && (temp.leftChild != null)) {
					temp = temp.leftChild;
				} else if ((keyValue > temp.key) && (temp.rightChild != null)) {
					temp = temp.rightChild;
				} else {
					break;
				}
			}
			
			// If the key doesn't exist, insert it properly
			// Otherwise, increment the count value of its node
			if (keyValue < temp.key) {
				temp.leftChild = new Node(keyValue);
				temp.leftChild.parent = temp;
			} else if (keyValue > temp.key) {
				temp.rightChild = new Node(keyValue);
				temp.rightChild.parent = temp;
			} else {
				temp.count++;
			}
		} else {
        // Else, establish a root node
			root.key = keyValue;
			root.count = 1;
			root.sizeOfSubtree = 1;
			root.subtreeHeight = 0;
		}
	}
	
	/**
	 * This method deletes a node from the BST based on
	 * the passed key.  It then moves other nodes if necessary
	 * to maintain the structure of the tree.
	 * @param keyValue Key of node to be deleted
	 */
	public void delete(int keyValue) {
		Node temp = root;
		// Find the node where the passed value exists
		while ((temp.leftChild != null) || (temp.rightChild != null)) {
			if ((keyValue < temp.key) && (temp.leftChild != null)) {
				temp = temp.leftChild;
			} else if ((keyValue > temp.key) && (temp.rightChild != null)) {
				temp = temp.rightChild;
			} else {
				break;
			}
		}
		
		if (temp.key == keyValue) {
			// While the node being moved is not a leaf node...
			while ((temp.leftChild != null) || (temp.rightChild != null)) {
				// Find the node that will replace the node being deleted/moved
				Node replacement = temp;
				if (replacement.leftChild != null) {
					replacement = replacement.leftChild;
					while (replacement.rightChild != null) {
						replacement = replacement.rightChild;
					}
				} else if (replacement.rightChild != null) {
					replacement = replacement.rightChild;
					while (replacement.leftChild != null) {
						replacement = replacement.leftChild;
					}
				}
				
				// Assign the replacement node to node it will replace
				temp.key = replacement.key;
				temp.count = replacement.count;
				temp = replacement;
			}
			// "Delete" the resulting empty node
			if (temp == temp.parent.leftChild) {
				temp.parent.leftChild = null;
			} else {
				temp.parent.rightChild = null;
			}
		}
	}
	
	/**
	 * This method returns the height of the BST
	 * @return Height of BST
	 */
	public int getHeight() {
		return root.subtreeHeight;
	}
	
	/**
	 * This method executes the findSumKeys method and prints
	 * and returns the results.
	 * @return Sum of all nodes' key values
	 */
	public int getSumKeys() {
		int sumKeys = findSumKeys(root);
		System.out.println("Sum of Keys: " + sumKeys);
		return sumKeys;
	}
	
	/**
	 * This method finds the sum of all of the nodes'
	 * key values.
	 * @param node Current node being checked
	 * @return Sum of all nodes' key values
	 */
	private int findSumKeys(Node node) {
		int sum = 0;
		if (node.leftChild != null) {
			sum += findSumKeys(node.leftChild);
		}
		
		sum += node.key;
		
		if (node.rightChild != null) {
			sum += findSumKeys(node.rightChild);
		}
		
		return sum;
	}
	
	/**
	 * This method executes the findSumCounts method and prints
	 * and returns the results.
	 * @return Sum of all nodes' count values
	 */
	public int getSumCounts() {
		int sumCounts = findSumCounts(root);
		System.out.println("Sum of Counts: " + sumCounts);
		return sumCounts;
	}
	
	/**
	 * This method finds the sum of all of the nodes'
	 * count values.
	 * @param node Current node being checked
	 * @return Sum of all nodes' count values
	 */
	private int findSumCounts(Node node) {
		int sum = 0;
		if (node.leftChild != null) {
			sum += findSumCounts(node.leftChild);
		}
		
		sum += node.count;
		
		if (node.rightChild != null) {
			sum += findSumCounts(node.rightChild);
		}
		
		return sum;
	}
	
	/**
	 * This method executes the recursive findKthSmallest method with
	 * arguments of 5, 20, 30, and 70 for the kth value.
	 */
	public void findKeys() {
		System.out.println("5th Smallest: " + findKthSmallest(root, 5));
		countTotal = 0;
		System.out.println("20th Smallest: " + findKthSmallest(root, 20));
		countTotal = 0;
		System.out.println("30th Smallest: " + findKthSmallest(root, 30));
		countTotal = 0;
		System.out.println("70th Smallest: " + findKthSmallest(root, 70));
	}

	/**
	 * This method finds the node with the kth smallest key value
	 * (where k is a passed integer value).
	 * @param node Current node being checked
	 * @param k The rank of key value to search for (kth smallest)
	 * @return The kth smallest key value
	 */
	private int findKthSmallest(Node node, int k) {
		int kthSmallestKey = 0;
		if (node.leftChild != null) {
			kthSmallestKey = findKthSmallest(node.leftChild, k);
		}
		if (kthSmallestKey != 0) {
			return kthSmallestKey;
		}
		
		countTotal += node.count;
		if (k <= countTotal) {
			return node.key;
		}
		
		if (node.rightChild != null) {
			kthSmallestKey = findKthSmallest(node.rightChild, k);
		}
		
		return kthSmallestKey;
	}
	
	/**
	 * This method executes the recursive preorder, postorder, and
	 * inorder printing methods.
	 */
	public void printOrders() {
		System.out.print("Preorder: ");
		printPreorder(root);
		System.out.print("\nPostorder: ");
		printPostorder(root);
		System.out.print("\nInorder: ");
		printInorder(root);
		System.out.println();
	}
	
	/**
	 * This method prints each of the tree's nodes' key, count,
	 * sizeOfSubtree, and subtreeHeight values in inorder format.
	 * @param node Current node being checked
	 */
	private void printInorder(Node node) {
		if (node.leftChild != null) {
			printInorder(node.leftChild);
		}
		
		System.out.print("(" + node.key + ", " + node.count + ", " + node.sizeOfSubtree + ", " + node.subtreeHeight + "), ");
		
		if (node.rightChild != null) {
			printInorder(node.rightChild);
		}
	}
	
	/**
	 * This method prints each of the tree's nodes' key, count,
	 * sizeOfSubtree, and subtreeHeight values in preorder format.
	 * @param node Current node being checked
	 */
	private void printPreorder(Node node) {
		System.out.print("(" + node.key + ", " + node.count + ", " + node.sizeOfSubtree + ", " + node.subtreeHeight + "), ");
		
		if (node.leftChild != null) {
			printPreorder(node.leftChild);
		}
		
		if (node.rightChild != null) {
			printPreorder(node.rightChild);
		}
	}
	
	/**
	 * This method prints each of the tree's nodes' key, count,
	 * sizeOfSubtree, and subtreeHeight values in postorder format.
	 * @param node Current node being checked
	 */
	private void printPostorder(Node node) {
		if (node.leftChild != null) {
			printPostorder(node.leftChild);
		}
		
		if (node.rightChild != null) {
			printPostorder(node.rightChild);
		}
		
		System.out.print("(" + node.key + ", " + node.count + ", " + node.sizeOfSubtree + ", " + node.subtreeHeight + "), ");
	}
	
	/**
	 * This method simply runs the updateSize and updateHeight
	 * methods to keep the BST's height subtrees' sizes up to date.
	 */
	public void updateTree() {
		updateSize(root);
		updateHeight(root);
	}
	
	/**
	 * This method recursively computes the size of all the nodes in the BST.
	 * @param node Current node being checked
	 * @return Size of subtree
	 */
	private int updateSize(Node node) {
		int size = 0;
		if ((node.leftChild == null) && (node.rightChild == null)) {
			node.sizeOfSubtree = 1;
			return 1;
		}
		if (node.leftChild != null) {
			size += updateSize(node.leftChild);
		}
		if (node.rightChild != null) {
			size += updateSize(node.rightChild);
		}
		size++;
		node.sizeOfSubtree = size;
		return size;
	}
	
	/**
	 * This method recursively computes the height of the BST.
	 * @param node Current node being checked
	 * @return Height of the BST
	 */
	private int updateHeight(Node node) {
		int leftHeight = 0;
		int rightHeight = 0;
		if ((node.leftChild == null) && (node.rightChild == null)) {
			node.subtreeHeight = 0;
			return 0;
		}
		if (node.leftChild != null) {
			leftHeight = updateHeight(node.leftChild);
			leftHeight++;
		}
		if (node.rightChild != null) {
			rightHeight = updateHeight(node.rightChild);
			rightHeight++;
		}
		if (leftHeight > rightHeight) {
			node.subtreeHeight = leftHeight;
			return leftHeight;
		} else {
			node.subtreeHeight = rightHeight;
			return rightHeight;
		}
	}
}
