package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;

import java.util.List;

public class UserMaintenanceController {

    @FXML
    private TableView<User> tableView;
    @FXML
    private TableColumn<User, Integer> idTColumn;
    @FXML
    private TableColumn<User, String> nameTColumn;
    @FXML
    private TableColumn<User, String> emailTColumn;
    @FXML
    private TableColumn<User, String> roleTColumn;
    @FXML
    private BorderPane bp;

    private UserXMLData userXMLData;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    public UserMaintenanceController() throws IOException, JDOMException {
        userXMLData = new UserXMLData("users.xml");
    }

    @FXML
    public void initialize() {
        idTColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailTColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleTColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUserData();
    }

    private void loadUserData() {
        List<User> users = userXMLData.getAllUsers();
        userList.addAll(users);
        tableView.setItems(userList);
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        // Implement search functionality
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "Por favor, seleccione un usuario para modificar.");
        } else {
            // Implement modify functionality
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            try {
                userXMLData.removeUser(selectedUser.getId());
                userList.remove(selectedUser);
                tableView.getItems().remove(selectedUser);
            } catch (IOException | ListException e) {
                showAlert("Error", "No se pudo eliminar el usuario.");
                e.printStackTrace();
            }
        } else {
            showAlert("Error", "Por favor, seleccione un usuario para eliminar.");
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        // Implement add user functionality
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}