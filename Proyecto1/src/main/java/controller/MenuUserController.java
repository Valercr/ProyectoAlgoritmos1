package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public class MenuUserController
{
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void editProfileOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("modifyUser.fxml", bp);
    }

    @javafx.fxml.FXML
    public void studyMatViewOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void completeEvaluationsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void reportsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void registerCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void personalReportOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void progressLessonOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void progressCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void searchCoursesOnAction(ActionEvent actionEvent) {
//        util.UtilityFX.loadPage("searchCourses.fxml", bp);
    }

    @javafx.fxml.FXML
    public void gradesCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void accessLessonOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void cancelCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }
}