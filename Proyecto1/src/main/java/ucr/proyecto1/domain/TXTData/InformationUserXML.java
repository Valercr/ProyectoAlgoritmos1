package ucr.proyecto1.domain.TXTData;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularDoublyLinkedList;
import ucr.proyecto1.domain.list.ListException;

import javax.mail.MessagingException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InformationUserXML {
    public static final String FILE_NAME = "users.xml"; // Archivo XML para guardar usuarios.
    private CircularDoublyLinkedList users = new CircularDoublyLinkedList(); // Almacenar los usuarios en memoria.

    public InformationUserXML() {
        loadUsersFromFile(); // Cargar los usuarios existentes del archivo XML en la lista circular.
    }

    public void registerUser(int id, String name, String email, String password) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password); // Encriptar la contraseña.
        User user = new User(id, name, email, encryptedPassword); // Crear un nuevo usuario.
        users.append(user); // Agregar el usuario a la lista circular.
        appendUserToFile(user); // Agregar el usuario al archivo XML.
    }

    private void loadUsersFromFile() {
        File inputFile = new File(FILE_NAME);
        if (!inputFile.exists()) {
            return; // Si el archivo no existe, no hacer nada.
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document document = saxBuilder.build(inputFile);
            Element rootElement = document.getRootElement();
            List<Element> userList = rootElement.getChildren("user");

            for (Element userElement : userList) {
                int id = Integer.parseInt(userElement.getChildText("id"));
                String name = userElement.getChildText("name");
                String email = userElement.getChildText("email");
                String encryptedPassword = userElement.getChildText("password");
                users.append(new User(id, name, email, encryptedPassword));
                System.out.println("Loaded user: " + id + ", " + name + ", " + email);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void appendUserToFile(User user) {
        try {
            File inputFile = new File(FILE_NAME);
            Document document;
            Element rootElement;

            if (inputFile.exists()) {
                SAXBuilder saxBuilder = new SAXBuilder();
                document = saxBuilder.build(inputFile);
                rootElement = document.getRootElement();
            } else {
                rootElement = new Element("users");
                document = new Document(rootElement);
            }

            Element userElement = new Element("user");
            userElement.addContent(new Element("id").setText(String.valueOf(user.getId())));
            userElement.addContent(new Element("name").setText(user.getName()));
            userElement.addContent(new Element("email").setText(user.getEmail()));
            userElement.addContent(new Element("password").setText(user.getPassword()));

            rootElement.addContent(userElement);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(FILE_NAME));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUser(int id) {
        CircularDoublyLinkedList.Node current = users.getFirstNode();
        if (current == null) return null;

        do {
            User user = (User) current.getData();
            if (user.getId() == id) {
                System.out.println("Found user: " + user.getId() + ", " + user.getName() + ", " + user.getEmail());
                return user;
            }
            current = current.getNext();
        } while (current != users.getFirstNode());
        System.out.println("User not found with ID: " + id);
        return null;
    }

    public boolean authenticateUser(String email, String password) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password);
        try {
            int size = users.size();
            users.next();
            for (int i = 0; i < size; i++) {
                User user = users.getCurrent();
                if (user.getEmail().equals(email) && user.getPassword().equals(encryptedPassword)) {
                    return true;
                }
                users.next();
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void deleteUser(int id) {
        try {
            CircularDoublyLinkedList.Node node = users.getNode(users.indexOf(new User(id, null, null, null)));
            if (node != null) {
                users.remove(node.getData());
                System.out.println("User deleted successfully.");
                saveUsersToFile(); // Guardar la lista actualizada en el archivo.
            } else {
                System.out.println("User not found.");
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    public void addUser(int id, String name, String email, String password) throws MessagingException {
        User user = new User(id, name, email, password);
        users.add(user);
        appendUserToFile(user); // Guardar el nuevo usuario en el archivo.
    }

    public void updateInformation(int id, String name, String email) {
        try {
            CircularDoublyLinkedList.Node node = users.getNode(users.indexOf(new User(id, null, null, null)));
            if (node != null) {
                User user = (User) node.getData();
                if (name != null && !name.isEmpty()) {
                    user.setName(name);
                }
                if (email != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                System.out.println("Information updated successfully.");
                saveUsersToFile(); // Guardar la lista actualizada en el archivo.
            } else {
                System.out.println("User not found.");
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int id, String prevPassword, String newPassword) {
        try {
            CircularDoublyLinkedList.Node node = users.getNode(users.indexOf(new User(id, null, null, null)));
            if (node != null) {
                User user = (User) node.getData();
                if (user.getPassword().equals(prevPassword)) {
                    user.setPassword(newPassword);
                    System.out.println("Password successfully updated.");
                    saveUsersToFile(); // Guardar la lista actualizada en el archivo.
                } else {
                    System.out.println("Previous password incorrect.");
                }
            } else {
                System.out.println("User not found.");
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    private void saveUsersToFile() {
        try {
            Element rootElement = new Element("users");
            Document document = new Document(rootElement);

            CircularDoublyLinkedList.Node current = users.getFirstNode();
            if (current == null) return;

            do {
                User user = (User) current.getData();
                Element userElement = new Element("user");
                userElement.addContent(new Element("id").setText(String.valueOf(user.getId())));
                userElement.addContent(new Element("name").setText(user.getName()));
                userElement.addContent(new Element("email").setText(user.getEmail()));
                userElement.addContent(new Element("password").setText(user.getPassword()));
                rootElement.addContent(userElement);
                current = current.getNext();
            } while (current != users.getFirstNode());

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(FILE_NAME));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CircularDoublyLinkedList getUsers() {
        return users;
    }

    public List<User> getUserList() {
        List<User> userList = new ArrayList<>();
        if (!users.isEmpty()) {
            CircularDoublyLinkedList.Node current = users.getFirstNode();
            do {
                userList.add((User) current.getData());
                current = current.getNext();
            } while (current != users.getFirstNode());
        }
        return userList;
    }
}