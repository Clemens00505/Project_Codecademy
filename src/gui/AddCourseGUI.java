package gui;

import java.sql.SQLException;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Course;
import objects.Difficulty;

public class AddCourseGUI extends Application {

    @Override
    public void start(Stage addCourseStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addCourseStage.setTitle("Cursus toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Difficulty.java for input 
        TextField courseNameInput = new TextField();
        TextField subjectInput = new TextField();
        TextField introTextInput = new TextField();
        ComboBox<Difficulty> difficultyInput = new ComboBox<>(FXCollections.observableArrayList(Difficulty.values()));
        
        difficultyInput.setValue(Difficulty.values()[0]);

        // add prompttext
        courseNameInput.setPromptText("Naam cursus");
        subjectInput.setPromptText("Onderwerp cursus");
        introTextInput.setPromptText("Korte introductie over de cursus");

        //sets equal width for input
        int equalWidth = 400;
        courseNameInput.setMinWidth(equalWidth);
        subjectInput.setMinWidth(equalWidth);
        introTextInput.setMinWidth(equalWidth);

        // labels for input
        Label courseNameInputLabel = new Label("Cursusnaam:");
        Label subjectInputLabel = new Label("Onderwerp:");
        Label introTextInputLabel = new Label("Introductietekst:");
        Label difficultyInputLabel = new Label("Niveau:");

        // Create gridpane with a layout
        GridPane addModuleGUIGridPane = new GridPane();
        addModuleGUIGridPane.addRow(0, courseNameInputLabel, courseNameInput);
        addModuleGUIGridPane.addRow(1, subjectInputLabel, subjectInput);
        addModuleGUIGridPane.addRow(2, introTextInputLabel, introTextInput);
        addModuleGUIGridPane.addRow(3, difficultyInputLabel, difficultyInput);
        addModuleGUIGridPane.addRow(4, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addModuleGUIGridPane.setHgap(10);
        addModuleGUIGridPane.setVgap(10);
        addModuleGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addModuleGUIGridPane, 500, 250);

        addCourseStage.setScene(scene);
        addCourseStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            addCourseStage.close();
        });

        // If saveButton is pressed, the module is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                String courseName = courseNameInput.getText();
                String subject = subjectInput.getText();
                String introText = introTextInput.getText();
                Difficulty difficulty = difficultyInput.getValue();

                //checks if all fields are filled
                if (courseName.length() == 0 || subject.length() == 0 || introText.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om een cursus toe te voegen");
                    errorAlert.showAndWait();
                } else {
                    Course course = new Course(courseName, subject, introText, difficulty); 

                    course.addCourse(course, databaseConnection);

                    addCourseStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
