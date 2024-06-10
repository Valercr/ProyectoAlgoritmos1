package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.InformationUserXML;

public class LogInController
{
    @javafx.fxml.FXML
    private TextField txtField_emailAddress;
    @javafx.fxml.FXML
    private BorderPane bp;
    Alert alert;
//    ArchivoTXTPassword archivoTXTPassword;
    InformationUserXML informationUserXML;

    @javafx.fxml.FXML
    private PasswordField passwordField;

    @javafx.fxml.FXML
    public void initialize() {
//        archivoTXTPassword = new ArchivoTXTPassword();
        informationUserXML = new InformationUserXML();
    }

    @javafx.fxml.FXML
    public void signUpOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("signUp.fxml", bp);//Cargar singUp
    }

    @javafx.fxml.FXML
    public void logInOnAction(ActionEvent actionEvent) {
        String email = txtField_emailAddress.getText();
        String password = passwordField.getText();
        if (email.isEmpty() || password.isEmpty())
            alert = util.UtilityFX.alert("Error", "Complete todos los espacios");
        else{

//            if (archivoTXTPassword.authenticateUser(email, password))
                        if (informationUserXML.authenticateUser(email, password))
            alert = util.UtilityFX.alert("Bienvenid@", "Inicio de sesion con éxtio");
            else alert = util.UtilityFX.alert("Error", "Usuario o contraseña incorrectos");
        }

    }
}