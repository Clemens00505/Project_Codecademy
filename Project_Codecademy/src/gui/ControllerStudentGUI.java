package gui;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Gender;
import objects.Student;

public class ControllerStudentGUI extends Application {
    @Override
    public void start(Stage controllerStudentGUI) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        controllerStudentGUI.setTitle("Student toevoegen");

        //buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input and datepicker for dateofbirth
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        DatePicker dateOfBirthInput = new DatePicker(); 
        
        //setting the defaultdate 20 years back, so that people dont have to click 20 times to get to their date of birth
        LocalDate defaultDate = LocalDate.now().minusYears(20);
        dateOfBirthInput.setValue(defaultDate);

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("MAN");
        dateOfBirthInput.setPromptText("Geboortedatum");

        //labels for input
        Label emailInputLabel = new Label("Email:");
        Label nameInputLabel = new Label("Naam:");
        Label genderInputLabel = new Label("Geslacht:");
        Label dateOfBirthInputLabel = new Label("Geboortedatum:");

        // Create gridpane with a layout
        GridPane controllerStudentGUIGridPane = new GridPane();
        controllerStudentGUIGridPane.addRow(0, emailInputLabel, emailInput);
        controllerStudentGUIGridPane.addRow(1, nameInputLabel, nameInput);
        controllerStudentGUIGridPane.addRow(2, genderInputLabel, genderInput);
        controllerStudentGUIGridPane.addRow(3, dateOfBirthInputLabel, dateOfBirthInput);
        controllerStudentGUIGridPane.addRow(4, cancelButton, saveButton);

        // gaps and padding in the gridpane
        controllerStudentGUIGridPane.setHgap(10);
        controllerStudentGUIGridPane.setVgap(10);
        controllerStudentGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(controllerStudentGUIGridPane, 300, 200);

        controllerStudentGUI.setScene(scene);
        controllerStudentGUI.show();

        //If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((event) -> {
            controllerStudentGUI.close();
        });

        //If saveButton is pressed, the student is saved to the databse
        saveButton.setOnAction((event) -> {
            try {
                String email = emailInput.getText();
                String name = nameInput.getText();
                String gender = genderInput.getPromptText();
                Date dateOfBirth = Date.valueOf(dateOfBirthInput.getValue());
    
                Student student = new Student(email, name, gender, dateOfBirth);
    
                student.addStudent(student, databaseConnection);

                controllerStudentGUI.close();
    
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        });
    }
}
