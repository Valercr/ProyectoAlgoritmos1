package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.ArchiveInformationUser;
import ucr.proyecto1.domain.data.User;

public class ModifyUserController
{
    @javafx.fxml.FXML
    private TextField txtField_userName;
    @javafx.fxml.FXML
    private TextField txtField_email;
    @javafx.fxml.FXML
    private CheckBox showPassword;
    @javafx.fxml.FXML
    private TextField txtField_idUser;
    @javafx.fxml.FXML
    private TextField txtFieldPassword;
    @javafx.fxml.FXML
    private PasswordField passwordField;
    @javafx.fxml.FXML
    private BorderPane bp;

    private ArchiveInformationUser archiveInformationUser;
    private Alert alert;
    private User selectedUser;

    public ModifyUserController() {
        archiveInformationUser = new ArchiveInformationUser();
    }

    @javafx.fxml.FXML
    public void initialize() {
    }

    public void setUser(User user) {
        selectedUser = user;
        //Establecer los datos del usuario en los campos de la interfaz
        txtField_userName.setText(selectedUser.getName());
        txtField_email.setText(selectedUser.getEmail());
        txtField_idUser.setText(String.valueOf(selectedUser.getId()));
    }

    @Deprecated
    public void signUpOnAction(ActionEvent actionEvent) {

    }

    @Deprecated
    public void logInOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
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

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        String username = txtField_userName.getText();
        String email = txtField_email.getText();
        String password = showPassword.isSelected() ? txtFieldPassword.getText() : passwordField.getText();
        String idUser = txtField_idUser.getText();

        if (idUser.isEmpty()) {
            //Si el ID del usuario está vacío, mostrar un mensaje de error.
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("You must enter a valid user ID");
            alert.showAndWait();
        } else {
            int id = Integer.parseInt(idUser);

            //Actualizar la información del usuario en ArchiveInformationUser
            archiveInformationUser.updateInformation(id, username, email);

            // Mostrar un mensaje de éxito y volver a la página de mantenimiento de usuario
            alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Éxito");
            alert.setHeaderText("Usuario modificado exitosamente");
            alert.showAndWait();

            util.UtilityFX.loadPage("userMaintenance.fxml", bp);
        }
    }

    @javafx.fxml.FXML
    public void txtFieldConfirmPassword(ActionEvent actionEvent) {
    }
}