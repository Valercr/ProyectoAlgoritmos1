package ucr.proyecto1.domain.tree;

public class BTree implements Tree {
    private BTreeNode root; //unica entrada al arbol

    public BTree(){
        this.root = null;
    }

    @Override
    public int size() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
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
            throw new TreeException("Binary Tree is empty");
        }
        return binarySearch(root, element);
    }

    //método interno
    private boolean binarySearch(BTreeNode node, Object element){
        if(node==null)
            return false;
        else
        if(util.Utility.compare(node.data, element)==0)
            return true; //ya lo encontro
        else
            return binarySearch(node.left, element)
                    || binarySearch(node.right, element);
    }

    @Override
    public void add(Object element) {
        //this.root = add(root, element);
        this.root = add(root, element, "root");
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
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element);
            else //si es impart inserte por la der
                node.right = add(node.right, element);
        }
        return node;
    }

    private BTreeNode add(BTreeNode node, Object element, String path){
        if(node==null){ //si el arbol esta vacio
            node = new BTreeNode(element, path);
        }else{
            //debemos establecer algun criterio de insercion
            int value = util.Utility.getRandom(10);
            if(value%2==0) //si es par inserte por la izq
                node.left = add(node.left, element, path+"/left");
            else //si es impart inserte por la der
                node.right = add(node.right, element, path+"/right");
        }
        return node;
    }

    @Override
    public void remove(Object element) throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        root = remove(root, element);
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



    private Object getLeaf(BTreeNode node) {
        Object aux;
        if (node == null) return null;
        //preguntamos si es una hoja
        else if (node.left == null && node.right == null)
                return  node.data;
        else {
            aux = getLeaf(node.left);
            if (aux == null)
                aux = getLeaf(node.right);
            }

        return  aux;
    }

    private BTreeNode removeLeaf(BTreeNode node, Object value) {
        if (node == null){
            return null;
        } else if (node.left == null && node.right == null && util.Utility.compare(node.data, value)==0) {
            return null;
        }else {
            node.left = removeLeaf(node.left, value);
            node.right = removeLeaf(node.right, value);
        }
        return node;
    }

    @Override
    public int height(Object element) throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
        }
        return height(root, element, 0);
    }

    private int height(BTreeNode node, Object element, int counter){
        if (node == null) return 0; //significa que el elemento no existe
        else if (util.Utility.compare(node.data,element)==0) {
            return counter;
        }else return Math.max(height(node.left, element, ++counter), height(node.right, element, counter));
    }

    @Override
    public int height() throws TreeException {
        if(isEmpty())
            throw new TreeException("Binary Tree is empty");
        return height(root)-1;
    }

    private int height(BTreeNode node){
        if (node == null) return 0;
        else return Math.max(height(node.left), height(node.right)) + 1; //suma 1 porque baja un nivel
    }

    @Override
    public Object min() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return min(root);
    }

    private Object min(BTreeNode node) {
        if (node.left == null) {
            return node.data;
        }
        return min(node.left);
    }

    @Override
    public Object max() throws TreeException {
        if (isEmpty()) {
            throw new TreeException("Binary Tree is empty");
        }
        return max(root);
    }

    private Object max(BTreeNode node) {
        if (node.right == null) {
            return node.data;
        }
        return max(node.right);
    }


    @Override
    public String preOrder() throws TreeException {
       if(isEmpty()){
           throw new TreeException("Binary Tree is empty");
       }
       return preOrder(root)+"\n";
    }

    //node-left-right
    private String preOrder(BTreeNode node){
        String result="";
        if(node!=null){
            result =  node.data+"("+node.path+") ";
            result += preOrder(node.left);
            result += preOrder(node.right);
        }
        return result;
    }

    @Override
    public String inOrder() throws TreeException {
        if(isEmpty()){
            throw new TreeException("Binary Tree is empty");
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
            throw new TreeException("Binary Tree is empty");
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
            return true; // Un árbol vacío es balanceado
        }

        int leftHeight = height(node.left);
        int rightHeight = height(node.right);

        // Verifica si la diferencia de alturas entre el subárbol izquierdo y derecho es como máximo 1
        if (Math.abs(leftHeight - rightHeight) <= 1 && isBalanced(node.left) && isBalanced(node.right)) {
            return true;
        }

        return false;
    }


    //preOrder: recorre el árbol de la forma: nodo-izq-der
    //inOrder: recorre el árbol de la forma: izq-nodo-der
    //postOrder: recorre el árbol de la forma: izq-der-nodo
    @Override
    public String toString() {
        if(isEmpty())
            return "Binary Tree is empty";
        String result = "BINARY TREE TOUR...\n";
        result+="PreOrder: "+preOrder(root)+"\n";
        result+="InOrder: "+inOrder(root)+"\n";
        result+="PostOrder: "+postOrder(root)+"\n";
        return result;
    }
}
