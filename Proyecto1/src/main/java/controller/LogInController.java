package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.ArchivoTXTPassword;

public class LogInController
{
    @javafx.fxml.FXML
    private TextField txtField_Password;
    @javafx.fxml.FXML
    private TextField txtField_emailAddress;
    @javafx.fxml.FXML
    private BorderPane bp;
    Alert alert;
    ArchivoTXTPassword archivoTXTPassword;

    @javafx.fxml.FXML
    public void initialize() {
        archivoTXTPassword = new ArchivoTXTPassword();
    }

    @javafx.fxml.FXML
    public void signUpOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("signUp.fxml", bp);//Cargar singUp
    }

    @javafx.fxml.FXML
    public void logInOnAction(ActionEvent actionEvent) {
        String email = txtField_emailAddress.getText();
        String password = txtField_Password.getText();
        if (email.isEmpty() || password.isEmpty())
            alert = util.UtilityFX.alert("Error", "Complete todos los espacios");
        else{

            if (archivoTXTPassword.authenticateUser(email, password))
            alert = util.UtilityFX.alert("Bienvenid@", "Inicio de sesion con éxtio");
            else alert = util.UtilityFX.alert("Error", "Usuario o contraseña incorrectos");
        }

    }
}