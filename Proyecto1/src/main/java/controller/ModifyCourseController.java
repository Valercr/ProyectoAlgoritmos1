package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class ModifyCourseController
{
    @javafx.fxml.FXML
    private ComboBox cBox_dificultyLevel;
    @javafx.fxml.FXML
    private TextField txtField_name;
    @javafx.fxml.FXML
    private Spinner spinnerDuration;
    @javafx.fxml.FXML
    private TextArea txtArea_description;
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        txtField_name.clear();
        txtArea_description.clear();
        util.UtilityFX.loadPage("courseMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void acceptOnAction(ActionEvent actionEvent) {
    }
}