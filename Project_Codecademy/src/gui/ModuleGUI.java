package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import objects.ContentModule;
import objects.Status;

public class ModuleGUI extends Application {

    @Override
    public void start(Stage moduleStage) throws Exception {
        // create a GenericGU
        GenericGUI<ContentModule> genericGUI = new GenericGUI<>();

        moduleStage.show();
        moduleStage.setTitle("Modules");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addModuleButton = new Button("module toevoegen");
        Button editModuleButton = new Button("module bewerken");
        Button deleteModuleButton = new Button("module verwijderen");
        Button confirmButton = new Button("Aanpassing bevestigen");
        Button backButton = new Button("Terug naar hoofdmenu");

        // create labels and textareas to show title and description since table columns
        // are narrow
        Label titleShow = new Label("Titel: ");
        Label descriptionShow = new Label("Beschrijving: ");
        TextArea titleShowText = new TextArea();
        TextArea descriptionShowText = new TextArea();

        //sets sizes for textareas to wrap text and uneditable
        titleShowText.setEditable(false);
        titleShowText.setWrapText(true);
        descriptionShowText.setEditable(false);
        descriptionShowText.setWrapText(true);
        
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addModuleButton.setMinWidth(equalWidth);
        editModuleButton.setMinWidth(equalWidth);
        deleteModuleButton.setMinWidth(equalWidth);
        confirmButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<ContentModule, String> titleCol = new TableColumn<>("Titel");
        TableColumn<ContentModule, Integer> versionCol = new TableColumn<>("Versie");
        TableColumn<ContentModule, String> descriptionCol = new TableColumn<>("Beschrijving");
        TableColumn<ContentModule, String> contactPersonNameCol = new TableColumn<>("Naam contactpersoon");
        TableColumn<ContentModule, String> contactPersonMailCol = new TableColumn<>("Mail contactpersoon");
        TableColumn<ContentModule, Date> publicationDateCol = new TableColumn<>("Publicatiedatum");
        TableColumn<ContentModule, Status> statusCol = new TableColumn<>("Status");
        TableColumn<ContentModule, Integer> indexNumberCol = new TableColumn<>("Volgnummer");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactPersonNameCol.setCellValueFactory(new PropertyValueFactory<>("contactPersonName"));
        contactPersonMailCol.setCellValueFactory(new PropertyValueFactory<>("contactPersonMail"));
        publicationDateCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        indexNumberCol.setCellValueFactory(new PropertyValueFactory<>("indexNumber"));

        // add columns to a list
        List<TableColumn<ContentModule, ?>> columns = new ArrayList<>();
        columns.add(titleCol);
        columns.add(versionCol);
        columns.add(descriptionCol);
        columns.add(contactPersonNameCol);
        columns.add(contactPersonMailCol);
        columns.add(publicationDateCol);
        columns.add(statusCol);
        columns.add(indexNumberCol);

        // create the table
        TableView<ContentModule> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Module");

        // puts the data from the resultset in an observablelist
        ObservableList<ContentModule> data = genericGUI.getData(resultSet, ContentModule.class);

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
        VBox buttons = new VBox(refreshButton, addModuleButton, editModuleButton, deleteModuleButton, confirmButton,
                backButton);

        VBox showData = new VBox(titleShow, titleShowText, descriptionShow, descriptionShowText);

        buttons.setPrefWidth(200);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons, showData);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        moduleStage.setScene(scene);

        // eventhandler for adding module
        addModuleButton.setOnAction((addModuleEvent) -> {
            try {
                AddModuleGUI addModuleGUI = new AddModuleGUI();
                Stage addModuleStage = new Stage();

                addModuleStage.setTitle("Module toevoegen");
                genericGUI.openPopupScreen(addModuleStage, addModuleGUI);

            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        //eventhandler for editing a module
        editModuleButton.setOnAction((editModuleEvent) -> {
            ContentModule selectedModule = table.getSelectionModel().getSelectedItem();

            if (selectedModule != null) {
                String title = selectedModule.getTitle();
                int version = selectedModule.getVersion();
                String description = selectedModule.getDescription();
                String contactPersonName = selectedModule.getContactPersonName();
                String contactPersonMail = selectedModule.getContactPersonMail();
                Date publicationDate = selectedModule.getPublicationDate();
                Status status = selectedModule.getStatus();
                int indexNumber = selectedModule.getIndexNumber();

                ContentModule contentModule = new ContentModule(title, version, description, contactPersonName, contactPersonMail, publicationDate, status, indexNumber);

                try {
                    EditModuleGUI editModuleGUI = new EditModuleGUI(contentModule);
                    Stage editModuleStage = new Stage();

                    editModuleStage.setTitle("Module bewerken");
                    genericGUI.openPopupScreen(editModuleStage, editModuleGUI);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                
            }
        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    // method for refreshing the table
    private void refreshTable(ObservableList<ContentModule> data, TableView<ContentModule> table,
            GenericGUI<ContentModule> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Module");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, ContentModule.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }

}
