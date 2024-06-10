package controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jdom2.JDOMException;
import org.jdom2.Parent;
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
    private Button btnInformation;

    @FXML
    public void initialize() {
        try {
            courseData = new CourseXMLData("courses.xml");

            idTColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameTColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            descriptionTColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            durationTColumn.setCellValueFactory(new PropertyValueFactory<>("length"));
            difficultTColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
            instructorIdTColumn.setCellValueFactory(new PropertyValueFactory<>("instructorId"));

            loadCourseData();
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }
    }


    private void loadCourseData() {
        try {
            List<Course> courses = courseData.getAllCourses();
            courseList.addAll(courses);
            tableView.setItems(courseList);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
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
            util.UtilityFX.alert("No Course Selected", "Please select a course to modify.");
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
                util.UtilityFX.alert("Error", "Error al eliminar el curso: " + e.getMessage());
            }
        } else {
            util.UtilityFX.alert("No Course Selected", "Please select a course to delete.");
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

    @FXML
    public void showInfoOnAction(ActionEvent actionEvent) {
        Course selectedCourse = tableView.getSelectionModel().getSelectedItem();
        if (selectedCourse != null) {
            try {
                CourseInfoWindow courseInfoWindow = new CourseInfoWindow(selectedCourse);
                courseInfoWindow.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
                util.UtilityFX.alert("Error", "Error loading course information window: " + e.getMessage());
            }
        } else {
            util.UtilityFX.alert("No Course Selected", "Please select a course to view information.");
        }
    }

}
