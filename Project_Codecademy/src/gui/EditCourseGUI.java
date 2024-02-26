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

public class EditCourseGUI extends Application {
    private Course course;

    public EditCourseGUI(Course course) {
        this.course = course;
    }

    @Override
    public void start(Stage editCourseStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        editCourseStage.setTitle("Module bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Difficulty.java for input
        TextField courseNameInput = new TextField(course.getCourseName());
        TextField subjectInput = new TextField(course.getSubject());
        TextField introTextInput = new TextField(course.getIntroText());
        ComboBox<Difficulty> difficultyInput = new ComboBox<>(FXCollections.observableArrayList(Difficulty.values()));

        difficultyInput.setValue(course.getDifficulty());

        // add prompttext
        courseNameInput.setPromptText("Naam cursus");
        subjectInput.setPromptText("Onderwerp cursus");
        introTextInput.setPromptText("Korte introductie over de cursus");

        // labels for input
        Label courseNameInputLabel = new Label("Cursusnaam:");
        Label subjectInputLabel = new Label("Onderwerp:");
        Label introTextInputLabel = new Label("Introductietekst:");
        Label difficultyInputLabel = new Label("Niveau:");

        // sets equal width for input
        int equalWidth = 400;
        courseNameInput.setMinWidth(equalWidth);
        subjectInput.setMinWidth(equalWidth);
        introTextInput.setMinWidth(equalWidth);

        // Create gridpane with a layout
        GridPane editCourseGUIGridPane = new GridPane();
        editCourseGUIGridPane.addRow(0, courseNameInputLabel, courseNameInput);
        editCourseGUIGridPane.addRow(1, subjectInputLabel, subjectInput);
        editCourseGUIGridPane.addRow(2, introTextInputLabel, introTextInput);
        editCourseGUIGridPane.addRow(3, difficultyInputLabel, difficultyInput);
        editCourseGUIGridPane.addRow(4, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editCourseGUIGridPane.setHgap(10);
        editCourseGUIGridPane.setVgap(10);
        editCourseGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editCourseGUIGridPane, 500, 350);

        editCourseStage.setScene(scene);
        editCourseStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editCourseStage.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((confirmButtonEvent) -> {
            try {
                int courseId = course.getCourseId();
                String courseName = courseNameInput.getText();
                String subject = subjectInput.getText();
                String introText = introTextInput.getText();
                Difficulty difficulty = difficultyInput.getValue();

                // checks if all fields are filled
                if (courseName.length() == 0 || subject.length() == 0 || introText.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om de cursus te bewerken");
                    errorAlert.showAndWait();
                } else {
                    Course course = new Course(courseName, subject, introText, difficulty);

                    course.updateCourse(courseId, course, databaseConnection);

                    editCourseStage.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
