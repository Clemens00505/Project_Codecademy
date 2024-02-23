package gui;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Gender;
import objects.Student;

public class addStudentGUI extends Application {
    @Override
    public void start(Stage addStudentGUI) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addStudentGUI.setTitle("Student toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input and
        // datepicker for dateofbirth
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        DatePicker dateOfBirthInput = new DatePicker();

        // setting the defaultdate 20 years back, so that people dont have to click 20
        // times to get to their date of birth
        LocalDate defaultDate = LocalDate.now().minusYears(20);
        dateOfBirthInput.setValue(defaultDate);

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("MAN");
        dateOfBirthInput.setPromptText("Geboortedatum");

        // labels for input
        Label emailInputLabel = new Label("Email:");
        Label nameInputLabel = new Label("Naam:");
        Label genderInputLabel = new Label("Geslacht:");
        Label dateOfBirthInputLabel = new Label("Geboortedatum:");

        // Create gridpane with a layout
        GridPane addStudentGUIGridPane = new GridPane();
        addStudentGUIGridPane.addRow(0, emailInputLabel, emailInput);
        addStudentGUIGridPane.addRow(1, nameInputLabel, nameInput);
        addStudentGUIGridPane.addRow(2, genderInputLabel, genderInput);
        addStudentGUIGridPane.addRow(3, dateOfBirthInputLabel, dateOfBirthInput);
        addStudentGUIGridPane.addRow(4, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addStudentGUIGridPane.setHgap(10);
        addStudentGUIGridPane.setVgap(10);
        addStudentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addStudentGUIGridPane, 300, 200);

        addStudentGUI.setScene(scene);
        addStudentGUI.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((event) -> {
            addStudentGUI.close();
        });

        // If saveButton is pressed, the student is saved to the databse
        saveButton.setOnAction((event) -> {
            try {
                String email = emailInput.getText();
                String name = nameInput.getText();
                Gender gender = genderInput.getValue();
                Date dateOfBirth = Date.valueOf(dateOfBirthInput.getValue());

                //controleert of de velden ingevuld zijn
                if (email.length() == 0 || name.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om een student toe te voegen");
                    errorAlert.showAndWait();
                } else {
                    Student student = new Student(email, name, gender, dateOfBirth);

                    student.addStudent(student, databaseConnection);

                    addStudentGUI.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
