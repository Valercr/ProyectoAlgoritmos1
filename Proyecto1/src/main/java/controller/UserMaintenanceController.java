package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ucr.proyecto1.domain.TXTData.ArchiveInformationUser;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.ListException;

import java.io.IOException;

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
        // Obtener el usuario seleccionado en la tabla
        User selectedUser = (User) tableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            // Si no se ha seleccionado ningún usuario, mostrar un mensaje de error
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Por favor, seleccione un usuario para modificar.");
            alert.showAndWait();
        } else {
            // Cargar la interfaz de modificación de usuario con los datos del usuario seleccionado
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyUser.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifyUserController controller = loader.getController();

                // Pasar los datos del usuario seleccionado al controlador de modificación de usuario
                controller.setUser(selectedUser);

                // Mostrar la interfaz de modificación de usuario
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modificar Usuario");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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