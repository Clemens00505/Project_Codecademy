package gui;

import java.sql.ResultSet;
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
import objects.Certificate;

public class CertificateGUI extends Application {

    @Override
    public void start(Stage certificateStage) throws Exception {
        // create a GenericGUI
        GenericGUI<Certificate> genericGUI = new GenericGUI<>();

        certificateStage.show();
        certificateStage.setTitle("Certificaten");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button backButton = new Button("Terug naar menu");

        // sets equal width for buttons
        int equalWidth = 175;
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<Certificate, Integer> enrollmentIdCol = new TableColumn<>("Inschrijvings-ID");
        TableColumn<Certificate, String> studentMailCol = new TableColumn<>("Student mail");
        TableColumn<Certificate, Integer> certificateId = new TableColumn<>("Certificaat-ID");

        enrollmentIdCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        studentMailCol.setCellValueFactory(new PropertyValueFactory<>("studentMail"));
        certificateId.setCellValueFactory(new PropertyValueFactory<>("certificateId"));

        // add columns to a list
        List<TableColumn<Certificate, ?>> columns = new ArrayList<>();
        columns.add(certificateId);
        columns.add(studentMailCol);
        columns.add(enrollmentIdCol);

        // create the table
        TableView<Certificate> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Certificate");
        // puts the data from the resultset in an observablelist
        ObservableList<Certificate> data = genericGUI.getData(resultSet, Certificate.class);

        // displays data in the table
        table.setItems(data);

        // Gives the table and columns a good size on the screen
        table.setPrefWidth(950);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        table.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE);
        });

        // placing everything in the screen
        // put buttons in a vbox
        VBox buttons = new VBox(backButton);

        buttons.setPrefWidth(200);
        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        certificateStage.setScene(scene);

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(certificateStage, codecademyStage, codecademyGUI);
        });
    }

}
