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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Status;
import objects.Student;

public class ModuleGUI extends Application {

    @Override
    public void start(Stage moduleStage) throws Exception {
        moduleStage.show();
        moduleStage.setTitle("Modules");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Module> genericGUI = new GenericGUI<>();

        //create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addModuleButton = new Button("module toevoegen");
        Button editModuleButton = new Button("module bewerken");
        Button deleteModuleButton = new Button("module verwijderen");
        Button confirmButton = new Button("Aanpassing bevestigen");
        Button backButton = new Button("Terug naar hoofdmenu");

        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addModuleButton.setMinWidth(equalWidth);
        editModuleButton.setMinWidth(equalWidth);
        deleteModuleButton.setMinWidth(equalWidth);
        confirmButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        //create columns for the table
        TableColumn<Module, String> titleCol = new TableColumn<>("Titel");
        TableColumn<Module, Integer> versionCol = new TableColumn<>("Versie");
        TableColumn<Module, String> descriptionCol = new TableColumn<>("Beschrijving");
        TableColumn<Module, String> contactPersonNameCol = new TableColumn<>("Naam contactpersoon");
        TableColumn<Module, String> contactPersonMailCol = new TableColumn<>("Mail contactpersoon");
        TableColumn<Module, Date> publicationDateCol = new TableColumn<>("Publicatiedatum");
        TableColumn<Module, Status> statusCol = new TableColumn<>("Status");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        contactPersonNameCol.setCellValueFactory(new PropertyValueFactory<>("contactPersonName"));
        contactPersonMailCol.setCellValueFactory(new PropertyValueFactory<>("contactPersonMail"));
        publicationDateCol.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        // add columns to a list
        List<TableColumn<Module, ?>> columns = new ArrayList<>();
        columns.add(titleCol);
        columns.add(versionCol);
        columns.add(descriptionCol);
        columns.add(contactPersonNameCol);
        columns.add(contactPersonMailCol);
        columns.add(publicationDateCol);
        columns.add(statusCol);

        // create the table
        TableView<Module> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Module");
        databaseConnection.connectionIsOpen();

        // puts the data from the resultset in an observablelist
        ObservableList<Module> data = genericGUI.getData(resultSet, Module.class);

        // displays data in the table
        table.setItems(data);

        //Gives the table and columns a good size on the screen
        table.setPrefWidth(950);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE); 
        });

        // placing everything in the screen
        // put buttons in a vbox
        VBox buttons = new VBox(refreshButton, addModuleButton, editModuleButton, deleteModuleButton, confirmButton, backButton);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
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

        //eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

    }

    // method for refreshing the table
    private void refreshTable(ObservableList<Module> data, TableView<Module> table, GenericGUI<Module> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Student");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, Module.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }

}
