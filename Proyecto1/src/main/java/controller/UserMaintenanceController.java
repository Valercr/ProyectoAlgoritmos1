package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ucr.proyecto1.domain.TXTData.InformationUserXML;
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


    @javafx.fxml.FXML
    private InformationUserXML informationUserXML;
    private BorderPane bp;
    private ObservableList<User> userList = FXCollections.observableArrayList();

    @javafx.fxml.FXML
    public void initialize() {
        informationUserXML = new InformationUserXML();

        // Set the cell value factories with proper data types
        roleTColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        emailTColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        idTColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        loadUserData();; // Load the data when initializing
    }

    private void loadUserData() {
        try {
            List<User> users = informationUserXML.getUserList();
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

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ModifyUserController.setUserToModify(selectedUser);
            util.UtilityFX.loadPage("modifyUser.fxml", bp);
        } else {
            showAlert("Error", "Por favor, seleccione un usuario para modificar.");
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        int selectedIndex = tableView.getSelectionModel().getSelectedIndex();

        if (selectedIndex >= 0) {
            User selectedUser = tableView.getItems().get(selectedIndex);
            informationUserXML.deleteUser(selectedUser.getId());
            tableView.getItems().remove(selectedIndex);
        } else {
            showAlert("Error", "Por favor, seleccione un usuario para eliminar.");
        }
    }

    @javafx.fxml.FXML
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


