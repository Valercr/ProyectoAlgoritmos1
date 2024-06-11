package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
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

    private InformationUserXML informationUserXML;

    @FXML
    public void initialize() {
        informationUserXML = new InformationUserXML(); // Initialize the ArchiveInformationUser instance
    }

    @FXML
    public void searchOnAction(ActionEvent actionEvent) {
        try {
            int id = Integer.parseInt(txtField_idUser.getText());
            User user = informationUserXML.findUser(id);
            if (user != null) {
                txtField_userName.setText(user.getName());
                txtField_email.setText(user.getEmail());

                util.UtilityFX.alert("User Found",user.getId()+"\n"+ user.getName()+"\n"+user.getEmail());

            } else {
                util.UtilityFX.alert("User Not Found", "No user found with ID " + id);
                clearFields();
            }
        } catch (NumberFormatException e) {
            util.UtilityFX.alert("Input Error", "Invalid user ID format.", Alert.AlertType.ERROR);
        }
    }


    private void clearFields() {
        txtField_userName.clear();
        txtField_email.clear();
        txtField_idUser.clear();
    }
}
