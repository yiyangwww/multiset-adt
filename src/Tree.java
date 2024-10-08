import java.util.ArrayList;
import java.util.Random;

class Tree<T> {
    private T root;
    private ArrayList<Tree<T>> subtrees;

    // Constructor
    public Tree(T root) {
        this.root = root;
        this.subtrees = new ArrayList<>();
    }

    // Check if the tree is empty
    public boolean isEmpty() {
        return root == null;
    }

    // Get the size of the tree (number of elements)
    public int size() {
        if (isEmpty()) {
            return 0;
        } else {
            int size = 1;  // count the root
            for (Tree<T> subtree : subtrees) {
                size += subtree.size();  // recursive call to get size of subtrees
            }
            return size;
        }
    }

    // Count occurrences of an item in the tree
    public int count(T item) {
        if (isEmpty()) {
            return 0;
        } else {
            int num = 0;
            if (root.equals(item)) {
                num += 1;
            }
            for (Tree<T> subtree : subtrees) {
                num += subtree.count(item);  // recursive call
            }
            return num;
        }
    }

    // Calculate the average of all the values in the tree




    // Check if the tree contains an item
    public boolean contains(T item) {
        if (isEmpty()) {
            return false;
        }

        if (root.equals(item)) {
            return true;
        }

        for (Tree<T> subtree : subtrees) {
            if (subtree.contains(item)) {
                return true;
            }
        }
        return false;
    }

    // Return a list of all leaves in the tree
    public ArrayList<T> leaves() {
        ArrayList<T> leafList = new ArrayList<>();

        if (isEmpty()) {
            return leafList;
        } else if (subtrees.isEmpty()) {
            // If there are no subtrees, this node is a leaf
            leafList.add(root);
        } else {
            // Recursively add leaves from subtrees
            for (Tree<T> subtree : subtrees) {
                leafList.addAll(subtree.leaves());
            }
        }
        return leafList;
    }


    // Insert an item into the tree
    public void insert(T item) {
        if (isEmpty()) {
            root = item;
        } else if (subtrees.isEmpty()) {
            subtrees.add(new Tree<>(item));
        } else {
            Random random = new Random();
            if (random.nextInt(3) == 2) {
                subtrees.add(new Tree<>(item));
            } else {
                int subtreeIndex = random.nextInt(subtrees.size());
                subtrees.get(subtreeIndex).insert(item);  // recursively insert into a random subtree
            }
        }
    }

    // Insert an item as a child of the specified parent
    public boolean insertChild(T item, T parent) {
        if (isEmpty()) {
            return false;
        } else if (root.equals(parent)) {
            subtrees.add(new Tree<>(item));  // Add item as a new subtree
            return true;
        } else {
            // Recursively try to insert the item in subtrees
            for (Tree<T> subtree : subtrees) {
                if (subtree.insertChild(item, parent)) {
                    return true;
                }
            }
            return false;  // Parent not found
        }
    }


    // Delete an item from the tree (if it exists)
    public boolean deleteItem(T item) {
        if (isEmpty()) {
            return false;
        } else if (root.equals(item)) {
            if (!subtrees.isEmpty()) {
                Tree<T> lastSubtree = subtrees.remove(subtrees.size() - 1);
                root = lastSubtree.root;
                subtrees.addAll(lastSubtree.subtrees);  // reassign the last subtree's root and subtrees
            } else {
                root = null;  // no subtrees, so tree becomes empty
            }
            return true;
        } else {
            for (Tree<T> subtree : subtrees) {
                if (subtree.deleteItem(item)) {
                    if (subtree.isEmpty()) {
                        subtrees.remove(subtree);  // remove the empty subtree
                    }
                    return true;
                }
            }
            return false;
        }
    }

    // Print the tree with indentation for debugging purposes
    public void printTree() {
        printIndentedTree(0);
    }

    // Recursive helper to print the tree with indentation
    private void printIndentedTree(int depth) {
        if (!isEmpty()) {
            System.out.println("  ".repeat(depth) + root);
            for (Tree<T> subtree : subtrees) {
                subtree.printIndentedTree(depth + 1);
            }
        }
    }
}
