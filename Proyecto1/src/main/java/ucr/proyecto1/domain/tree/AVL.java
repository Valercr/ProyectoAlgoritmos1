package ucr.proyecto1.domain.tree;

import ucr.proyecto1.domain.data.Course;

public class AVL implements Tree {
    private BTreeNode root; //unica entrada al arbol

    private String sequence;

    public AVL() {
        this.root = null;
        this.sequence = "";
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return size(root);
    }

    public BTreeNode getRoot() {
        return root;
    }

    private int size(BTreeNode node){
        if(node==null)
            return 0;
        else
            return 1+size(node.left)+size(node.right);
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
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else if(util.Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else if (util.Utility.compare(element, node.data)<0)
            return binarySearch(node.left, element);
        else return binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        this.root = add(root, element, "root");
    }

    private BTreeNode add(BTreeNode node, Object element, String sequence) {
        if (node == null) { // Si el arbol esta vacio
            node = new BTreeNode(element, "Added as " + sequence);
        } else {
            if (util.Utility.compare(element, node.data) < 0)
                node.left = add(node.left, element, sequence + "left. ");
            else if (util.Utility.compare(element, node.data) > 0)
                node.right = add(node.right, element, sequence + "right. ");
        }
        // Se determina si se requiere rebalanceo
        node = reBalance(node, element, sequence);
        return node;
    }


    // Método para actualizar la altura de un nodo
    private void updateHeight(BTreeNode node) {
        if (node != null) {
            node.height = Math.max(height(node.left), height(node.right)) + 1;
        }
    }

    // Método para calcular el factor de balance de un nodo
    private int getBalanceFactor(BTreeNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Rotación simple a la izquierda
    private BTreeNode leftRotate(BTreeNode node) {
        BTreeNode newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        // Actualiza alturas
        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    //Rotación simple a la derecha
    private BTreeNode rightRotate(BTreeNode node) {
        BTreeNode newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        //Actualiz alturas
        updateHeight(node);
        updateHeight(newRoot);

        return newRoot;
    }

    //Rebalanceo del nodo después de la inserción/eliminación
    private BTreeNode reBalance(BTreeNode node, Object element, String sequence) {
        int balance = getBalanceFactor(node);

        // Left Left Case
        if (balance > 1 && util.Utility.compare(element, node.left.data) < 0) {
            node.path += " Simple Right Rotation ";
            this.sequence += node.path + "\n";
            return rightRotate(node);
        }

        // Right Right Case
        if (balance < -1 && util.Utility.compare(element, node.right.data) > 0) {
            node.path += " Simple Left Rotation ";
            this.sequence += node.path + "\n";
            return leftRotate(node);
        }

        // Left Right Case
        if (balance > 1 && util.Utility.compare(element, node.left.data) > 0) {
            node.path += " Double Left/Right Rotation ";
            this.sequence += node.path + "\n";
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && util.Utility.compare(element, node.right.data) < 0) {
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

    public BTreeNode search(int key) {
        return search(root, key);
    }

    private BTreeNode search(BTreeNode node, int key) {
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
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element) {
        if (node == null) {
            return node;
        }

        if (util.Utility.compare(element, node.data) < 0) {
            node.left = remove(node.left, element);
        } else if (util.Utility.compare(element, node.data) > 0) {
            node.right = remove(node.right, element);
        } else {
            if ((node.left == null) || (node.right == null)) {
                BTreeNode temp = null;
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
                BTreeNode temp = minValueNode(node.right);
                node.data = temp.data;
                node.right = remove(node.right, temp.data);
            }
        }

        if (node == null) {
            return node;
        }

        node.height = Math.max(height(node.left), height(node.right)) + 1;

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

    private BTreeNode minValueNode(BTreeNode node) {
        BTreeNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter){
        if (node == null) return 0; //significa que el elemento no existe
        else if (util.Utility.compare(node.data,element)==0) {
            return counter;
        } else if (util.Utility.compare(element, node.data)<0) {
            return height(node.left, element, ++counter);
        }else 
            return height(node.right, element, ++counter);
        //else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("AVL Binary Search Tree is empty");
        return height(root)-1;
    }

    private int height(BTreeNode node){
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1; //suma 1 porque baja un nivel
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return min(root);
    }

    private Object min(BTreeNode node) {
        
        if (node.left!=null)
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

    private Object max(BTreeNode node) {
        if (node.right != null) 
            return max(node.right);
        return node.data;
    }


    @Override
    public String preOrder() throws TreeException {
       if(isEmpty()){
           throw new TreeException("AVL Binary Search Tree is empty");
       }
       return preOrder(root)+"\n";
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data+" ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return inOrder(root)+"\n";
    }

    //left-node-right
    private String inOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result  = inOrder(node.left);
            result += node.data+" ";
            result += inOrder(node.right);
        }
        return result;
    }

    @Override
    public String postOrder() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        return postOrder(root) + "\n";
    }

    //left-right-node
    private String postOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result = postOrder(node.left);
            result += postOrder(node.right);
            result += node.data+" ";

        }
        return result;
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(BTreeNode node) {
        if (node == null) {
            return true;
        }

        int balanceFactor = Math.abs(getBalanceFactor(node));
        if (balanceFactor > 1) {
            return false;
        }

        return isBalanced(node.left) && isBalanced(node.right);
    }

    public int getRandomValue() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("AVL Binary Search Tree is empty");
        }
        int treeSize = size();
        int randomIndex = util.Utility.getRandom(treeSize);
        return getRandomValue(root, randomIndex, new Counter());
    }

    private int getRandomValue(BTreeNode node, int targetIndex, Counter counter) {
        if (node == null) {
            return -1;
        }
        int leftValue = getRandomValue(node.left, targetIndex, counter);
        if (leftValue != -1) {
            return leftValue;
        }
        counter.increment();
        if (counter.getCount() - 1 == targetIndex) {
            return (int) node.data;
        }
        return getRandomValue(node.right, targetIndex, counter);
    }

    private static class Counter {
        private int count;

        public Counter() {
            this.count = 0;
        }

        public int getCount() {
            return count;
        }

        public void increment() {
            count++;
        }
    }


    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo
    @Override
    public String toString() {
        if(isEmpty())
            return "AVL Binary Search Tree is empty";
        String result = "AVL BINARY SEARCH TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }
}
