package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import org.jdom2.JDOMException;
import ucr.proyecto1.domain.XMLData.CourseXMLData;
import ucr.proyecto1.domain.data.Course;
import ucr.proyecto1.domain.tree.TreeException;

import java.io.IOException;
import java.util.List;

public class CourseMaintenanceController {
    @FXML
    private TextField searchTxtField;
    @FXML
    private TableView<Course> tableView;
    @FXML
    private BorderPane bp;
    @FXML
    private Button btnSearchCourse;
    @FXML
    private TableColumn<Course, Integer> idTColumn;
    @FXML
    private TableColumn<Course, String> nameTColumn;
    @FXML
    private TableColumn<Course, String> descriptionTColumn;
    @FXML
    private TableColumn<Course, String> durationTColumn;
    @FXML
    private TableColumn<Course, String> difficultTColumn;
    @FXML
    private TableColumn<Course, Integer> instructorIdTColumn;

    private CourseXMLData courseData;
    private ObservableList<Course> courseList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the CourseXMLData
        try {
            courseData = new CourseXMLData("courses.xml");
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
            return;
        }

        // Initialize columns
        idTColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        nameTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        descriptionTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescription()));
        durationTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLength()));
        difficultTColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLevel()));
        instructorIdTColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInstructorId()).asObject());

        // Load the data
        loadCourseData();

        // Set the items for the TableView
        tableView.setItems(courseList);
    }

    private void loadCourseData() {
        courseList.clear();
        List<Course> courses = courseData.getAllCourses();
        courseList.addAll(courses);
    }

    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.UtilityFX.loadPage("addCourse.fxml", bp);
    }

    @FXML
    public void modifyOnAction(ActionEvent actionEvent) {
        Course selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            ModifyCourseController.setCourseToModify(selectedCourse);
            util.UtilityFX.loadPage("modifyCourse.fxml", bp);
        } else {
            showAlert("No Course Selected", "Please select a course to modify.");
        }
    }

    @FXML
    public void deleteOnAction(ActionEvent actionEvent) {
        Course selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                courseData.removeCourse(selectedCourse.getId());
                courseList.remove(selectedCourse);
            } catch (IOException | TreeException e) {
                e.printStackTrace();
                showAlert("Error", "Error al eliminar el curso: " + e.getMessage());
            }
        } else {
            showAlert("No Course Selected", "Please select a course to delete.");
        }
    }

    @FXML
    public void searchCourseOnAction(ActionEvent actionEvent) {
        String searchTerm = searchTxtField.getText().toLowerCase();
        ObservableList<Course> filteredList = FXCollections.observableArrayList();
        List<Course> courses = courseData.getAllCourses();
        for (Course course : courses) {
            if (course.getName().toLowerCase().contains(searchTerm) ||
                    course.getDescription().toLowerCase().contains(searchTerm)) {
                filteredList.add(course);
            }
        }
        tableView.setItems(filteredList);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
