package gui;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Enrollment;
import objects.Status;

public class EnrollmentGUI extends Application {

    @Override
    public void start(Stage enrollmentStage) throws Exception {
        // create a GenericGUI
        GenericGUI<Enrollment> genericGUI = new GenericGUI<>();

        enrollmentStage.show();
        enrollmentStage.setTitle("Enrollments");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addEnrollmentButton = new Button("Enrollment toevoegen");
        Button editEnrollmentButton = new Button("Enrollment bewerken");
        Button deleteEnrollmentButton = new Button("Enrollment verwijderen");
        Button backButton = new Button("Terug naar menu");

        // sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addEnrollmentButton.setMinWidth(equalWidth);
        editEnrollmentButton.setMinWidth(equalWidth);
        deleteEnrollmentButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<Enrollment, String> studentMailCol = new TableColumn<>("Student E-mail");
        TableColumn<Enrollment, String> courseNameCol = new TableColumn<>("Cursusnaam");
        TableColumn<Enrollment, Integer> percentageCol = new TableColumn<>("Percentage");
        TableColumn<Enrollment, Date> enrollmentDateCol = new TableColumn<>("Inschrijfdatum");
        TableColumn<Enrollment, Integer> enrollmentIdCol = new TableColumn<>("Inschrijvings-ID");

        studentMailCol.setCellValueFactory(new PropertyValueFactory<>("studentMail"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        percentageCol.setCellValueFactory(new PropertyValueFactory<>("percentage"));
        enrollmentDateCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentDate"));
        enrollmentIdCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));

        // add columns to a list
        List<TableColumn<Enrollment, ?>> columns = new ArrayList<>();
        columns.add(studentMailCol);
        columns.add(courseNameCol);
        columns.add(percentageCol);
        columns.add(enrollmentDateCol);

        // create the table
        TableView<Enrollment> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Enrollment");

        // puts the data from the resultset in an observablelist
        ObservableList<Enrollment> data = genericGUI.getData(resultSet, Enrollment.class);

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
        VBox buttons = new VBox(refreshButton, addEnrollmentButton, editEnrollmentButton, deleteEnrollmentButton,
                backButton);


        buttons.setPrefWidth(200);
        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        enrollmentStage.setScene(scene);

        // eventhandler for adding enrollment
        // addEnrollmentButton.setOnAction((addEnrollmentEvent) -> {
        //     // try {
        //     //     if (selectedStudentMail != null) {
        //     //         AddEnrollmentGUI addEnrollmentGUI = new AddEnrollmentGUI(selectedStudentMail);
        //     //         Stage addEnrollmentStage = new Stage();

        //     //         addEnrollmentStage.setTitle("Enrollment toevoegen");
        //     //         genericGUI.openPopupScreen(addEnrollmentStage, addEnrollmentGUI);
        //     //     } else {
        //         }
        //     } catch (Exception e) {
        //         e.printStackTrace();
        //     }
        // });

        // eventhandler for editing an enrollment
        // editEnrollmentButton.setOnAction((editEnrollmentEvent) -> {
            // Implement editing logic here
        // });

        // eventhandler for deleting an enrollment
        deleteEnrollmentButton.setOnAction((deleteEnrollmentEvent) -> {
            // Implement deletion logic here
        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        backButton.setOnAction((backButtonEvent) -> {
            // Implement back button functionality here
        });
    }

    // method for refreshing the table
    private void refreshTable(ObservableList<Enrollment> data, TableView<Enrollment> table,
                                  GenericGUI<Enrollment> genericGUI,
                                  DatabaseConnection databaseConnection) throws SQLException {
            databaseConnection.openConnection();
            ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Enrollment");
            databaseConnection.connectionIsOpen();
        
            data = genericGUI.getData(resultSet, Enrollment.class);
        
            // displays new data in the table
            table.setItems(data);
            table.refresh();
        }
}
