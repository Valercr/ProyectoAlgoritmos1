package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public class MenuInstructorController
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
    public void maintenanceCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("coursesMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void reportCourseOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void maintenanceLessonOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }
}