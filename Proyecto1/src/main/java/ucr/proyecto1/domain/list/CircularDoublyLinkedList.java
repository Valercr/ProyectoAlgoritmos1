package ucr.proyecto1.domain.list;

import ucr.proyecto1.domain.data.User;
import util.Utility;

import java.io.FileReader;

public class CircularDoublyLinkedList {
    private Node first; //apuntador al inicio de la lista
    private Node last; //apuntador al final de la lista

    public CircularDoublyLinkedList() {
        this.first = null; //la lista no existe
        this.last = null; //la lista no existe
    }

    public int size() throws ListException {
        int count = 0;
        Node current = first;

        while (current != last) {//mientas que no llegue al último, por ser circular
            count++;
            current = current.next;
        }

        return count+1;//Para que cuente el último nodo +1
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
        this.first = null; //anulamos la lista
        this.last = null; //anulamos la lista
    }

    public boolean isEmpty() {
        return this.first == null; //si es nulo está vacía
    }


    public boolean contains(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        do {
            if (Utility.compare(current.data, element) == 0) {
                return true; // Elemento encontrado en la lista
            }
            current = current.next;
        } while (current != first);
        return false;
    }

    public void add(Object element) {
        Node newNode = new Node((User) element);
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

    public int indexOf(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        int index = 0;
        do {
            if (util.Utility.compare(current.data, element) == 0) {
                return index;
            }
            index++;
            current = current.next;
        } while (current != first);
        return -1; //indica que el elemento no existe
    }

    public Node getNode(int index) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Doubly Linked List is Empty");
        }
        Node current = first;
        int i = 0; // índice del primer nodo
        do {
            if (i == index) {
                return current;
            }
            i++;
            current = current.next;
        } while (current != first);
        return null; //si llega aquí es porque no encontró el índice
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
        return result.append(aux.data).toString();//Agrega la data del último nodo
    }


    public void remove(Object element) throws ListException {
        if (isEmpty()) {
            throw new ListException("Circular Linked List is Empty");
        }
        //CASO 1 el elemento a suprimir está al inicio:
        if (Utility.compare(first.data,element)==0){
            first=first.next; //saltamos al primer nodo
        }else {  //caso 2 suprimir el ultimo
            Node prev = first; //dejo un apuntador al nodo anterior
            Node aux= first.next;
            while (aux!=last && !(Utility.compare(aux.data, element)==0)){
                prev = aux;
                aux= aux.next;
            }
            //se sale cuando encuentra el elemento
            if (Utility.compare(aux.data, element)==0){
                //ya lo encontró procede a desenlazar el nodo
                prev.next= aux.next;
                //mantengo el doble enlace
                aux.next.prev = prev;
            }

            //Que pasas si el elemento a suprimir esta en el ultimo nodo
            if (aux==last && Utility.compare(aux.data, element)==0){
                last = prev; //desenlaza el ultimo nodo

            }


        }
        //mantengo el enlace circular y doble
        last.next = first;
        first.prev = last;

        //Otro caso:
        //Si solo queda un nodo y es el que quiero eliminar
        if (first == last && Utility.compare(first.data, element)==0) {
            clear();//anulo la lista
        }
    }

}