package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AddUserController {
    @FXML
    private TextField txtField_userName;
    @FXML
    private TextField txtFieldConfirmPassword;
    @FXML
    private TextField txtField_email;
    @FXML
    private TextField txtField_idUser;
    @FXML
    private TextField txtFieldPassword;
    @FXML
    private BorderPane bp;
    @FXML
    private ComboBox<String> cBoxRole;

    private InformationUserXML informationUserXML;

    @FXML
    public void initialize() {
        informationUserXML = new InformationUserXML();
        cBoxRole.getItems().addAll("Administrador", "Usuario", "Inspector");
    }

    @FXML
    public void createOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtField_idUser.getText());
            String name = txtField_userName.getText();
            String email = txtField_email.getText();
            String password = txtFieldPassword.getText();
            String confirmPassword = txtFieldConfirmPassword.getText();
            String role = cBoxRole.getValue();

            if (password.isEmpty() || confirmPassword.isEmpty() || name.isEmpty() || email.isEmpty() || role == null) {
                util.UtilityFX.alert( "Error", "Todos los campos deben ser llenados.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                util.UtilityFX.alert("Error", "Las contraseñas no coinciden.", Alert.AlertType.ERROR);
                return;
            }

            informationUserXML.registerUser(id, name, email, password);
            util.UtilityFX.alert("Éxito", "Usuario creado exitosamente.", Alert.AlertType.INFORMATION);
            clearFields();
        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Error", "El formato del ID del usuario es inválido.", Alert.AlertType.ERROR);
        } catch (Exception e) {
            util.UtilityFX.alert("Error", "Ocurrió un error: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        txtField_idUser.clear();
        txtField_userName.clear();
        txtField_email.clear();
        txtFieldPassword.clear();
        txtFieldConfirmPassword.clear();
        cBoxRole.getSelectionModel().clearSelection();
    }
}