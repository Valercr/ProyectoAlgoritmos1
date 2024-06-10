package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ucr.proyecto1.domain.TXTData.ArchiveInformationUser;
import ucr.proyecto1.domain.data.User;

import java.io.IOException;
import java.util.List;

public class UserMaintenanceController {
    @javafx.fxml.FXML
    private TableView<User> tableView; // Specify the type parameter for TableView
    @javafx.fxml.FXML
    private TableColumn<User, String> roleTColumn; // Specify the type parameters for TableColumn
    @javafx.fxml.FXML
    private TableColumn<User, String> emailTColumn;
    @javafx.fxml.FXML
    private TableColumn<User, Integer> idTColumn;
    @javafx.fxml.FXML
    private TableColumn<User, String> nameTColumn;
    Alert alert;

    private ArchiveInformationUser archiveInformationUser;
    @javafx.fxml.FXML
    private BorderPane bp;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        archiveInformationUser = new ArchiveInformationUser();

        // Set the cell value factories with proper data types
        roleTColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        emailTColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        idTColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadCourseData(); // Load the data when initializing
    }

    private void loadCourseData() {
        try {
            List<User> users = archiveInformationUser.getUserList();
            userList.setAll(users);
            tableView.setItems(userList);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @javafx.fxml.FXML
    public void searchOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("searchUser.fxml", bp);
    }

    @javafx.fxml.FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Por favor, seleccione un usuario para modificar.");
            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modifyUser.fxml"));
            Parent root;
            try {
                root = loader.load();
                ModifyUserController controller = loader.getController();
                controller.setUser(selectedUser);
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
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            User selectedUser = tableView.getItems().get(selectedIndex);
            archiveInformationUser.deleteUser(selectedUser.getId()); // Use user ID to delete
            tableView.getItems().remove(selectedIndex);
        } else {
            alert.setContentText("Please select a customer to remove.");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("addUser.fxml", bp);
    }
}
