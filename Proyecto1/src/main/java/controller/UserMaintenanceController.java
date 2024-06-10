package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.ArchiveInformationUser;
import ucr.proyecto1.domain.list.ListException;

public class UserMaintenanceController
{
    @javafx.fxml.FXML
    private TableView tableView;
    @javafx.fxml.FXML
    private TableColumn roleTColumn;
    @javafx.fxml.FXML
    private TableColumn emailTColumn;
    @javafx.fxml.FXML
    private TableColumn idTColumn;
    @javafx.fxml.FXML
    private TableColumn nameTColumn;
    Alert alert;

    private ArchiveInformationUser archiveInformationUser;
    @javafx.fxml.FXML
    private BorderPane bp;

    @javafx.fxml.FXML
    public void initialize() {

    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("searchUser.fxml", bp);
    }

    @javafx.fxml.FXML
    public void modifyOnAction(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void deleteOnAction(ActionEvent actionEvent) {

            //Obtiene el índice seleccionado en la tabla
            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

            //Verifica si se seleccionó algún elemento
            if (selectedIndex >= 0) {
                //Elimina el cliente de la lista
                archiveInformationUser.deleteUser(selectedIndex + 1); // Se suma 1 porque la lista está basada en índices comenzando desde 1

                //Elimina el cliente de la tabla
                tableView.getItems().remove(selectedIndex);
            } else {
                //Muestra un mensaje si no se seleccionó ningún elemento
                alert.setContentText("Please select a customer to remove.");
                alert.showAndWait();
            }

    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {

        util.UtilityFX.loadPage("addUser.fxml", bp);
    }
}