package util;

import org.jdom2.JDOMException;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularDoublyLinkedList;
import ucr.proyecto1.domain.list.CircularLinkedList;
import ucr.proyecto1.domain.list.SinglyLinkedList;
import ucr.proyecto1.domain.tree.BTree;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Random;

public class Utility {


    public static CircularDoublyLinkedList usuarios;

    public static CircularLinkedList usuariosSistema;

    public static UserXMLData userData;

    public static String usuariosActivos= "";

    static {
        try {
            userData = new UserXMLData();
            usuarios = new CircularDoublyLinkedList();
            userData.llenarCircularList();
            usuariosSistema = new CircularLinkedList();

        }catch (IOException | JDOMException io){
            throw new RuntimeException(io);
        }


    }

    public static String format(double value) {
        return new DecimalFormat("###,###,###.##").format(value);
    }

    public static String $format(double value) {
        return new DecimalFormat("$###,###,###.##").format(value);
    }

    public static String show(int[] a, int size) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < size; i++) {
            result.append(a[i]);
        }
        return result.toString();
    }

    public static void fill(int[] a, int bound) {
        Random random = new Random();
        for (int i = 0; i < a.length; i++) {
            a[i] = random.nextInt(bound);
        }
    }

    public static int getRandom(int bound) {
        return new Random().nextInt(bound) + 1;
    }

    public static int compare(Object a, Object b) {
        switch (instanceOf(a, b)) {
            case "Integer":
                Integer int1 = (Integer) a;
                Integer int2 = (Integer) b;
                return int1.compareTo(int2);
            case "String":
                String st1 = (String) a;
                String st2 = (String) b;
                return st1.compareTo(st2);
            case "Character":
                Character c1 = (Character) a;
                Character c2 = (Character) b;
                return c1.compareTo(c2);
            case "SinglyLinkendList":
                SinglyLinkedList s1 = (SinglyLinkedList) a;
                SinglyLinkedList s2 = (SinglyLinkedList) b;
                return s1 == s2 ? 0 : -1;
            case "CircularDoublyLinkedList":
                CircularDoublyLinkedList cdl1 = (CircularDoublyLinkedList) a;
                CircularDoublyLinkedList cdl2 = (CircularDoublyLinkedList) b;
                return cdl1 == cdl2 ? 0 : -1;
            case "BTree":
                BTree bt1 = (BTree) a;
                BTree bt2 = (BTree) b;
                return bt1 == bt2 ? 0 : -1;
        }
        return 2; //Unknown
    }

    private static String instanceOf(Object a, Object b) {
        if (a instanceof Integer && b instanceof Integer) return "Integer";
        if (a instanceof String && b instanceof String) return "String";
        if (a instanceof Character && b instanceof Character) return "Character";
        if (a instanceof SinglyLinkedList && b instanceof SinglyLinkedList) return "SinglyLinkendList";
        if (a instanceof CircularDoublyLinkedList && b instanceof CircularDoublyLinkedList) return "CircularDoublyLinkedList";
        if (a instanceof BTree && b instanceof BTree) return "BTree";
        return "Unknown";
    }

    public static String roleUsuarioActivo = "";
    public static String nameUsuarioActivo = "";
    public static String emailUsuarioActivo = "";
    public static String passwordUsuarioActivo = "";
}