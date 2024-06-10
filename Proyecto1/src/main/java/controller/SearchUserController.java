package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ucr.proyecto1.domain.TXTData.ArchiveInformationUser;
import ucr.proyecto1.domain.data.User;

public class SearchUserController {
    @FXML
    private TextField txtField_userName;
    @FXML
    private TextField txtField_email;
    @FXML
    private TextField txtField_idUser;
    @FXML
    private Label laberFond;
    @FXML
    private BorderPane bp;

    private ArchiveInformationUser archiveInformationUser;

    @FXML
    public void initialize() {
        archiveInformationUser = new ArchiveInformationUser(); // Initialize the ArchiveInformationUser instance
    }

    @FXML
    public void backOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("userMaintenance.fxml", bp);
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtField_idUser.getText());
            User user = archiveInformationUser.findUser(id);
            if (user != null) {
                txtField_userName.setText(user.getName());
                txtField_email.setText(user.getEmail());

                showAlert(Alert.AlertType.INFORMATION, "User Found",user.getId()+"\n"+ user.getName()+"\n"+user.getEmail());

            } else {
                showAlert(Alert.AlertType.INFORMATION, "User Not Found", "No user found with ID " + id);
                clearFields();
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Invalid user ID format.");
        }
    }


    private void clearFields() {
        txtField_userName.clear();
        txtField_email.clear();
        txtField_idUser.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
