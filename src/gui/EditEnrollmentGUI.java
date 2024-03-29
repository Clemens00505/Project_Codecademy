package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Enrollment;

public class EditEnrollmentGUI extends Application {
    private Enrollment enrollment;

    public EditEnrollmentGUI(Enrollment enrollment) {
        this.enrollment = enrollment;
    }

    @Override
    public void start(Stage editEnrollmentStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        editEnrollmentStage.setTitle("Inschrijving bewerken");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        databaseConnection.openConnection();

        // ComboBox for selecting existing emails
        ComboBox<String> studentMailInput = new ComboBox<>();
        studentMailInput.setValue(enrollment.getStudentMail());

        // ComboBox for selecting existing course names
        ComboBox<String> courseNameInput = new ComboBox<>();
        courseNameInput.setValue(enrollment.getCourseName());

        // DatePicker for selecting enrollment date
        DatePicker enrollmentDateInput = new DatePicker();
        enrollmentDateInput.setValue(enrollment.getEnrollmentDate().toLocalDate());

        // Make it non-editable
        studentMailInput.setEditable(false);
        courseNameInput.setEditable(false);
        enrollmentDateInput.setDisable(true); 

        // Spinner for entering enrollment percentage
        Spinner<Integer> enrollmentPercentageInput = new Spinner<>(0, 100, enrollment.getPercentage());
        enrollmentPercentageInput.setEditable(true); // Set the spinner editable

        // Sets equal width for input
        int equalWidth = 400;
        studentMailInput.setMinWidth(equalWidth);
        courseNameInput.setMinWidth(equalWidth);
        enrollmentDateInput.setMinWidth(equalWidth);
        enrollmentPercentageInput.setMinWidth(equalWidth);

        // Create GridPane with a layout
        GridPane editEnrollmentGUIGridPane = new GridPane();
        editEnrollmentGUIGridPane.addRow(0, new Label("Studentmail:"), studentMailInput);
        editEnrollmentGUIGridPane.addRow(1, new Label("Naam cursus:"), courseNameInput);
        editEnrollmentGUIGridPane.addRow(2, new Label("Percentage: "), enrollmentPercentageInput);
        editEnrollmentGUIGridPane.addRow(3, new Label("Inschrijfdatum:"), enrollmentDateInput);
        editEnrollmentGUIGridPane.addRow(4, cancelButton, saveButton);

        // Gaps and padding in the GridPane
        editEnrollmentGUIGridPane.setHgap(10);
        editEnrollmentGUIGridPane.setVgap(10);
        editEnrollmentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editEnrollmentGUIGridPane, 500, 250);

        editEnrollmentStage.setScene(scene);
        editEnrollmentStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editEnrollmentStage.close();
        });

        // If saveButton is pressed, the enrollment is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                Integer newPercentage = enrollmentPercentageInput.getValue();
                enrollment.setPercentage(newPercentage);
                enrollment.updateEnrollment(enrollment, databaseConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }

            editEnrollmentStage.close();
        });
    }
}