package controller;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ucr.proyecto1.domain.data.Course;

public class CourseInfoWindow extends Application {

    private Course selectedCourse;

    public CourseInfoWindow(Course selectedCourse) {
        this.selectedCourse = selectedCourse;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Course Information");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));

        Label idLabel = new Label("ID: " + selectedCourse.getId());
        Label nameLabel = new Label("Name: " + selectedCourse.getName());
        Label descriptionLabel = new Label("Description: " + selectedCourse.getDescription());
        Label durationLabel = new Label("Duration: " + selectedCourse.getLength());
        Label difficultLabel = new Label("Difficult: " + selectedCourse.getLevel());
        Label instructorIdLabel = new Label("Instructor ID: " + selectedCourse.getInstructorId());

        root.getChildren().addAll(idLabel, nameLabel, descriptionLabel,
                durationLabel, difficultLabel, instructorIdLabel);

        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
