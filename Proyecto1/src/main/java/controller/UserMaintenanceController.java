package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;
import ucr.proyecto1.domain.XMLData.UserXMLData;
import ucr.proyecto1.domain.data.User;
import ucr.proyecto1.domain.list.ListException;

import javax.mail.MessagingException;
import java.io.IOException;
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
        userXMLData = new UserXMLData();
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Buscar Usuario");
        dialog.setHeaderText("Buscar Usuario por Nombre");
        dialog.setContentText("Nombre:");

        String userName = dialog.showAndWait().orElse("");
        if (!userName.isEmpty()) {
            User user = userXMLData.findUserByUsername(userName);
            if (user != null) {
                tableView.getSelectionModel().select(user);
            } else {
                showAlert("Error", "Usuario no encontrado.");
            }
        }
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        User selectedUser = tableView.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showAlert("Error", "Por favor, seleccione un usuario para modificar.");
        } else {
            ModifyUserController.setUserToModify(selectedUser);
            util.UtilityFX.loadPage("modifyUser.fxml", bp);
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
