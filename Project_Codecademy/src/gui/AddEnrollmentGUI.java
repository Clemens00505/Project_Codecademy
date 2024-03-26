package gui;

import java.sql.Date;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Enrollment;

public class AddEnrollmentGUI extends Application {

    @Override
    public void start(Stage addEnrollmentStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addEnrollmentStage.setTitle("Inschrijving toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        databaseConnection.openConnection();

        // ComboBox for selecting existing emails
        ComboBox<String> studentMailInput = new ComboBox<>();
        studentMailInput.setPromptText("Selecteer email");

        // Fetch existing emails from the database
        List<String> existingEmails = databaseConnection.getAllStudentEmails();
        studentMailInput.setItems(FXCollections.observableArrayList(existingEmails));

        // Do the same for courses
        ComboBox<String> courseNameInput = new ComboBox<>();
        courseNameInput.setPromptText("Selecteer cursus");

        List<String> existingCourses = databaseConnection.getAllCourseNames();
        courseNameInput.setItems(FXCollections.observableArrayList(existingCourses));

        databaseConnection.closeConnection();

        // add textfield
        DatePicker enrollmentDateInput = new DatePicker();

        // add prompttext
        enrollmentDateInput.setPromptText("Inschrijfdatum");

        //sets equal width for input
        int equalWidth = 400;
        studentMailInput.setMinWidth(equalWidth);
        courseNameInput.setMinWidth(equalWidth);
        enrollmentDateInput.setMinWidth(equalWidth);

        // Create gridpane with a layout
        GridPane addEnrollmentGUIGridPane = new GridPane();
        addEnrollmentGUIGridPane.addRow(0, new Label("Studentmail:"), studentMailInput);
        addEnrollmentGUIGridPane.addRow(1, new Label("Naam cursus:"), courseNameInput);
        addEnrollmentGUIGridPane.addRow(2, new Label("Inschrijfdatum:"), enrollmentDateInput);
        addEnrollmentGUIGridPane.addRow(3, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addEnrollmentGUIGridPane.setHgap(10);
        addEnrollmentGUIGridPane.setVgap(10);
        addEnrollmentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addEnrollmentGUIGridPane, 500, 250);

        addEnrollmentStage.setScene(scene);
        addEnrollmentStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            addEnrollmentStage.close();
        });

        // If saveButton is pressed, the enrollment is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                String studentMail = studentMailInput.getValue();
                String courseName = courseNameInput.getValue();
                Date enrollmentDate = Date.valueOf(enrollmentDateInput.getValue());

                // Perform validation checks
                if (studentMail.isEmpty() || courseName.isEmpty() || enrollmentDateInput.getValue() == null) {
                    // Validation error handling remains the same
                } else {
                    Enrollment enrollment = new Enrollment(studentMail, courseName, enrollmentDate);

                    // Retrieve the course ID based on the course name
                    int courseId = enrollment.getCourseIdByName(courseName, databaseConnection);
                    // Set the retrieved course ID
                    enrollment.setCourseId(courseId);

                    // Save the enrollment to the database
                    enrollment.addEnrollment(enrollment, databaseConnection);

                    addEnrollmentStage.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
