package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class SignUpController
{
    @javafx.fxml.FXML
    private TextField txtField_userName;
    @javafx.fxml.FXML
    private TextField txtField_confirmPassword;
    @javafx.fxml.FXML
    private TextField txtField_password;
    @javafx.fxml.FXML
    private TextField txtField_email;
    @javafx.fxml.FXML
    private TextField txtField_idUser;

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
    public void updateOnAction(ActionEvent actionEvent) {
    }
}