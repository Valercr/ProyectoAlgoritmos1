package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public class MenuManagerController
{
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {
    }

    @javafx.fxml.FXML
    public void monitorProgressOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void userMaintenanceOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void editProfileOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("modifyUser.fxml", bp);
    }

    @javafx.fxml.FXML
    public void applyEvaluationsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void completeEvaluationsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void maintenanceCoursesOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("courseMaintenance.fxml", bp);
    }

    @javafx.fxml.FXML
    public void reportsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }

    @javafx.fxml.FXML
    public void reportsMaintenanceOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);

    }

    @javafx.fxml.FXML
    public void maintenanceLessonsOnAction(ActionEvent actionEvent) {
        util.UtilityFX.alert("Este apartado se encuentra en construcción",  "Espere a la segunda entrega", Alert.AlertType.INFORMATION);
    }
}