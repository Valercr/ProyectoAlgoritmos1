package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;
public class ModifyUserController {
    @FXML
    private TextField txtField_userName;
    @FXML
    private TextField txtField_email;
    @FXML
    private CheckBox showPassword;
    @FXML
    private TextField txtField_idUser;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField txtFieldPassword;
    @FXML
    private BorderPane bp;

    private static User userToModify;

    public static void setUserToModify(User user) {
        userToModify = user;
    }

    @FXML
    public void initialize() {
        if (userToModify != null) {
            txtField_userName.setText(userToModify.getName());
            txtField_email.setText(userToModify.getEmail());
            txtField_idUser.setText(String.valueOf(userToModify.getId()));
        }
    }

    @FXML
    public void changeVisibility(ActionEvent actionEvent) {
        if (showPassword.isSelected()) {
            txtFieldPassword.setText(passwordField.getText());
            txtFieldPassword.setVisible(true);
            passwordField.setVisible(false);
        } else {
            txtFieldPassword.setVisible(false);
            passwordField.setVisible(true);
        }
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        String username = txtField_userName.getText();
        String email = txtField_email.getText();
        String password = showPassword.isSelected() ? txtFieldPassword.getText() : passwordField.getText();
        String idUser = txtField_idUser.getText();

        if (idUser.isEmpty()) {
            util.UtilityFX.alert("Error", "Debe ingresar un ID de usuario válido.");
        } else {
            try {
                int id = Integer.parseInt(idUser);
                userToModify.setName(username);
                userToModify.setEmail(email);
                userToModify.setPassword(password);

                // Lógica para actualizar la información del usuario
                updateUserInDataSource(userToModify);

                util.UtilityFX.alert("Éxito", "Usuario modificado exitosamente.");
                util.UtilityFX.loadPage("userMaintenance.fxml", bp);
            } catch (NumberFormatException e) {
                util.UtilityFX.alert("Error", "Error al modificar el usuario: " + e.getMessage());
            } catch (Exception e) {
                util.UtilityFX.alert("Error", "Ocurrió un error al modificar el usuario.");
            }
        }
    }

    @FXML
    public void txtFieldConfirmPassword(ActionEvent actionEvent) {
        // Implementar si es necesario la confirmación de la contraseña
    }

    private void updateUserInDataSource(User user) throws Exception {
        // Implementa la lógica para actualizar el usuario en la fuente de datos (e.g., XML, base de datos)
        UserXMLData userXMLData = new UserXMLData();
        userXMLData.updateUser(user);
    }
}