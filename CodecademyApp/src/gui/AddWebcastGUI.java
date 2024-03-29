
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
import objects.Status;
import objects.URLTools;
import objects.Webcast;

public class AddWebcastGUI extends Application {

    @Override
    public void start(Stage addWebcastStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        addWebcastStage.setTitle("Webcast toevoegen");

        // buttons for saving and cancelling
        Button saveButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Status.java for input and
        // datepicker for publicationDate
        TextField titleInput = new TextField();
        TextField descriptionInput = new TextField();
        TextField speakerNameInput = new TextField();
        TextField speakerOrganisationInput = new TextField();
        DatePicker publicationDateInput = new DatePicker();
        ComboBox<Status> statusInput = new ComboBox<>(FXCollections.observableArrayList(Status.values()));
        TextField urlInput = new TextField();
        
        statusInput.setValue(Status.values()[0]);

        // add prompttext
        titleInput.setPromptText("Titel");
        descriptionInput.setPromptText("Beschrijving");
        speakerNameInput.setPromptText("Naam spreker");
        speakerOrganisationInput.setPromptText("Organisatie spreker");
        publicationDateInput.setPromptText("Publicatiedatum");
        urlInput.setPromptText("url");

        //sets equal width for input
        int equalWidth = 400;
        titleInput.setMinWidth(equalWidth);
        descriptionInput.setMinWidth(equalWidth);
        speakerNameInput.setMinWidth(equalWidth);
        speakerOrganisationInput.setMinWidth(equalWidth);
        publicationDateInput.setMinWidth(equalWidth);
        statusInput.setMinWidth(equalWidth);
        urlInput.setMinWidth(equalWidth);

        // labels for input
        Label titleInputLabel = new Label("Titel:");
        Label descriptionInputLabel = new Label("Beschrijving:");
        Label speakerNameInputLabel = new Label("Naam spreker:");
        Label speakerOrganisationInputLabel = new Label("Organisatie spreker:");
        Label publicationDateInputLabel = new Label("Publicatiedatum:");
        Label statusInputLabel = new Label("Status:");
        Label urlInputLabel = new Label("Url:");

        // Create gridpane with a layout
        GridPane addWebcastGUIGridPane = new GridPane();
        addWebcastGUIGridPane.addRow(0, titleInputLabel, titleInput);
        addWebcastGUIGridPane.addRow(2, descriptionInputLabel, descriptionInput);
        addWebcastGUIGridPane.addRow(3, speakerNameInputLabel, speakerNameInput);
        addWebcastGUIGridPane.addRow(4, speakerOrganisationInputLabel, speakerOrganisationInput);
        addWebcastGUIGridPane.addRow(5, publicationDateInputLabel, publicationDateInput);
        addWebcastGUIGridPane.addRow(6, statusInputLabel, statusInput);
        addWebcastGUIGridPane.addRow(7, urlInputLabel, urlInput);
        addWebcastGUIGridPane.addRow(8, cancelButton, saveButton);

        // gaps and padding in the gridpane
        addWebcastGUIGridPane.setHgap(10);
        addWebcastGUIGridPane.setVgap(10);
        addWebcastGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(addWebcastGUIGridPane, 500, 350);

        addWebcastStage.setScene(scene);
        addWebcastStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            addWebcastStage.close();
        });

        // If saveButton is pressed, the Webcast is saved to the database
        saveButton.setOnAction((event) -> {
            try {
                String title = titleInput.getText();
                String description = descriptionInput.getText();
                String speakerName = speakerNameInput.getText();
                String speakerOrganisation = speakerOrganisationInput.getText();
                Date publicationDate = Date.valueOf(publicationDateInput.getValue());
                Status status = statusInput.getValue();
                String url = urlInput.getText();

                //checks if all fields are filled
                if (title.length() == 0 || description.length() == 0 || speakerName.length() == 0 || speakerOrganisation.length() == 0 || !URLTools.validateURL(url)) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om een Webcast toe te voegen");
                    errorAlert.showAndWait();
                } else {
                    Webcast webcast = new Webcast(title, description, speakerName, speakerOrganisation, publicationDate, status, url);

                    webcast.addWebcast(webcast, databaseConnection);

                    addWebcastStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
    

}
