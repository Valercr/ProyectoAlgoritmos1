package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.InformationUserXML;

public class AddUserController {
    @FXML
    private TextField txtField_userName;
    @FXML
    private TextField txtFieldConfirmPassword;
    @FXML
    private TextField txtField_email;
    @FXML
    private CheckBox showPassword;
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
    public void changeVisibility(ActionEvent actionEvent) {
        if (showPassword.isSelected()) {
            txtFieldPassword.setPromptText(txtFieldPassword.getText());
            txtFieldPassword.setText("");
        } else {
            txtFieldPassword.setText(txtFieldPassword.getPromptText());
            txtFieldPassword.setPromptText("");
        }
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
                showAlert(Alert.AlertType.ERROR, "Error", "Todos los campos deben ser llenados.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert(Alert.AlertType.ERROR, "Error", "Las contraseñas no coinciden.");
                return;
            }

            informationUserXML.registerUser(id, name, email, password);
            showAlert(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado exitosamente.");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "El formato del ID del usuario es inválido.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Ocurrió un error: " + e.getMessage());
        }
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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