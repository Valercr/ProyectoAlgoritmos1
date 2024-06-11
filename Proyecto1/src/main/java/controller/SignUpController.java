package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;
import ucr.proyecto1.domain.TXTData.PasswordEncryption;
import ucr.proyecto1.domain.XMLData.Email;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;

import javax.mail.MessagingException;
import java.io.IOException;

import static util.UtilityFX.loadPage;
public class SignUpController {

    @FXML
    private TextField txtField_userName;
    @FXML
    private TextField txtField_email;
    @FXML
    private TextField txtField_idUser;
    @FXML
    private PasswordField passwordField;
    @FXML
    private BorderPane bp;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField txtFieldConfirmPassword;
    @FXML
    private TextField txtFieldPassword;

    private UserXMLData userXMLData;
    private Alert alert;

    public SignUpController() {
        try {
            userXMLData = new UserXMLData();
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        txtFieldPassword.setVisible(false);
        txtFieldConfirmPassword.setVisible(false);
    }

    @FXML
    public void signUpOnAction(ActionEvent actionEvent) {
        String username = txtField_userName.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = txtField_email.getText();
        String idUser = txtField_idUser.getText();

        // Verificar que no esté ningún textField vacío
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || idUser.isEmpty()) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Complete todos los espacios");
            alert.showAndWait();
        } else if (!password.equals(confirmPassword)) { // Verificar si las contraseñas no coinciden
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Trate de nuevo");
            alert.setHeaderText("Las contraseñas no coinciden");
            alert.showAndWait();
        } else {
            // Crear un nuevo usuario y guardar la información usando UserXMLData
            try {
                int id = Integer.parseInt(idUser);
                String encryptedPassword = PasswordEncryption.encryptPassword(password);
                User user = new User(id, username, email, encryptedPassword, "usuario"); // Asumiendo el rol "usuario" por defecto
                userXMLData.addUser(user);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registro Exitoso");
                alert.setHeaderText("Usuario registrado correctamente");
                alert.showAndWait();

                // Cargar la página de inicio de sesión
                util.UtilityFX.loadPage("logIn.fxml", bp);
            } catch (NumberFormatException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("ID de usuario inválido");
                alert.showAndWait();
            } catch (IOException | MessagingException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Ocurrió un error al registrar el usuario");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
            }
        }
    }

    @FXML
    public void logInOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("logIn.fxml", bp); // Cargar LogIn
    }

    @FXML
    public void changeVisibility(ActionEvent actionEvent) {
        if (showPassword.isSelected()) {
            txtFieldPassword.setText(passwordField.getText());
            txtFieldPassword.setVisible(true);
            passwordField.setVisible(false);
            txtFieldConfirmPassword.setText(confirmPasswordField.getText());
            txtFieldConfirmPassword.setVisible(true);
            confirmPasswordField.setVisible(false);
        } else {
            txtFieldPassword.setVisible(false);
            passwordField.setVisible(true);
            txtFieldConfirmPassword.setVisible(false);
            confirmPasswordField.setVisible(true);
        }
    }
}