package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.InformationUserXML;
import ucr.proyecto1.domain.XMLData.Email;
import ucr.proyecto1.domain.data.User;

import static util.UtilityFX.loadPage;

public class SignUpController
{
    @javafx.fxml.FXML
    private TextField txtField_userName;
    @javafx.fxml.FXML
    private TextField txtField_email;
    @javafx.fxml.FXML
    private TextField txtField_idUser;
    InformationUserXML informationUserXML;
    Alert alert;
    @javafx.fxml.FXML
    private PasswordField passwordField;
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private PasswordField confirmPasswordField;
    @javafx.fxml.FXML
    private CheckBox showPassword;
    @javafx.fxml.FXML
    private TextField txtFieldConfirmPassword;
    @javafx.fxml.FXML
    private TextField txtFieldPassword;
    User user;
    private Email userXMLData;

    public SignUpController() {
        informationUserXML = new InformationUserXML();
        userXMLData = new Email();
    }

    @javafx.fxml.FXML
    public void initialize() {

    }

    @javafx.fxml.FXML
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
            // Crear un nuevo usuario y guardar la información usando ArchiveInformationUser
            int id = Integer.parseInt(idUser);
            User user = new User(id, username, email, password);
            informationUserXML.registerUser(id, username, email, password);

            // Enviar correo de confirmación
            userXMLData.sendConfirmationEmail(email, id, password);
        }
    }



    @javafx.fxml.FXML
    public void logInOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("logIn.fxml", bp);//Cargar LogIn

    }

    @javafx.fxml.FXML
    public void changeVisibility(ActionEvent actionEvent) {
        if (showPassword.isSelected()) {
            txtFieldPassword.setText(passwordField.getText());
            txtFieldPassword.setVisible(true);
            passwordField.setVisible(false);
            txtFieldConfirmPassword.setText(confirmPasswordField.getText());
            txtFieldConfirmPassword.setVisible(true);
            confirmPasswordField.setVisible(false);
        }
        txtFieldPassword.setVisible(false);
        passwordField.setVisible(true);
        txtFieldConfirmPassword.setVisible(false);
        confirmPasswordField.setVisible(true);

    }
}