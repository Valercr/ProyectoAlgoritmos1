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
        userXMLData = new UserXMLData(); // Ajusta el constructor según tu implementación
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
            // Implementar la funcionalidad de modificar el usuario
            TextInputDialog dialog = new TextInputDialog(selectedUser.getName());
            dialog.setTitle("Modificar Usuario");
            dialog.setHeaderText("Modificar Usuario");
            dialog.setContentText("Nuevo Nombre:");

            String newName = dialog.showAndWait().orElse("");
            if (!newName.isEmpty()) {
                try {
                    selectedUser.setName(newName);
                    userXMLData.updateUser(selectedUser);
                    tableView.refresh();
                } catch (IOException | ListException e) {
                    showAlert("Error", "No se pudo modificar el usuario.");
                    e.printStackTrace();
                }
            }
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
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Agregar Usuario");
        dialog.setHeaderText("Agregar Nuevo Usuario");
        dialog.setContentText("Nombre:");

        String newName = dialog.showAndWait().orElse("");
        if (!newName.isEmpty()) {
            try {
                // Solicitar otros detalles del usuario como email, password, etc.
                int newId = userList.size() + 1; // Generar un ID para el nuevo usuario
                User newUser = new User(newId, newName, "newEmail@example.com", "defaultPassword", "usuario"); // Ajusta según los detalles solicitados
                userXMLData.addUser(newUser);
                userList.add(newUser);
                tableView.refresh();
            } catch (IOException | MessagingException e) {
                showAlert("Error", "No se pudo agregar el usuario.");
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}