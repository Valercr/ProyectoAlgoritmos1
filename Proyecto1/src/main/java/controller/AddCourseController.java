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

public class AddCourseController {

    @FXML
    private TextField txtField_name;
    @FXML
    private Spinner<Integer> spinnerDuration;
    @FXML
    private TextArea txtArea_description;
    @FXML
    private ComboBox<String> cBox_dificultyLevel;
    @FXML
    private TextField txtField_id;
    @FXML
    private TextField txtField_instructorId;
    @FXML
    private BorderPane bp;

    @FXML
    public void initialize() {
        // Inicializa el spinner con valores posibles para la duración del curso.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 52, 1);
        spinnerDuration.setValueFactory(valueFactory);

        // Inicializa el ComboBox con los niveles de dificultad.
        cBox_dificultyLevel.getItems().addAll("low", "medium", "high");
    }

    @FXML
    public void cancelOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("courseMaintenance.fxml", bp);
    }

    @FXML
    public void acceptOnAction(ActionEvent actionEvent) {
        try {
            // Lee los datos de los campos de la interfaz de usuario.
            int id = Integer.parseInt(txtField_id.getText());
            String name = txtField_name.getText();
            String description = txtArea_description.getText();
            String length = spinnerDuration.getValue() + " weeks";
            String level = cBox_dificultyLevel.getValue();
            int instructorId = Integer.parseInt(txtField_instructorId.getText());

            // Valida que todos los campos estén llenos.
            if (name.isEmpty() || description.isEmpty() || level == null) {
                showAlert("Error", "Todos los campos deben ser llenados", Alert.AlertType.ERROR);
                return;
            }

            // Crea objeto Course.
            Course course = new Course(id, name, description, length, level, instructorId);

            // Añade el curso al archivo XML.
            CourseXMLData courseXMLData = new CourseXMLData("courses.xml");

            // Verifica si el curso ya existe.
            if (courseXMLData.courseTree.contains(course)) {
                showAlert("Error", "El curso con este ID ya existe.", Alert.AlertType.ERROR);
                return;
            }

            // Añade el curso al archivo XML.
            courseXMLData.addCourse(course);

            // Muestra un mensaje de éxito.
            showAlert("Éxito", "Curso añadido exitosamente", Alert.AlertType.INFORMATION);

            // Limpia los campos después de añadir el curso.
            clearFields();

        } catch (NumberFormatException e) {
            showAlert("Error", "ID e Instructor ID deben ser números enteros.", Alert.AlertType.ERROR);
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        } catch (IOException | JDOMException e) {
            showAlert("Error", "Error al interactuar con el archivo XML: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (TreeException e) {
            showAlert("Error", "Error en la estructura de datos del árbol: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        txtField_id.clear();
        txtField_name.clear();
        txtArea_description.clear();
        spinnerDuration.getValueFactory().setValue(1);
        cBox_dificultyLevel.setValue(null);
        txtField_instructorId.clear();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
