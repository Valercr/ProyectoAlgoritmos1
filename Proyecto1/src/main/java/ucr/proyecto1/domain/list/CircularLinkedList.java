package ucr.proyecto1.domain.list;

import ucr.proyecto1.domain.data.User;

public class CircularLinkedList {
    public Node head;
    private Node current;


    public int size() throws ListException {
        int count = 0;
        Node f = head;

        while (f != current) {//mientas que no llegue al último, por ser circular
            count++;
            f = f.next;
        }

        return count+1;//Para que cuente el último nodo +1
    }


    public void append(User data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
            current = head;
        } else {
            newNode.next = head;
            current.next = newNode;
            current = newNode;
        }
    }

    public User next() {
        if (current != null) {
            current = current.next;
        }
        return current != null ? current.data : null;
    }

    public User getCurrent() {
        return current != null ? current.data : null;
    }

    public static class Node {
        public User data;
        public Node next;

        Node(User data) {
            this.data = data;
        }
    }
}

