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
import objects.Status;
import objects.ContentModule;
import objects.MailTools;

public class AddModuleGUI extends Application {

    @Override
    public void start(Stage addModuleStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addModuleStage.setTitle("Module toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Status.java for input and
        // datepicker for publicationDate
        TextField titleInput = new TextField();
        Spinner<Integer> versionInput = new Spinner<>(1, 999, 1);
        TextField descriptionInput = new TextField();
        TextField contactPersonNameInput = new TextField();
        TextField contactPersonMailInput = new TextField();
        DatePicker publicationDateInput = new DatePicker();
        ComboBox<Status> statusInput = new ComboBox<>(FXCollections.observableArrayList(Status.values()));
        Spinner<Integer> indexNumberInput = new Spinner<>(1, 999, 1);
        
        statusInput.setValue(Status.values()[0]);

        // add prompttext
        titleInput.setPromptText("Titel");
        versionInput.setPromptText("1");
        descriptionInput.setPromptText("Beschrijving");
        contactPersonNameInput.setPromptText("Naam contactpersoon");
        contactPersonMailInput.setPromptText("Email contactpersoon");
        publicationDateInput.setPromptText("Publicatiedatum");
        indexNumberInput.setPromptText("1");

        //sets equal width for input
        int equalWidth = 400;
        titleInput.setMinWidth(equalWidth);
        versionInput.setMinWidth(equalWidth);
        descriptionInput.setMinWidth(equalWidth);
        contactPersonNameInput.setMinWidth(equalWidth);
        contactPersonMailInput.setMinWidth(equalWidth);
        publicationDateInput.setMinWidth(equalWidth);
        statusInput.setMinWidth(equalWidth);
        indexNumberInput.setMinWidth(equalWidth);

        // labels for input
        Label titleInputLabel = new Label("Titel:");
        Label versionInputLabel = new Label("Versie:");
        Label descriptionInputLabel = new Label("Beschrijving:");
        Label contactPersonNameInputLabel = new Label("Naam contactpersoon:");
        Label contactPersonMailInputLabel = new Label("Email contactpersoon:");
        Label publicationDateInputLabel = new Label("Publicatiedatum:");
        Label statusInputLabel = new Label("Status:");
        Label indexNumberInputLabel = new Label("Volgnummber:");

        // Create gridpane with a layout
        GridPane addModuleGUIGridPane = new GridPane();
        addModuleGUIGridPane.addRow(0, titleInputLabel, titleInput);
        addModuleGUIGridPane.addRow(1, versionInputLabel, versionInput);
        addModuleGUIGridPane.addRow(2, descriptionInputLabel, descriptionInput);
        addModuleGUIGridPane.addRow(3, contactPersonNameInputLabel, contactPersonNameInput);
        addModuleGUIGridPane.addRow(4, contactPersonMailInputLabel, contactPersonMailInput);
        addModuleGUIGridPane.addRow(5, publicationDateInputLabel, publicationDateInput);
        addModuleGUIGridPane.addRow(6, statusInputLabel, statusInput);
        addModuleGUIGridPane.addRow(7, indexNumberInputLabel, indexNumberInput);
        addModuleGUIGridPane.addRow(8, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addModuleGUIGridPane.setHgap(10);
        addModuleGUIGridPane.setVgap(10);
        addModuleGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addModuleGUIGridPane, 500, 350);

        addModuleStage.setScene(scene);
        addModuleStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            addModuleStage.close();
        });

        // If saveButton is pressed, the module is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                String title = titleInput.getText();
                int version = versionInput.getValue();
                String description = descriptionInput.getText();
                String contactPersonName = contactPersonNameInput.getText();
                String contactPersonMail = contactPersonMailInput.getText();
                Date publicationDate = Date.valueOf(publicationDateInput.getValue());
                Status status = statusInput.getValue();
                int indexNumber = indexNumberInput.getValue();

                //checks if all fields are filled
                if (title.length() == 0 || description.length() == 0 || contactPersonName.length() == 0 || !MailTools.validateMailAddress(contactPersonMail)) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om een module toe te voegen");
                    errorAlert.showAndWait();
                } else {
                    ContentModule module = new ContentModule(title, version, description, contactPersonName, contactPersonMail, publicationDate, status, indexNumber);

                    module.addModule(module, databaseConnection);

                    addModuleStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    

}
