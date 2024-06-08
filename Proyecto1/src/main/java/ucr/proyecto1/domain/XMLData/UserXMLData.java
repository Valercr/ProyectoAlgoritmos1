package ucr.proyecto1.domain.XMLData;

import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularDoublyLinkedList;
import ucr.proyecto1.domain.list.ListException;
import ucr.proyecto1.domain.list.Node;

import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class UserXMLData {

    private CircularDoublyLinkedList usersList;
    private final String from = "proyectoalgoritmos2024@gmail.com";
    private final String pass = "uojf wcod qcau hcoy"; //Esto lo genere en google, contraseñas de aplicaciones

    public UserXMLData() {
        this.usersList = new CircularDoublyLinkedList();
    }

    public void addUser(int id, String name, String email, String password) {
        User user = new User(id, name, email, password);
        usersList.add(user);
        emailNotification(email, id, password);
    }

    private void emailNotification(String email, int id, String password) {
        String hourDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String register = "Successful Registration";
        String sentence = "Date and hour of registration: " + hourDate + "\nUser: " + id+ "\nPassword: " + password;

        //Configuración del correo (SMTP)
        String host = "smtp.example.com"; // Cambia esto con tu servidor SMTP

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Aquí utilizamos la variable de instancia `from` y `pass`
        final String emailFrom = this.from;
        final String emailPass = this.pass;

//        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
//            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(emailFrom, emailPass);
//            }
//        });

//        try {
//            MimeMessage message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
//            message.setSubject(register);
//            message.setText(sentence);
//            Transport.send(message);
//            System.out.println("Email sent successfully.");
//        } catch (MessagingException mex) {
//            mex.printStackTrace();
//        }
    }

    public boolean logIn(int id, String password) {
        try {
            Node node = usersList.getNode(usersList.indexOf(new User(id, null, null, null)));
            if (node != null) {
                User user = (User) node.getData();
                if (user.getPassword().equals(password)) {
                    System.out.println("Log in successful.");
                    return true;
                }
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
        System.out.println("Incorrect user or password.");
        return false;
    }

    public void updateInformation(int id, String name, String email) {
        try {
            Node node = usersList.getNode(usersList.indexOf(new User(id, null, null, null)));
            if (node != null) {
                User user = (User) node.getData();
                if (name != null && !name.isEmpty()) {
                    user.setName(name);
                }
                if (email != null && !email.isEmpty()) {
                    user.setEmail(email);
                }
                System.out.println("Information updated successful.");
            } else {
                System.out.println("User not found.");
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
    }

    public void changePassword(int id, String prevPassword, String newPassword) {
        try {
            Node node = usersList.getNode(usersList.indexOf(new User(id, null, null, null)));
            if (node != null) {
                User user = (User) node.getData();
                if (user.getPassword().equals(prevPassword)) {
                    user.setPassword(newPassword);
                    System.out.println("Password successful updated.");
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
}
