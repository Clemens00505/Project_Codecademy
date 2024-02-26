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
import objects.ContentModule;
import objects.Status;

/**
 * Everything except version and publicationDate is editable
 */

public class EditModuleGUI extends Application {
    private ContentModule contentModule;

    public EditModuleGUI(ContentModule contentModule) {
        this.contentModule = contentModule;
    }
    @Override
    public void start(Stage editModuleStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();

        //Stores old title. used to find the record when updating
        String oldTitle = contentModule.getTitle();

        editModuleStage.setTitle("Module bewerken");

        // buttons for saving and cancelling
        Button confirmButton = new Button("Opslaan");
        Button cancelButton = new Button("Annuleren");

        // add textfields and combobox using the enum objects.Status.java for input and
        // datepicker for publicationDate
        TextField titleInput = new TextField(contentModule.getTitle());
        Spinner<Integer> versionInput = new Spinner<>(1, 999, contentModule.getVersion());
        TextField descriptionInput = new TextField(contentModule.getDescription());
        TextField contactPersonNameInput = new TextField(contentModule.getContactPersonName());
        TextField contactPersonMailInput = new TextField(contentModule.getContactPersonMail());
        DatePicker publicationDateInput = new DatePicker(contentModule.getPublicationDate().toLocalDate());
        ComboBox<Status> statusInput = new ComboBox<>(FXCollections.observableArrayList(Status.values()));
        Spinner<Integer> indexNumberInput = new Spinner<>(1, 999, contentModule.getIndexNumber());

        statusInput.setValue(contentModule.getStatus());

        //make dateOfBirth an gender not editable
        versionInput.setDisable(true);
        publicationDateInput.setDisable(true);

        statusInput.setValue(contentModule.getStatus());

        // add prompttext
        titleInput.setPromptText("Titel");
        versionInput.setPromptText("1");
        descriptionInput.setPromptText("Beschrijving");
        contactPersonNameInput.setPromptText("Naam contactpersoon");
        contactPersonMailInput.setPromptText("Email contactpersoon");
        indexNumberInput.setPromptText("1");

        // labels for input
        Label titleInputLabel = new Label("Titel:");
        Label versionInputLabel = new Label("Versie:");
        Label descriptionInputLabel = new Label("Beschrijving:");
        Label contactPersonNameInputLabel = new Label("Naam contactpersoon:");
        Label contactPersonMailInputLabel = new Label("Email contactpersoon:");
        Label publicationDateInputLabel = new Label("Publicatiedatum:");
        Label statusInputLabel = new Label("Status:");
        Label indexNumberInputLabel = new Label("Volgnummber:");

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

        // Create gridpane with a layout
        GridPane editModuleGUIGridPane = new GridPane();
        editModuleGUIGridPane.addRow(0, titleInputLabel, titleInput);
        editModuleGUIGridPane.addRow(1, versionInputLabel, versionInput);
        editModuleGUIGridPane.addRow(2, descriptionInputLabel, descriptionInput);
        editModuleGUIGridPane.addRow(3, contactPersonNameInputLabel, contactPersonNameInput);
        editModuleGUIGridPane.addRow(4, contactPersonMailInputLabel, contactPersonMailInput);
        editModuleGUIGridPane.addRow(5, publicationDateInputLabel, publicationDateInput);
        editModuleGUIGridPane.addRow(6, statusInputLabel, statusInput);
        editModuleGUIGridPane.addRow(7, indexNumberInputLabel, indexNumberInput);
        editModuleGUIGridPane.addRow(8, cancelButton, confirmButton);

        // gaps and padding in the gridpane
        editModuleGUIGridPane.setHgap(10);
        editModuleGUIGridPane.setVgap(10);
        editModuleGUIGridPane.setPadding(new javafx.geometry.Insets(10));

        Scene scene = new Scene(editModuleGUIGridPane, 500, 350);

        editModuleStage.setScene(scene);
        editModuleStage.show();

        // If cancelButton is pressed, the screen will be closed
        cancelButton.setOnAction((cancelButtonEvent) -> {
            editModuleStage.close();
        });

        // If confirmButton is pressed, the student is edited in the databse
        confirmButton.setOnAction((confirmButtonEvent) -> {
            try {
                String title = titleInput.getText();
                int version = versionInput.getValue();
                String description = descriptionInput.getText();
                String contactPersonName = contactPersonNameInput.getText();
                String contactPersonMail = contactPersonMailInput.getText();
                Date publicationDate = Date.valueOf(publicationDateInput.getValue());
                Status status = statusInput.getValue();
                int indexNumber = indexNumberInput.getValue();

                //controleert of de velden ingevuld zijn
                if (title.length() == 0 || description.length() == 0 || contactPersonName.length() == 0 || contactPersonMail.length() == 0) {
                    Alert errorAlert = new Alert(AlertType.ERROR);
                    errorAlert.setHeaderText("Niet alles ingevuld");
                    errorAlert.setContentText("Vul alle gegevens in om de module te bewerken");
                    errorAlert.showAndWait();
                } else {
                    ContentModule contentModule = new ContentModule(title, version, description, contactPersonName, contactPersonMail, publicationDate, status, indexNumber);

                    contentModule.updateModule(oldTitle, contentModule, databaseConnection);

                    editModuleStage.close();
                }


            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

}
