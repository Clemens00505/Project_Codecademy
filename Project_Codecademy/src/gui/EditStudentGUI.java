package gui;

import java.sql.Date;
import java.sql.SQLException;

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

public class EditStudentGUI extends Application {
    private Student student;

    public EditStudentGUI(Student student) {
        this.student = student;
    }

    @Override
    public void start(Stage editStudentGUI) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        // Stores old email. Used to update student 
        String oldEmail = student.getEmail();

        editStudentGUI.setTitle("Student bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input and
        // datepicker for dateofbirth
        TextField emailInput = new TextField(student.getEmail());
        TextField nameInput = new TextField(student.getName());
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        DatePicker dateOfBirthInput = new DatePicker(student.getDateOfBirth().toLocalDate());

        //make dateOfBirth an gender not editable
        genderInput.setDisable(true);
        dateOfBirthInput.setDisable(true);

        genderInput.setValue(student.getGender());

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
        GridPane editStudentGUIGridPane = new GridPane();
        editStudentGUIGridPane.addRow(0, emailInputLabel, emailInput);
        editStudentGUIGridPane.addRow(1, nameInputLabel, nameInput);
        editStudentGUIGridPane.addRow(2, genderInputLabel, genderInput);
        editStudentGUIGridPane.addRow(3, dateOfBirthInputLabel, dateOfBirthInput);
        editStudentGUIGridPane.addRow(4, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editStudentGUIGridPane.setHgap(10);
        editStudentGUIGridPane.setVgap(10);
        editStudentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editStudentGUIGridPane, 300, 200);

        editStudentGUI.setScene(scene);
        editStudentGUI.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((event) -> {
            editStudentGUI.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((event) -> {
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

                    student.updateStudent(oldEmail, student, databaseConnection);

                    editStudentGUI.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
