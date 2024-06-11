package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;

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

    private UserXMLData userXMLData;

    @FXML
    public void initialize() {
        try {
            userXMLData = new UserXMLData();
        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error al inicializar los datos del usuario.");
        }
        cBoxRole.getItems().addAll("Administrador", "Usuario", "Instructor");
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
                showAlert("Error", "Todos los campos deben ser llenados.");
                return;
            }

            if (!password.equals(confirmPassword)) {
                showAlert("Error", "Las contraseñas no coinciden.");
                return;
            }

            User newUser = new User(id, name, password, email, role);
            userXMLData.addUser(newUser);
            showAlert("Éxito", "Usuario creado exitosamente.");
            clearFields();
        } catch (NumberFormatException e) {
            showAlert("Error", "El formato del ID del usuario es inválido.");
        } catch (Exception e) {
            showAlert("Error", "Ocurrió un error: " + e.getMessage());
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

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
