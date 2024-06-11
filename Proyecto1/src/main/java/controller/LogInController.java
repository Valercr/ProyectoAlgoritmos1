package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.XMLData.PasswordXML;
import util.Utility;
public class LogInController {

    @FXML
    private TextField txtField_emailAddress;
    @FXML
    private BorderPane bp;
    @FXML
    private PasswordField passwordField;

    private PasswordXML passwordXML;

    @FXML
    public void initialize() {
        passwordXML = new PasswordXML();
    }

    @FXML
    public void signUpOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("signUp.fxml", bp); // Cargar página de registro
    }

    @FXML
    public void logInOnAction(ActionEvent actionEvent) {
        String email = txtField_emailAddress.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            util.UtilityFX.alert("Error", "Complete todos los espacios");
        } else {
            if (passwordXML.authenticateUser(email, password)) {
                switch (Utility.roleUsuarioActivo.toLowerCase()) {
                    case "administrative":
                        util.UtilityFX.loadPage("menuManager.fxml", bp);
                        break;
                    case "instructor":
                        util.UtilityFX.loadPage("menuInstructor.fxml", bp);
                        break;
                    case "user":
                        util.UtilityFX.loadPage("menuUser.fxml", bp);
                        break;
                    default:
                        util.UtilityFX.alert("Error", "Rol de usuario desconocido");
                        break;
                }
            } else {
                util.UtilityFX.alert("Error", "Usuario o contraseña incorrectos");
            }
        }
    }
}