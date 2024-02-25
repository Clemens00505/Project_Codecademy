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
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Gender;
import objects.Student;


//TODO: afmaken
public class AddModuleGUI extends Application {

    @Override
    public void start(Stage addModuleStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addModuleStage.setTitle("Module toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input and
        // datepicker for dateofbirth
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        DatePicker dateOfBirthInput = new DatePicker();
        TextField postalCodeInput = new TextField();
        Spinner<Integer> houseNumberInput = new Spinner<>(1, 999, 1);
        TextField cityInput = new TextField();
        TextField countryInput = new TextField();
        

        // setting the defaultdate 20 years back, so that people dont have to click 20
        // times to get to their date of birth
        LocalDate defaultDate = LocalDate.now().minusYears(20);
        dateOfBirthInput.setValue(defaultDate);
        
        genderInput.setValue(Gender.values()[0]);

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        dateOfBirthInput.setPromptText("Geboortedatum");
        postalCodeInput.setPromptText("ABCD12");
        houseNumberInput.setPromptText("0");
        cityInput.setPromptText("Woonplaats");
        countryInput.setPromptText("Land");

        //sets equal width for input
        int equalWidth = 175;
        emailInput.setMinWidth(equalWidth);
        nameInput.setMinWidth(equalWidth);
        genderInput.setMinWidth(equalWidth);
        dateOfBirthInput.setMinWidth(equalWidth);
        postalCodeInput.setMinWidth(equalWidth);
        houseNumberInput.setMinWidth(equalWidth);
        cityInput.setMinWidth(equalWidth);
        countryInput.setMinWidth(equalWidth);

        // labels for input
        Label emailInputLabel = new Label("Email:");
        Label nameInputLabel = new Label("Naam:");
        Label genderInputLabel = new Label("Geslacht:");
        Label dateOfBirthInputLabel = new Label("Geboortedatum:");
        Label postalCodeInputLabel = new Label("Postcode:");
        Label houseNumberInputLabel = new Label("Huisnummer:");
        Label cityInputLabel = new Label("Woonplaats:");
        Label countryInputLabel = new Label("Land:");

        // Create gridpane with a layout
        GridPane addModuleGUIGridPane = new GridPane();
        addModuleGUIGridPane.addRow(0, emailInputLabel, emailInput);
        addModuleGUIGridPane.addRow(1, nameInputLabel, nameInput);
        addModuleGUIGridPane.addRow(2, genderInputLabel, genderInput);
        addModuleGUIGridPane.addRow(3, dateOfBirthInputLabel, dateOfBirthInput);
        addModuleGUIGridPane.addRow(4, postalCodeInputLabel, postalCodeInput);
        addModuleGUIGridPane.addRow(5, houseNumberInputLabel, houseNumberInput);
        addModuleGUIGridPane.addRow(6, cityInputLabel, cityInput);
        addModuleGUIGridPane.addRow(7, countryInputLabel, countryInput);
        addModuleGUIGridPane.addRow(8, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addModuleGUIGridPane.setHgap(10);
        addModuleGUIGridPane.setVgap(10);
        addModuleGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addModuleGUIGridPane, 300, 350);

        addModuleStage.setScene(scene);
        addModuleStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((event) -> {
            addModuleStage.close();
        });

        // If saveButton is pressed, the student is saved to the databse
        saveButton.setOnAction((event) -> {
            try {
                String email = emailInput.getText();
                String name = nameInput.getText();
                Gender gender = genderInput.getValue();
                Date dateOfBirth = Date.valueOf(dateOfBirthInput.getValue());
                String postalCode = postalCodeInput.getText();
                int houseNumber = houseNumberInput.getValue();
                String city = cityInput.getText();
                String country = countryInput.getText();

                //controleert of de velden ingevuld zijn
                if (email.length() == 0 || name.length() == 0 || postalCode.length() < 6 || postalCode.length() > 7 || city.length() == 0 || country.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om een student toe te voegen");
                    errorAlert.showAndWait();
                } else {
                    Student student = new Student(email, name, gender, dateOfBirth, postalCode, houseNumber, city, country);

                    student.addStudent(student, databaseConnection);

                    addModuleStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    

}
