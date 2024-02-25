package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import objects.Status;
import objects.Student;

public class ModuleGUI extends Application {

    @Override
    public void start(Stage modulesStage) throws Exception {
        modulesStage.show();
        modulesStage.setTitle("Modules");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Module> genericGUI = new GenericGUI<>();

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

        table.setPrefWidth(700);

        Scene scene = new Scene(table);

        modulesStage.setScene(scene);


    }

}
