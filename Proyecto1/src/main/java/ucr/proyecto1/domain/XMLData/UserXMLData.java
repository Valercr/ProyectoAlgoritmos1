package ucr.proyecto1.domain.XMLData;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import ucr.proyecto1.domain.TXTData.PasswordEncryption;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularDoublyLinkedList;
import ucr.proyecto1.domain.list.ListException;
import util.Utility;

import javax.mail.MessagingException;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class UserXMLData {
    private String filePath;
    private Element root;
    private Document document;
    private Email emailSender;
    private CircularDoublyLinkedList userList;


    public UserXMLData() throws IOException, JDOMException {
        this.filePath = "users";
        this.userList = Utility.usuarios;
        this.emailSender = new Email();
        File file = new File(filePath);
        if (!file.exists()) {
            this.root = new Element("users");
            this.document = new Document(root);
            save();
        } else {
            SAXBuilder saxBuilder = new SAXBuilder();
            this.document = saxBuilder.build(file);
            this.root = document.getRootElement();
        }
        loadUsersToLinkedList();
    }

    public ArrayList<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        List<Element> userElements = root.getChildren("user");
        for (Element userElement : userElements) {
            users.add(buildUserFromElement(userElement));
        }
        return users;
    }

    public User findUserByUsername(String username) {
        List<Element> userElements = root.getChildren("user");
        for (Element userElement : userElements) {
            if (userElement.getChildText("name").equals(username)) {
                return buildUserFromElement(userElement);
            }
        }
        return null;
    }

    private void save() throws IOException {
        XMLOutputter xmlOutputter = new XMLOutputter(Format.getPrettyFormat());
        xmlOutputter.output(document, new PrintWriter(filePath));
    }

    private void loadUsersToLinkedList() {
        List<Element> userElements = root.getChildren("user");
        for (Element userElement : userElements) {
            User user = buildUserFromElement(userElement);
            userList.add(user);
        }
    }

    public void addUser(User user) throws IOException, MessagingException {
        validateUser(user);
        String encryptedPassword = PasswordEncryption.encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);

        Element userElement = createUserElement(user);
        root.addContent(userElement);
        userList.add(user);
        save();
        emailSender.sendConfirmationEmail(user.getEmail(), user.getId(), user.getPassword());
    }

    public void updateUser(User updatedUser) throws IOException, ListException {
        validateUser(updatedUser);
        List<Element> userElements = root.getChildren("user");
        boolean userFound = false;
        for (Element userElement : userElements) {
            if (userElement.getAttributeValue("id").equals(String.valueOf(updatedUser.getId()))) {
                updateUserElement(userElement, updatedUser);
                userList.remove(updatedUser);
                userList.add(updatedUser);
                save();
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            throw new IllegalArgumentException("The user to update does not exist in the XML.");
        }
    }

    public void removeUser(int userId) throws IOException, ListException {
        List<Element> userElements = root.getChildren("user");
        boolean userFound = false;
        for (Element userElement : userElements) {
            if (userElement.getAttributeValue("id").equals(String.valueOf(userId))) {
                User userToRemove = buildUserFromElement(userElement);
                root.removeContent(userElement);
                userList.remove(userToRemove);
                save();
                userFound = true;
                break;
            }
        }
        if (!userFound) {
            throw new IllegalArgumentException("The user to remove does not exist in the XML.");
        }
    }

    public User findUserById(int userId) {
        List<Element> userElements = root.getChildren("user");
        for (Element userElement : userElements) {
            if (userElement.getAttributeValue("id").equals(String.valueOf(userId))) {
                return buildUserFromElement(userElement);
            }
        }
        return null;
    }

    private Element createUserElement(User user) {
        Element userElement = new Element("user");
        userElement.setAttribute("id", String.valueOf(user.getId()));

        Element nameElement = new Element("name");
        nameElement.addContent(user.getName());

        Element passwordElement = new Element("password");
        passwordElement.addContent(user.getPassword());

        Element emailElement = new Element("email");
        emailElement.addContent(user.getEmail());

        Element roleElement = new Element("role");
        roleElement.addContent(user.getRole());

        userElement.addContent(nameElement);
        userElement.addContent(passwordElement);
        userElement.addContent(emailElement);
        userElement.addContent(roleElement);

        return userElement;
    }

    private void updateUserElement(Element userElement, User updatedUser) {
        userElement.getChild("name").setText(updatedUser.getName());
        userElement.getChild("password").setText(updatedUser.getPassword());
        userElement.getChild("email").setText(updatedUser.getEmail());
        userElement.getChild("role").setText(updatedUser.getRole());
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null.");
        }
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("User password cannot be null or empty.");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }
        if (user.getRole() == null || user.getRole().isEmpty()) {
            throw new IllegalArgumentException("User role cannot be null or empty.");
        }
    }

    private User buildUserFromElement(Element userElement) {
        int id = Integer.parseInt(userElement.getAttributeValue("id"));
        String name = getElementText(userElement, "name");
        String password = getElementText(userElement, "password");
        String email = getElementText(userElement, "email");
        String role = getElementText(userElement, "role");

        return new User(id, name, password, email, role);
    }

    public   void llenarCircularList(){

        ArrayList<User> userList = getAllUsers();
        for (int i = 0; i < userList.size() ; i++) {
            Utility.usuarios.add(userList.get(i));
        }

    }
    private String getElementText(Element parentElement, String childName) {
        Element childElement = parentElement.getChild(childName);
        return (childElement != null) ? childElement.getText() : "";
    }
}