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
import ucr.proyecto1.domain.XMLData.Email;
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

    private InformationUserXML informationUserXML;
    private ObservableList<User> userList = FXCollections.observableArrayList();
    private Email email;
    private PasswordXML passwordXML;

    @FXML
    public void initialize() {
        informationUserXML = new InformationUserXML(); // Inicializa InformationUserXML
        email = new Email();

        idTColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailTColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleTColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadUserData();
    }

    private void loadUserData() {
        try {
            List<User> users = informationUserXML.getUserList(); // Utiliza getUserList() para obtener los usuarios
            userList.addAll(users);
            tableView.setItems(userList);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("searchUser.fxml", bp);
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert("Error", "Por favor, seleccione un usuario para modificar.");
        } else {
            util.UtilityFX.loadPage("modifyUser.fxml", bp);
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        User selectedIndex = tableView.getSelectionModel().getSelectedItem();

        if (selectedIndex != null) {
            informationUserXML.deleteUser(selectedIndex.getId());
            userList.remove(selectedIndex);
            tableView.getItems().remove(selectedIndex);
        } else {
            showAlert("Error", "Por favor, seleccione un usuario para eliminar.");
        }
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("addUser.fxml", bp);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
