package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.StudentWebcast;

public class EditWebcastProgressGUI extends Application {

    private StudentWebcast webcast;

    public EditWebcastProgressGUI(StudentWebcast webcast) {
        this.webcast = webcast;
    }

    @Override
    public void start(Stage editWebcastStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        editWebcastStage.setTitle("Webcast bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        //spinner to edit percentage
        Spinner<Integer> progressInput = new Spinner<>(0, 100, webcast.getProgressPercentage());

        // labels for input
        Label progressInputLabel = new Label("Voortgang:");


        //sets equal width for input
        int equalWidth = 400;
        progressInput.setMinWidth(equalWidth);

        //create gridpane
        GridPane editWebcastGUIGridPane = new GridPane();
        editWebcastGUIGridPane.addRow(0, progressInputLabel, progressInput);
        editWebcastGUIGridPane.addRow(1, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editWebcastGUIGridPane.setHgap(10);
        editWebcastGUIGridPane.setVgap(10);
        editWebcastGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editWebcastGUIGridPane, 500, 350);

        editWebcastStage.setScene(scene);
        editWebcastStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editWebcastStage.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((confirmButtonEvent) -> {
            try {
            
                int studentWebcastProgressId = webcast.getStudentWebcastProgressid();
                int progress = progressInput.getValue();

                webcast.updateProgress(studentWebcastProgressId, progress, databaseConnection);

                editWebcastStage.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
