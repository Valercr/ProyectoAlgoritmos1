package ucr.proyecto1.domain.tree;

import ucr.proyecto1.domain.data.Course;

import java.util.ArrayList;
import java.util.List;

public class AVL<T extends Comparable<T>> implements Tree {
    private BTreeNode<T> root;
    private String sequence;

    public AVL() {
        this.root = null;
        this.sequence = "";
    }

    @Override
    public int size() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return size(root);
    }

    private int size(BTreeNode<T> node) {
        if (node == null) return 0;
        else return 1 + size(node.left) + size(node.right);
    }

    @Override
    public void clear() {
        root = null;
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public boolean contains(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return binarySearch(root, element);
    }

    private boolean binarySearch(BTreeNode<T> node, Object element) {
        if (node == null) return false;
        else if (node.data.compareTo((T) element) == 0) return true;
        else if (((T) element).compareTo(node.data) < 0) return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        this.root = add(root, new BTreeNode<T>((T) element), "root");
    }

    private BTreeNode<T> add(BTreeNode<T> node, BTreeNode<T> element, String sequence) {
        if (node == null) {
            element.path = "Added as " + sequence;
            return element;
        }

        if (element.data.compareTo(node.data) < 0)
            node.left = add(node.left, element, sequence + "left. ");
        else if (element.data.compareTo(node.data) > 0)
            node.right = add(node.right, element, sequence + "right. ");

        node = reBalance(node, element.data, sequence);
        return node;
    }

    private void updateHeight(BTreeNode<T> node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    private int getBalanceFactor(BTreeNode<T> node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    private BTreeNode<T> leftRotate(BTreeNode<T> node) {
        BTreeNode<T> newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private BTreeNode<T> rightRotate(BTreeNode<T> node) {
        BTreeNode<T> newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;
        updateHeight(node);
        updateHeight(newRoot);
        return newRoot;
    }

    private BTreeNode<T> reBalance(BTreeNode<T> node, T element, String sequence) {
        int balance = getBalanceFactor(node);

        if (balance > 1 && element.compareTo(node.left.data) < 0) {
            node.path += " Simple Right Rotation ";
            this.sequence += node.path + "\n";
            return rightRotate(node);
        }

        if (balance < -1 && element.compareTo(node.right.data) > 0) {
            node.path += " Simple Left Rotation ";
            this.sequence += node.path + "\n";
            return leftRotate(node);
        }

        if (balance > 1 && element.compareTo(node.left.data) > 0) {
            node.path += " Double Left/Right Rotation ";
            this.sequence += node.path + "\n";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if (balance < -1 && element.compareTo(node.right.data) < 0) {
            node.path += " Double Right/Left Rotation ";
            this.sequence += node.path + "\n";
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    public String getSequence() {
        return sequence;
    }

    public BTreeNode<T> search(int key) {
        return search(root, key);
    }

    private BTreeNode<T> search(BTreeNode<T> node, int key) {
        if (node == null || ((Course) node.getData()).getId() == key) {
            return node;
        }
        if (key < ((Course) node.getData()).getId()) {
            return search(node.getLeft(), key);
        } else {
            return search(node.getRight(), key);
        }
    }

    @Override
    public void remove(Object element) throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = remove(root, element);
    }

    private BTreeNode<T> remove(BTreeNode<T> node, Object element) {
        if (node == null) {
            return node;
        }

        if (((T) element).compareTo(node.data) < 0) {
            node.left = remove(node.left, element);
        } else if (((T) element).compareTo(node.data) > 0) {
            node.right = remove(node.right, element);
        } else {
            if ((node.left == null) || (node.right == null)) {
                BTreeNode<T> temp = null;
                if (temp == node.left) {
                    temp = node.right;
                } else {
                    temp = node.left;
                }

                if (temp == null) {
                    temp = node;
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                BTreeNode<T> temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }

        if (node == null) {
            return node;
        }

        updateHeight(node);

        int balance = getBalanceFactor(node);

        if (balance > 1 && getBalanceFactor(node.left) >= 0) {
            return rightRotate(node);
        }
        if (balance > 1 && getBalanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && getBalanceFactor(node.right) <= 0) {
            return leftRotate(node);
        }
        if (balance < -1 && getBalanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private BTreeNode<T> minValueNode(BTreeNode<T> node) {
        BTreeNode<T> current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    @Override
    public int height(Object element) throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode<T> node, Object element, int counter) {
        if (node == null) return 0;
        else if (node.data.compareTo((T) element) == 0) {
            return counter;
        } else if (((T) element).compareTo(node.data) < 0) {
            return height(node.left, element, ++counter);
        } else
            return height(node.right, element, ++counter);
    }

    @Override
    public int height() throws TreeException {
        if (isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return height(root) - 1;
    }

    private int height(BTreeNode<T> node) {
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1;
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return min(root);
    }

    private T min(BTreeNode<T> node) {
        if (node.left != null)
            return min(node.left);
        return node.data;
    }

    @Override
    public Object max() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return max(root);
    }

    private T max(BTreeNode<T> node) {
        if (node.right != null)
            return max(node.right);
        return node.data;
    }

    @Override
    public String preOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return preOrder(root) + "\n";
    }

    private String preOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result = node.data + ", ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return inOrder(root) + "\n";
    }

    private String inOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result += inOrder(node.left);
            result += node.data + ", ";
            result += inOrder(node.right);
        }
        return result;
    }

    public List<T> inOrderList() throws TreeException {
        if (isEmpty()) {
            return new ArrayList<>(); // Devuelve una lista vacía en lugar de lanzar una excepción
        }
        List<T> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    private void inOrderTraversal(BTreeNode<T> node, List<T> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.data);
            inOrderTraversal(node.right, result);
        }
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return postOrder(root) + "\n";
    }

    private String postOrder(BTreeNode<T> node) {
        String result = "";
        if (node != null) {
            result += postOrder(node.left);
            result += postOrder(node.right);
            result += node.data + ", ";
        }
        return result;
    }
}