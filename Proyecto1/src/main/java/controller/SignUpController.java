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

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || idUser.isEmpty()) {
            showAlert("Error", "Complete todos los espacios");
        } else if (!password.equals(confirmPassword)) {
            showAlert("Trate de nuevo", "Las contraseñas no coinciden");
        } else {
            try {
                int id = Integer.parseInt(idUser);
                User user = new User(id, username, password, email, "usuario");
                userXMLData.addUser(user);

                showAlert("Registro Exitoso", "Usuario registrado correctamente");
                util.UtilityFX.loadPage("logIn.fxml", bp);
            } catch (NumberFormatException e) {
                showAlert("Error", "ID de usuario inválido");
            } catch (IOException | MessagingException e) {
                showAlert("Error", "Ocurrió un error al registrar el usuario: " + e.getMessage());
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
