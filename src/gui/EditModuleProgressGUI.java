package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.EnrollmentModules;

public class EditModuleProgressGUI extends Application {
    private EnrollmentModules enrollmentModules;

    public EditModuleProgressGUI(EnrollmentModules enrollmentModules) {
        this.enrollmentModules = enrollmentModules;
    }

    @Override
    public void start(Stage editModuleProgressStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        editModuleProgressStage.setTitle("Voortgang bewerken");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        databaseConnection.openConnection();

        // Spinner for entering enrollment percentage
        Spinner<Integer> contentPercentageInput = new Spinner<>(0, 100, enrollmentModules.getProgress());
        contentPercentageInput.setEditable(true); // Set the spinner editable

        // Sets equal width for input
        int equalWidth = 400;
        contentPercentageInput.setMinWidth(equalWidth);

        // Create GridPane with a layout
        GridPane editEnrollmentGUIGridPane = new GridPane();
        editEnrollmentGUIGridPane.addRow(0, new Label("Voortgang:"), contentPercentageInput);
        editEnrollmentGUIGridPane.addRow(1, cancelButton, saveButton);

        // Gaps and padding in the GridPane
        editEnrollmentGUIGridPane.setHgap(10);
        editEnrollmentGUIGridPane.setVgap(10);
        editEnrollmentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editEnrollmentGUIGridPane, 500, 250);

        editModuleProgressStage.setScene(scene);
        editModuleProgressStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editModuleProgressStage.close();
        });

        // If saveButton is pressed, the enrollment is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                Integer newPercentage = contentPercentageInput.getValue();
                enrollmentModules.setProgress(newPercentage);
                enrollmentModules.updateProgress(enrollmentModules, databaseConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }

            editModuleProgressStage.close();
        });
    }
}
