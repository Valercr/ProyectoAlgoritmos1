package ucr.proyecto1.domain.XMLData;

import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.CircularDoublyLinkedList;
import ucr.proyecto1.domain.list.ListException;
import ucr.proyecto1.domain.list.Node;

import java.net.PasswordAuthentication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;

public class UserXMLData {

    private CircularDoublyLinkedList usersList;
    private final String emailFrom = "proyectoalgoritmos2024@gmail.com";
    private final String passwordFrom = "uojf wcod qcau hcoy"; //Esto lo genere en google, contraseñas de aplicaciones
    private String emailTo;
    private String subject;
    private String content;

    private Properties props;
    private Session session;
    private MimeMessage mEmail;

    public UserXMLData(){
        props = new Properties();
        this.usersList = new CircularDoublyLinkedList();
    }

    public void addUser(int id, String name, String email, String password) throws AddressException {
        User user = new User(id, name, email, password);
        usersList.add(user);
        emailNotification(email, id, password);
    }


    private void emailNotification(String email, int id, String password) throws AddressException {
        String hourDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        emailTo = email;
        subject = "Successful Registration";
        content = "Date and hour of registration: " + hourDate + "\nUser: " + id+ "\nPassword: " + password;

        //Simple mail transfer protocol
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.port", "587");
        props.setProperty("mail.smtp.user", emailFrom);
        props.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");
        props.setProperty("mail.smtp.auth", "true");

        session = Session.getDefaultInstance(props);

        try {
            mEmail = new MimeMessage(session);
            mEmail.setFrom(new InternetAddress(emailFrom));
            mEmail.setRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
            mEmail.setSubject(subject);
            mEmail.setText(content, "ISO-8859-1", "html");
        }catch (AddressException ex){
            Logger.getLogger(UserXMLData.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendEmail() throws MessagingException {
        Transport transport = session.getTransport("smtp");
        try {
            transport.connect("smtp.gmail.com", emailFrom, passwordFrom);
            transport.sendMessage(mEmail, mEmail.getAllRecipients());
        } finally {
            transport.close();
        }

        System.out.println("Email sent successfully.");
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

