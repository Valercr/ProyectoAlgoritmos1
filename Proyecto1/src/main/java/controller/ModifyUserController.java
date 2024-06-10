package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @Deprecated
    public void signUpOnAction(ActionEvent actionEvent) {
    }

    @Deprecated
    public void logInOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void changeVisibility(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void modifyOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void txtFieldConfirmPassword(ActionEvent actionEvent) {
    }
}