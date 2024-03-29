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
import objects.URLTools;
import objects.Webcast;


public class EditWebcastGUI extends Application {
    private Webcast webcast;

    public EditWebcastGUI(Webcast webcast) {
        this.webcast = webcast;
    }
    @Override
    public void start(Stage editWebcastStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        editWebcastStage.setTitle("Webcast bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Status.java for input and
        // datepicker for publicationDate
        TextField titleInput = new TextField(webcast.getTitle());
        TextField descriptionInput = new TextField(webcast.getDescription());
        TextField speakerNameInput = new TextField(webcast.getSpeakerName());
        TextField speakerOrganisationInput = new TextField(webcast.getSpeakerOrganisation());
        DatePicker publicationDateInput = new DatePicker(webcast.getPublicationDate().toLocalDate());
        ComboBox<Status> statusInput = new ComboBox<>(FXCollections.observableArrayList(Status.values()));
        Spinner<Integer> timesViewedInput = new Spinner<>(1, 999999999, webcast.getTimesViewed());
        TextField URLInput = new TextField(webcast.getURL());

        statusInput.setValue(webcast.getStatus());

        publicationDateInput.setDisable(true);

        statusInput.setValue(webcast.getStatus());

        // add prompttext
        titleInput.setPromptText("Titel");
        descriptionInput.setPromptText("Beschrijving");
        speakerNameInput.setPromptText("Naam spreker");
        speakerOrganisationInput.setPromptText("Organisatie spreker");
        publicationDateInput.setPromptText("Publicatiedatum");
        URLInput.setPromptText("url");

        // labels for input
        Label titleInputLabel = new Label("Titel:");
        Label descriptionInputLabel = new Label("Beschrijving:");
        Label speakerNameInputLabel = new Label("Naam spreker:");
        Label speakerOrganisationInputLabel = new Label("Organisatie spreker:");
        Label publicationDateInputLabel = new Label("Publicatiedatum:");
        Label statusInputLabel = new Label("Status:");
        Label urlInputLabel = new Label("Url:");
        Label timesViewedLabel = new Label("Aantal keer bekeken:");

        //sets equal width for input
        int equalWidth = 400;
        titleInput.setMinWidth(equalWidth);
        descriptionInput.setMinWidth(equalWidth);
        speakerNameInput.setMinWidth(equalWidth);
        speakerOrganisationInput.setMinWidth(equalWidth);
        publicationDateInput.setMinWidth(equalWidth);
        statusInput.setMinWidth(equalWidth);
        URLInput.setMinWidth(equalWidth);
        timesViewedInput.setMinWidth(equalWidth);

        //create gridpane
        GridPane editWebcastGUIGridPane = new GridPane();
        editWebcastGUIGridPane.addRow(0, titleInputLabel, titleInput);
        editWebcastGUIGridPane.addRow(2, descriptionInputLabel, descriptionInput);
        editWebcastGUIGridPane.addRow(3, speakerNameInputLabel, speakerNameInput);
        editWebcastGUIGridPane.addRow(4, speakerOrganisationInputLabel, speakerOrganisationInput);
        editWebcastGUIGridPane.addRow(5, publicationDateInputLabel, publicationDateInput);
        editWebcastGUIGridPane.addRow(6, statusInputLabel, statusInput);
        editWebcastGUIGridPane.addRow(7, urlInputLabel, URLInput);
        editWebcastGUIGridPane.addRow(8, timesViewedLabel, timesViewedInput);
        editWebcastGUIGridPane.addRow(9, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editWebcastGUIGridPane.setHgap(10);
        editWebcastGUIGridPane.setVgap(10);
        editWebcastGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editWebcastGUIGridPane, 500, 350);

        editWebcastStage.setScene(scene);
        editWebcastStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editWebcastStage.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((confirmButtonEvent) -> {
            try {
            
                int contentId = webcast.getContentId();
                String title = titleInput.getText();
                String description = descriptionInput.getText();
                String speakerName = speakerNameInput.getText();
                String speakerOrganisation = speakerOrganisationInput.getText();
                Date publicationDate = Date.valueOf(publicationDateInput.getValue());
                Status status = statusInput.getValue();
                String URL = urlInputLabel.getText();
                int timesViewed = timesViewedInput.getValue();


                //checks if all fields are filled
                if (title.length() == 0 || description.length() == 0 || speakerName.length() == 0 || speakerOrganisation.length() == 0 || !URLTools.validateURL(URL)) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om de Webcast te bewerken");
                    errorAlert.showAndWait();
                } else {
                    Webcast webcast = new Webcast(contentId, title, description, speakerName, speakerOrganisation,
                    publicationDate, status, URL, timesViewed);

                    webcast.updateWebcast(contentId, webcast, databaseConnection);

                    editWebcastStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
