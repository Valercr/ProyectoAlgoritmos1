package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class ProfileManagementController
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

    @javafx.fxml.FXML
    public void updateOnAction(ActionEvent actionEvent) {
    }
}