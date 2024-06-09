package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.HelloApplication;
import ucr.proyecto1.domain.TXTData.ArchivoTXTPassword;
import ucr.proyecto1.domain.list.CircularLinkedList;
import util.UtilityFX;

import java.io.IOException;

import static util.UtilityFX.loadPage;

public class SignUpController
{
    @javafx.fxml.FXML
    private TextField txtField_userName;
    @javafx.fxml.FXML
    private TextField txtField_email;
    @javafx.fxml.FXML
    private TextField txtField_idUser;
    private CircularLinkedList userList;
    ArchivoTXTPassword archivoTXTPassword;
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

    public SignUpController() {
        archivoTXTPassword = new ArchivoTXTPassword();
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

        //Verficar que no esté ningun textField vacío
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty() || idUser.isEmpty()) {
            alert = util.UtilityFX.alert("Error", "Complete todos los espacios");
        } else if (!password.equals(confirmPassword)) {// Verificar si las contraseñas no coinciden
            alert = util.UtilityFX.alert("Trate de nuevo", "Las contraseñas no coinciden");
        } else // Guardar la información usando la clase ArchivoTXTPassword
                archivoTXTPassword.registerUser(Integer.parseInt(idUser), username,email, password);
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