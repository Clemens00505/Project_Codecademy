package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Status;
import objects.Webcast;

public class MostViewedWebcastsGUI extends Application{

    @Override
    public void start(Stage mostViewedWebcastsStage) throws Exception {
        // create a GenericGU
        GenericGUI<Webcast> genericGUI = new GenericGUI<>();

        mostViewedWebcastsStage.show();
        mostViewedWebcastsStage.setTitle("Webcasts");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button backButton = new Button("Terug naar webcasts");

        // create labels and textareas to show title and description since table columns
        // are narrow
        Label titleShow = new Label("Titel: ");
        Label descriptionShow = new Label("Beschrijving: ");
        TextArea titleShowText = new TextArea();
        TextArea descriptionShowText = new TextArea();

        // sets sizes for textareas to wrap text and uneditable
        titleShowText.setEditable(false);
        titleShowText.setWrapText(true);
        descriptionShowText.setEditable(false);
        descriptionShowText.setWrapText(true);

        //sets equal width for buttons
        int equalWidth = 175;
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<Webcast, String> titleCol = new TableColumn<>("Titel");
        TableColumn<Webcast, String> descriptionCol = new TableColumn<>("Beschrijving");
        TableColumn<Webcast, String> speakerNameCol = new TableColumn<>("Naam spreker");
        TableColumn<Webcast, String> speakerOrganisationCol = new TableColumn<>("Organisatie spreker");
        TableColumn<Webcast, Date> publicationDateCol = new TableColumn<>("Publicatiedatum");
        TableColumn<Webcast, Status> statusCol = new TableColumn<>("Status");
        TableColumn<Webcast, String> URLCol = new TableColumn<>("URL");
        TableColumn<Webcast, Integer> timesViewedCol = new TableColumn<>("Aantal keer bekeken");
        TableColumn<Webcast, Integer> contentIdCol = new TableColumn<>("ContentId");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        speakerNameCol.setCellValueFactory(new PropertyValueFactory<>("speakerName"));
        speakerOrganisationCol.setCellValueFactory(new PropertyValueFactory<>("speakerOrganisation"));
        publicationDateCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("Status"));
        URLCol.setCellValueFactory(new PropertyValueFactory<>("URL"));
        timesViewedCol.setCellValueFactory(new PropertyValueFactory<>("timesViewed"));
        contentIdCol.setCellValueFactory(new PropertyValueFactory<>("contenId"));

        // add columns to a list
        List<TableColumn<Webcast, ?>> columns = new ArrayList<>();
        columns.add(titleCol);
        columns.add(descriptionCol);
        columns.add(speakerNameCol);
        columns.add(speakerOrganisationCol);
        columns.add(publicationDateCol);
        columns.add(statusCol);
        columns.add(URLCol);
        columns.add(timesViewedCol);

        // create the table
        TableView<Webcast> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT TOP(3) * FROM Webcast ORDER BY TimesViewed DESC");

        // puts the data from the resultset in an observablelist
        ObservableList<Webcast> data = genericGUI.getData(resultSet, Webcast.class);

        // displays data in the table
        table.setItems(data);

        // displays the title and description on the right side of the screen
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                titleShowText.setText(newSelection.getTitle());
                descriptionShowText.setText(newSelection.getDescription());
            } else {
                titleShowText.setText("");
                descriptionShowText.setText("");
            }
        });

        // Gives the table and columns a good size on the screen
        table.setPrefWidth(950);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE);
        });

        // placing everything in the screen
        // put buttons in a vbox
        VBox buttons = new VBox(backButton);

        VBox showData = new VBox(titleShow, titleShowText, descriptionShow, descriptionShowText);

        buttons.setPrefWidth(200);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons, showData);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        mostViewedWebcastsStage.setScene(scene);

        backButton.setOnAction((backButtonEvent) -> {
            ContentWebcastGUI webcastGUI = new ContentWebcastGUI();

            Stage webcastStage = new Stage();
            webcastStage.setTitle("Webcasts");

            genericGUI.switchScreen(mostViewedWebcastsStage, webcastStage, webcastGUI);
        });

    }

}

