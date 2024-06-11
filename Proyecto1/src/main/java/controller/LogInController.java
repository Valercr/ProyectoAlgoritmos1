package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.PasswordEncryption;
import ucr.proyecto1.domain.XMLData.PasswordXML;
import util.Utility;

import static util.Utility.usuariosSistema;
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
            showAlert("Error", "Complete todos los espacios");
        } else {
            if (passwordXML.authenticateUser(email, password)) {
                String rolUsuario = Utility.roleUsuarioActivo;
                switch (rolUsuario.toLowerCase()) {
                    case "administrador":
                        util.UtilityFX.loadPage("menuAdministrador.fxml", bp);
                        break;
                    case "instructor":
                        util.UtilityFX.loadPage("menuInstructor.fxml", bp);
                        break;
                    case "usuario":
                        util.UtilityFX.loadPage("menuUsuario.fxml", bp);
                        break;
                    default:
                        showAlert("Error", "Rol de usuario desconocido");
                        break;
                }
            } else {
                showAlert("Error", "Usuario o contraseña incorrectos");
            }
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
