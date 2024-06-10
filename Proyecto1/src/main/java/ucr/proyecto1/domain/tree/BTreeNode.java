package ucr.proyecto1.domain.tree;
public class BTreeNode<T> {
    public T data;
    public BTreeNode<T> left, right;
    public String path;
    public int height;

    // Constructor
    public BTreeNode(T data) {
        this.data = data;
        this.left = this.right = null;
        this.height = 1; // altura inicial al crear el nodo
    }

    // Constructor sobrecargado
    public BTreeNode(T data, String path) {
        this.data = data;
        this.path = path;
        this.left = this.right = null;
        this.height = 1; // altura inicial al crear el nodo
    }

    public T getData() {
        return data;
    }

    public BTreeNode<T> getLeft() {
        return left;
    }

    public BTreeNode<T> getRight() {
        return right;
    }
}
