package ucr.proyecto1.domain.TXTData;


import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularLinkedList;
import ucr.proyecto1.domain.list.ListException;

import java.io.*;
import java.util.List;

public class PasswordXML {

    private static final String FILE_NAME = "usersLogIn.xml"; // almacena la ruta del archivo XML que se usará para guardar los usuarios.
    private CircularLinkedList users = new CircularLinkedList(); // almacenar los usuarios en memoria durante la ejecución del programa.

    public PasswordXML() {
        loadUsersFromFile(); // cargar los usuarios existentes del archivo XML en la lista circular.
    }

    public void registerUser(String username, String password, String role) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password); // encriptar la contraseña.
        User user = new User(username, encryptedPassword, role); // crear un nuevo objeto User con los datos proporcionados y la contraseña encriptada.
        users.append(user); // agregar el nuevo usuario a la lista circular.
        appendUserToFile(user); // agregar el nuevo usuario al archivo XML.
    }

    private void loadUsersFromFile() {
        File inputFile = new File(FILE_NAME);
        if (!inputFile.exists()) {
            return; // si el archivo no existe, no hacer nada.
        }

        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document document = saxBuilder.build(inputFile);
            Element rootElement = document.getRootElement();
            List<Element> userList = rootElement.getChildren("user");

            for (Element userElement : userList) {
                String username = userElement.getChildText("username");
                String password = userElement.getChildText("password");
                String role = userElement.getChildText("role");
                users.append(new User(username, password, role));
                System.out.println("Loaded user: " + username);
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
            userElement.addContent(new Element("username").setText(user.getName()));
            userElement.addContent(new Element("password").setText(user.getPassword()));
            userElement.addContent(new Element("role").setText(user.getRole()));

            rootElement.addContent(userElement);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(FILE_NAME));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean authenticateUser(String email, String password) {
        String encryptedPassword = PasswordEncryption.encryptPassword(password);

        try {
            int size = users.size();
            users.next(); // Inicia la iteración desde el primer nodo de la lista circular.
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

    private void saveUsersToFile() {
        try {
            Element rootElement = new Element("users");
            Document document = new Document(rootElement);

            CircularLinkedList.Node current = users.head;
            if (current == null) return;

            do {
                User user = current.data;
                Element userElement = new Element("user");
                userElement.addContent(new Element("username").setText(user.getName()));
                userElement.addContent(new Element("password").setText(user.getPassword()));
                userElement.addContent(new Element("role").setText(user.getRole()));
                rootElement.addContent(userElement);
                current = current.next;
            } while (current != users.head);

            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(document, new FileWriter(FILE_NAME));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}