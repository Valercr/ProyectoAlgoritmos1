package ucr.proyecto1.domain.list;

import ucr.proyecto1.domain.data.User;
import util.Utility;

import java.io.FileReader;

public class CircularDoublyLinkedList {
    private Node first;
    private Node last;

    public CircularDoublyLinkedList() {
        this.first = null;
        this.last = null;
    }

    public int size() throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        int count = 0;
        Node current = first;
        while (current != last) {
            count++;
            current = current.next;
        }
        return count + 1;
    }

    public void append(User data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            first = last = newNode;
            first.next = first.prev = first;
        } else {
            last.next = newNode;
            newNode.prev = last;
            newNode.next = first;
            first.prev = newNode;
            last = newNode;
        }
    }

    public User next() {
        if (last != null) {
            last = last.next;
            return last.data;
        }
        return null;
    }

    public User getCurrent() {
        return last != null ? last.data : null;
    }

    public static class Node {
        public Node prev;
        User data;
        Node next;

        Node(User data) {
            this.data = data;
        }

        public User getData() {
            return data;
        }

        public Node getNext() {
            return next;
        }
    }

    public void clear() {
        this.first = null;
        this.last = null;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public boolean contains(User element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        do {
            if (current.data.equals(element)) {
                return true;
            }
            current = current.next;
        } while (current != first);
        return false;
    }

    public void add(User element) {
        Node newNode = new Node(element);
        if (isEmpty()) {
            first = last = newNode;
            first.next = first.prev = first;
        } else {
            last.next = newNode;
            newNode.prev = last;
            newNode.next = first;
            first.prev = newNode;
            last = newNode;
        }
    }

    public int indexOf(User element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        int index = 0;
        do {
            if (current.data.equals(element)) {
                return index;
            }
            index++;
            current = current.next;
        } while (current != first);
        return -1;
    }

    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        int i = 0;
        do {
            if (i == index) {
                return current;
            }
            i++;
            current = current.next;
        } while (current != first);
        return null;
    }

    public Node getFirstNode() {
        return first;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Circular Doubly Linked List Content\n\n");
        Node aux = first;
        while (aux != last) {
            result.append(aux.data).append("\n");
            aux = aux.next;
        }
        return result.append(aux.data).toString();
    }

    public void remove(User element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        if (first.data.equals(element)) {
            first = first.next;
            first.prev = last;
            last.next = first;
            if (first == last) {
                clear();
            }
        } else {
            Node prev = first;
            Node aux = first.next;
            while (aux != first && !aux.data.equals(element)) {
                prev = aux;
                aux = aux.next;
            }
            if (aux.data.equals(element)) {
                prev.next = aux.next;
                aux.next.prev = prev;
                if (aux == last) {
                    last = prev;
                }
            }
        }
    }
}
