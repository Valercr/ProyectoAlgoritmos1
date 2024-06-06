package ucr.proyecto1.domain.tree;

public interface Tree {
    public int size() throws TreeException;
    public void clear();
    public boolean isEmpty();
    public boolean contains(Object element) throws TreeException;
    public void add (Object element);
    public void remove(Object element) throws TreeException;
    public int height(Object element) throws TreeException;
    public int height() throws TreeException;
    public Object min() throws TreeException;
    public Object max() throws TreeException;
    public String preOrder() throws TreeException;
    public String inOrder() throws TreeException;
    public String postOrder() throws TreeException;
}
