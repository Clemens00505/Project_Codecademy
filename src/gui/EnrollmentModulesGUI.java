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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Certificate;
import objects.Enrollment;
import objects.EnrollmentModules;

public class EnrollmentModulesGUI extends Application{
    private int enrollmentId;

    public EnrollmentModulesGUI(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    @Override
    public void start(Stage enrollmentModulesStage) throws Exception {
        // create a GenericGUI
        GenericGUI<EnrollmentModules> genericGUI = new GenericGUI<>();

        enrollmentModulesStage.show();
        enrollmentModulesStage.setTitle("Modules bij inschrijving");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addEnrollmentButton = new Button("Enrollment toevoegen");
        Button editEnrollmentButton = new Button("Enrollment bewerken");
        Button deleteEnrollmentButton = new Button("Enrollment verwijderen");
        Button createCertificateButton = new Button("Certificaat maken");
        Button backButton = new Button("Terug naar menu");

        // sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addEnrollmentButton.setMinWidth(equalWidth);
        editEnrollmentButton.setMinWidth(equalWidth);
        deleteEnrollmentButton.setMinWidth(equalWidth);
        createCertificateButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<EnrollmentModules, Integer> enrollmentIdCol = new TableColumn<>("Inschrijvings-ID");
        TableColumn<EnrollmentModules, Integer> courseIdCol = new TableColumn<>("Cursus-ID");
        TableColumn<EnrollmentModules, Integer> contentIdCol = new TableColumn<>("Content-ID");
        TableColumn<EnrollmentModules, String> titleCol = new TableColumn<>("Titel");
        TableColumn<EnrollmentModules, Integer> versionCol = new TableColumn<>("Versie");
        TableColumn<EnrollmentModules, Integer> progressCol = new TableColumn<>("Voortgang");

        enrollmentIdCol.setCellValueFactory(new PropertyValueFactory<>("enrollmentId"));
        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        contentIdCol.setCellValueFactory(new PropertyValueFactory<>("contentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        progressCol.setCellValueFactory(new PropertyValueFactory<>("progress"));

        // add columns to a list
        List<TableColumn<EnrollmentModules, ?>> columns = new ArrayList<>();
        columns.add(enrollmentIdCol);
        columns.add(courseIdCol);
        columns.add(contentIdCol);
        columns.add(titleCol);
        columns.add(versionCol);
        columns.add(progressCol);

        // create the table
        TableView<EnrollmentModules> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM EnrollmentModules");
        // puts the data from the resultset in an observablelist
        ObservableList<EnrollmentModules> data = genericGUI.getData(resultSet, EnrollmentModules.class);

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
        VBox buttons = new VBox(refreshButton, addEnrollmentButton, editEnrollmentButton, deleteEnrollmentButton, createCertificateButton,
                backButton);

        buttons.setPrefWidth(200);
        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        enrollmentModulesStage.setScene(scene);

        // eventhandler for editing an enrollment
        editEnrollmentButton.setOnAction((editEnrollmentEvent) -> {


        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(enrollmentModulesStage, codecademyStage, codecademyGUI);
        });
    }

    // method for refreshing the table
    private void refreshTable(ObservableList<EnrollmentModules> data, TableView<EnrollmentModules> table,
            GenericGUI<EnrollmentModules> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM EnrollmentModules");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, EnrollmentModules.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }
}
