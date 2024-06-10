package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class SearchUserController
{
    @javafx.fxml.FXML
    private TextField txtField_userName;
    @javafx.fxml.FXML
    private TextField txtField_email;
    @javafx.fxml.FXML
    private TextField txtField_idUser;
    @javafx.fxml.FXML
    private Label laberFond;
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) {

    }
}