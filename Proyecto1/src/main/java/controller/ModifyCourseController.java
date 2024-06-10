package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;
import ucr.proyecto1.domain.XMLData.CourseXMLData;
import ucr.proyecto1.domain.data.Course;
import ucr.proyecto1.domain.tree.TreeException;

import java.io.IOException;
public class ModifyCourseController {
    @FXML
    private ComboBox<String> cBox_dificultyLevel;
    @FXML
    private Spinner<Integer> spinnerDuration;
    @FXML
    private TextArea txtArea_description;
    @FXML
    private BorderPane bp;

    private static Course courseToModify;

    public static void setCourseToModify(Course course) {
        courseToModify = course;
    }

    @FXML
    public void initialize() {
        // Inicializa el spinner con valores posibles para la duración del curso.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 52, 1);
        spinnerDuration.setValueFactory(valueFactory);

        // Inicializa el ComboBox con los niveles de dificultad.
        cBox_dificultyLevel.getItems().addAll("low", "medium", "high");

        if (courseToModify != null) {
            cBox_dificultyLevel.setValue(courseToModify.getLevel());
            spinnerDuration.getValueFactory().setValue(Integer.parseInt(courseToModify.getLength().replace(" weeks", "")));
            txtArea_description.setText(courseToModify.getDescription());
        }
    }

    @FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("courseMaintenance.fxml", bp);
    }

    @FXML
    public void acceptOnAction(ActionEvent actionEvent) {
        try {
            courseToModify.setLevel(cBox_dificultyLevel.getValue());
            courseToModify.setLength(spinnerDuration.getValue() + " weeks");
            courseToModify.setDescription(txtArea_description.getText());

            CourseXMLData courseData = new CourseXMLData("courses.xml");
            courseData.updateCourse(courseToModify);

            util.UtilityFX.loadPage("courseMaintenance.fxml", bp);
        } catch (IOException | JDOMException | TreeException e) {
            e.printStackTrace();
            showAlert("Error", "Error al actualizar el curso: " + e.getMessage());
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
