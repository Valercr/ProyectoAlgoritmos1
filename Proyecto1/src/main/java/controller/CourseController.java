package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CourseController
{
    @javafx.fxml.FXML
    private TextField txtField_name;
    @javafx.fxml.FXML
    private Slider sliderDifficultyLevel;
    @javafx.fxml.FXML
    private Spinner spinnerDuration;
    @javafx.fxml.FXML
    private TextArea txtArea_description;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void cancelOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void acceptOnAction(ActionEvent actionEvent) {
    }
}