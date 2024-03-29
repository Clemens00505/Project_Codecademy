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
import javafx.scene.control.Spinner;
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
    public void start(Stage editStudentStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        // Stores old email. Used to update student 
        String oldEmail = student.getEmail();

        editStudentStage.setTitle("Student bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input and
        // datepicker for publicationDate
        TextField emailInput = new TextField(student.getEmail());
        TextField nameInput = new TextField(student.getName());
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        DatePicker dateOfBirthInput = new DatePicker(student.getDateOfBirth().toLocalDate());
        TextField postalCodeInput = new TextField(student.getPostalCode());
        Spinner<Integer> houseNumberInput = new Spinner<>(1, 999, student.getHouseNumber());
        TextField cityInput = new TextField(student.getCity());
        TextField countryInput = new TextField(student.getCountry());

        //make dateOfBirth an gender not editable
        genderInput.setDisable(true);
        dateOfBirthInput.setDisable(true);

        genderInput.setValue(student.getGender());

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        postalCodeInput.setPromptText("ABCD12");
        houseNumberInput.setPromptText("0");
        cityInput.setPromptText("Woonplaats");
        countryInput.setPromptText("Land");

        // labels for input
        Label emailInputLabel = new Label("Email:");
        Label nameInputLabel = new Label("Naam:");
        Label genderInputLabel = new Label("Geslacht:");
        Label dateOfBirthInputLabel = new Label("Geboortedatum:");
        Label postalCodeInputLabel = new Label("Postcode:");
        Label houseNumberInputLabel = new Label("Huisnummer:");
        Label cityInputLabel = new Label("Woonplaats:");
        Label countryInputLabel = new Label("Land:");

        //sets equal width for input fields
        int equalWidth = 175;
        emailInput.setMinWidth(equalWidth);
        nameInput.setMinWidth(equalWidth);
        genderInput.setMinWidth(equalWidth);
        dateOfBirthInput.setMinWidth(equalWidth);
        postalCodeInput.setMinWidth(equalWidth);
        houseNumberInput.setMinWidth(equalWidth);
        cityInput.setMinWidth(equalWidth);
        countryInput.setMinWidth(equalWidth);

        // Create gridpane with a layout
        GridPane editStudentGUIGridPane = new GridPane();
        editStudentGUIGridPane.addRow(1, nameInputLabel, nameInput);
        editStudentGUIGridPane.addRow(0, emailInputLabel, emailInput);
        editStudentGUIGridPane.addRow(2, genderInputLabel, genderInput);
        editStudentGUIGridPane.addRow(3, dateOfBirthInputLabel, dateOfBirthInput);
        editStudentGUIGridPane.addRow(4, postalCodeInputLabel, postalCodeInput);
        editStudentGUIGridPane.addRow(5, houseNumberInputLabel, houseNumberInput);
        editStudentGUIGridPane.addRow(6, cityInputLabel, cityInput);
        editStudentGUIGridPane.addRow(7, countryInputLabel, countryInput);
        editStudentGUIGridPane.addRow(8, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editStudentGUIGridPane.setHgap(10);
        editStudentGUIGridPane.setVgap(10);
        editStudentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editStudentGUIGridPane, 300, 350);

        editStudentStage.setScene(scene);
        editStudentStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editStudentStage.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((confirmButtonEvent) -> {
            try {
                String email = emailInput.getText();
                String name = nameInput.getText();
                Gender gender = genderInput.getValue();
                Date dateOfBirth = Date.valueOf(dateOfBirthInput.getValue());
                String postalCode = postalCodeInput.getText();
                int houseNumber = houseNumberInput.getValue();
                String city = cityInput.getText();
                String country = countryInput.getText();

                //checks if all fields are filled
                if (email.length() == 0 || name.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om de student te bewerken");
                    errorAlert.showAndWait();
                } else {
                    Student student = new Student(email, name, gender, dateOfBirth, postalCode, houseNumber, city, country);

                    student.updateStudent(oldEmail, student, databaseConnection);

                    editStudentStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}
