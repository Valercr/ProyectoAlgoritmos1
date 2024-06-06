package ucr.proyecto1.domain.tree;

public class BTreeNode {
    public Object data;
    public BTreeNode left, right; // hijo izq, hijo der
    public String path; // valores: root/left/right
    public int height; // altura del nodo

    // Constructor
    public BTreeNode(Object data) {
        this.data = data;
        this.left = this.right = null;
        this.height = 1; // altura inicial al crear el nodo
    }

    // Constructor sobrecargado
    public BTreeNode(Object data, String path) {
        this.data = data;
        this.path = path;
        this.left = this.right = null;
        this.height = 1; // altura inicial al crear el nodo
    }

    public Object getData() {
        return data;
    }

    public BTreeNode getLeft() {
        return left;
    }

    public BTreeNode getRight() {
        return right;
    }
}
