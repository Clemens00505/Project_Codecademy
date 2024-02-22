package gui;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import objects.Gender;

public class ControllerStudentGUI extends Application {
    @Override
    public void start(Stage controllerStudentGUI) throws Exception {
        controllerStudentGUI.setTitle("Student toevoegen");

        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Gender.java for input
        TextField emailInput = new TextField();
        TextField nameInput = new TextField();
        ComboBox<Gender> genderInput = new ComboBox<>(FXCollections.observableArrayList(Gender.values()));
        TextField dateOfBirthInput = new TextField();

        // add prompttext
        emailInput.setPromptText("abcdefg@gmail.com");
        nameInput.setPromptText("abcdefg");
        genderInput.setPromptText("geslacht");
        dateOfBirthInput.setPromptText("yyyy-mm-dd");

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

        cancelButton.setOnAction((event) -> {
            controllerStudentGUI.close();
        });

    }
}
