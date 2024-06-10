package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.InformationUserXML;
import util.Utility;

public class LogInController
{
    @javafx.fxml.FXML
    private TextField txtField_emailAddress;
    @javafx.fxml.FXML
    private BorderPane bp;
//    ArchivoTXTPassword archivoTXTPassword;
    InformationUserXML informationUserXML;
//    ArchiveInformationUser archiveInformationUser;

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
            util.UtilityFX.alert("Error", "Complete todos los espacios");
        else{
//            if (archivoTXTPassword.authenticateUser(email, password))
            if (informationUserXML.authenticateUser(email, password)){
                if (Utility.roleUsuarioActivo.equalsIgnoreCase("administrative"))
                    util.UtilityFX.loadPage("menuManager.fxml", bp);
                else if (Utility.roleUsuarioActivo.equalsIgnoreCase("instructor"))
                    util.UtilityFX.loadPage("menuInstructor.fxml", bp);
                else util.UtilityFX.loadPage("menuUser.fxml", bp);
            }
            else util.UtilityFX.alert("Error", "Usuario o contraseña incorrectos");
        }

    }
}