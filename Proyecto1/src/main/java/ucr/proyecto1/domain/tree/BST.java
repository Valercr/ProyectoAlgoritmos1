package ucr.proyecto1.domain.tree;

public class BST implements Tree {
    private BTreeNode root; //unica entrada al arbol

    public BST(){
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
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
            throw new TreeException("Binary Search Tree is empty");
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
        this.root = add(root, element);
    }

    /**
     * else if(node.left==null){
     *    node.left = add(node.left, element);
     * }else if(node.right==null){
     *     node.right = add(node.right, element);
     * }
     * */
    private BTreeNode add(BTreeNode node, Object element){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element);
        }else{
            if(util.Utility.compare(element, node.data)<0)
                node.left = add(node.left, element);
            else if(util.Utility.compare(element, node.data)>0)
                node.right = add(node.right, element);
        }
        return node;
    }


    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Search Tree is empty");
        root = remove(root,element);
    }

    private BTreeNode remove(BTreeNode node, Object element) {
        if (node == null)
            return null;

        if (util.Utility.compare(element, node.data) < 0) {
            node.left = remove(node.left, element);
        } else if (util.Utility.compare(element, node.data) > 0) {
            node.right = remove(node.right, element);
        } else {
            // Caso 1: Nodo sin hijos
            if (node.left == null && node.right == null) {
                return null;
            }
            // Caso 2: Nodo con un hijo
            else if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            }
            // Caso 3: Nodo con dos hijos
            else {
                Object minValue = min(node.right);
                node.data = minValue;
                node.right = remove(node.right, minValue);
            }
        }
        return node;
    }



    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Search Tree is empty");
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
            throw new TreeException("Binary Search Tree is empty");
        return height(root)-1;
    }

    private int height(BTreeNode node){
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1; //suma 1 porque baja un nivel
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
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
            throw new TreeException("Binary Search Tree is empty");
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
           throw new TreeException("Binary Search Tree is empty");
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
            throw new TreeException("Binary Search Tree is empty");
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
            throw new TreeException("Binary Search Tree is empty");
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
        int leftHeight = height(node.left);
        int rightHeight = height(node.right);
        return Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(node.left) && isBalanced(node.right);
    }


    public int getRandomValue() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Search Tree is empty");
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
            return "Binary Search Tree is empty";
        String result = "BINARY SEARCH TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }
    //si
}
