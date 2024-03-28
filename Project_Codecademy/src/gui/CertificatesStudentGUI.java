package gui;

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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;

public class CertificatesStudentGUI extends Application {
    private String studentMail;

    public CertificatesStudentGUI(String mail) {
        this.studentMail = mail;
    }

    @Override
    public void start(Stage certificatesStudentStage) throws Exception {
        // create a GenericGUI
        GenericGUI<Certificate> genericGUI = new GenericGUI<>();

        certificatesStudentStage.show();
        certificatesStudentStage.setTitle("Certificaten van " + studentMail);

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button backButton = new Button("Terug naar studenten");

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
        String query = "SELECT * FROM Certificate WHERE StudentMail = '" + studentMail + "'";
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);

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

        certificatesStudentStage.setScene(scene);

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Studenten");

            genericGUI.switchScreen(certificatesStudentStage, studentStage, studentGUI);
        });
    }

}
