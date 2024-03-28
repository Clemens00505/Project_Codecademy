
package gui;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Student;
import objects.Webcast;

public class AddWebcastToStudentGUI extends Application {

    private Student student;

    public AddWebcastToStudentGUI(Student student) {
        this.student = student;
    }

    @Override
    public void start(Stage addWebcastToStudentStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addWebcastToStudentStage.setTitle("Webcast toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add combobox with all available webcasts
        ComboBox<Webcast> webcastInput = new ComboBox<>();

        // Fetch webcasts from the database and populate the ComboBox
        List<Webcast> webcasts = fetchWebcastsFromDatabase(databaseConnection);
        webcastInput.setItems(FXCollections.observableArrayList(webcasts));

        // Display the name of the webcast in the ComboBox
        webcastInput.setConverter(new WebcastStringConverter());

        // sets equal width for input
        int equalWidth = 400;
        webcastInput.setMinWidth(equalWidth);

        // labels for input
        Label titleInputLabel = new Label("Titel:");

        // Create gridpane with a layout
        GridPane addWebcastGUIGridPane = new GridPane();
        addWebcastGUIGridPane.addRow(0, titleInputLabel, webcastInput);
        addWebcastGUIGridPane.addRow(1, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addWebcastGUIGridPane.setHgap(10);
        addWebcastGUIGridPane.setVgap(10);
        addWebcastGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addWebcastGUIGridPane, 500, 350);

        addWebcastToStudentStage.setScene(scene);
        addWebcastToStudentStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            addWebcastToStudentStage.close();
        });

        // If saveButton is pressed, the Webcast is saved to the database
        saveButton.setOnAction((saveButtonEvent) -> {
            // Retrieve the selected webcast from the ComboBox
            Webcast selectedWebcast = webcastInput.getValue();

            // Check if a webcast is selected
            if (selectedWebcast != null) {
                selectedWebcast.addToStudent(selectedWebcast, student, databaseConnection);

                addWebcastToStudentStage.close();

            } else {
                // If no webcast is selected, display an error message
                Alert errorAlert = new Alert(AlertType.ERROR);
                errorAlert.setHeaderText("Geen webcast geselecteerd");
                errorAlert.setContentText("Selecteer een webcast voordat u opslaat.");
                errorAlert.showAndWait();
            }
        });

    }

    // Method to fetch webcasts from the database
    private List<Webcast> fetchWebcastsFromDatabase(DatabaseConnection databaseConnection) throws SQLException {
        List<Webcast> webcasts = new ArrayList<>();

        // Open database connection
        databaseConnection.openConnection();

        // Execute SQL query to fetch webcasts
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Webcast");

        // Iterate through the result set and create Webcast objects
        while (resultSet.next()) {
            int contentId = resultSet.getInt("ContentId");
            String title = resultSet.getString("Title");
            String description = resultSet.getString("Description");
            // Similarly, retrieve other columns as required

            // Create a Webcast object and add it to the list
            Webcast webcast = new Webcast(contentId, title, description);
            webcasts.add(webcast);
        }

        // Close database connection
        databaseConnection.closeConnection();

        return webcasts;
    }

    // Custom StringConverter to display only webcast titles in the ComboBox
    private static class WebcastStringConverter extends javafx.util.StringConverter<Webcast> {
        @Override
        public String toString(Webcast webcast) {
            return (webcast != null) ? webcast.getTitle() : "";
        }

        @Override
        public Webcast fromString(String string) {
            throw new UnsupportedOperationException("Not supported");
        }
    }

}
