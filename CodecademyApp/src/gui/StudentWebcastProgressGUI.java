package gui;

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
import objects.Status;
import objects.Student;
import objects.StudentWebcast;

public class StudentWebcastProgressGUI extends Application {
    private Student student;

    public StudentWebcastProgressGUI(Student student) {
        this.student = student;
    }

    public void start(Stage studentWebcastProgressStage) throws Exception {
        // create a GenericGU
        GenericGUI<StudentWebcast> genericGUI = new GenericGUI<>();

        studentWebcastProgressStage.show();
        studentWebcastProgressStage.setTitle("Voortgang webcasts van " + student.getEmail());

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addWebcastToStudentButton = new Button("Webcast toevoegen");
        Button editWebcastButton = new Button("Voortgang bewerken");
        Button backButton = new Button("Terug naar studenten");

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

        // sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addWebcastToStudentButton.setMinWidth(equalWidth);
        editWebcastButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<StudentWebcast, String> emailCol = new TableColumn<>("Email");
        TableColumn<StudentWebcast, String> titleCol = new TableColumn<>("Titel");
        TableColumn<StudentWebcast, String> descriptionCol = new TableColumn<>("Beschrijving");
        TableColumn<StudentWebcast, Integer> progressCol = new TableColumn<>("Voortgang");
        TableColumn<StudentWebcast, Status> webcastIdCol = new TableColumn<>("WebcastId");
        TableColumn<StudentWebcast, String> studentWebcastIdCol = new TableColumn<>("StudentWebcastId");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        progressCol.setCellValueFactory(new PropertyValueFactory<>("progressPercentage"));
        webcastIdCol.setCellValueFactory(new PropertyValueFactory<>("contentId"));
        studentWebcastIdCol.setCellValueFactory(new PropertyValueFactory<>("studentWebcastProgressid"));


        // add columns to a list
        List<TableColumn<StudentWebcast, ?>> columns = new ArrayList<>();
        columns.add(emailCol);
        columns.add(titleCol);
        columns.add(descriptionCol);
        columns.add(progressCol);
        columns.add(webcastIdCol);
        columns.add(studentWebcastIdCol);

        // create the table
        TableView<StudentWebcast> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(
                "SELECT Webcast.Title, Webcast.Description, Webcast.ContentId, StudentWebcastProgress.ProgressPercentage, StudentWebcastProgress.StudentWebcastProgressId, Student.Email \n"
                        + //
                        "FROM Webcast \n" + //
                        "JOIN StudentWebcastProgress on Webcast.ContentId = StudentWebcastProgress.WebcastContentId\n" + //
                        "JOIN Student on StudentWebcastProgress.StudentMail = Student.Email\n" + //
                        "WHERE Student.Email = '" + student.getEmail() + "';");


        // puts the data from the resultset in an observablelist
        ObservableList<StudentWebcast> data = genericGUI.getData(resultSet, StudentWebcast.class);

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
        VBox buttons = new VBox(refreshButton, addWebcastToStudentButton, editWebcastButton, backButton);

        VBox showData = new VBox(titleShow, titleShowText, descriptionShow, descriptionShowText);

        buttons.setPrefWidth(200);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons, showData);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        studentWebcastProgressStage.setScene(scene);

        addWebcastToStudentButton.setOnAction((addWebcastToStudentButtonEvent) -> {
            AddWebcastToStudentGUI addWebcastToStudentGUI = new AddWebcastToStudentGUI(student);
            Stage addWebcastToStudentStage = new Stage();
            addWebcastToStudentStage.setTitle("Webcast toevoegen aan student");

            genericGUI.openPopupScreen(addWebcastToStudentStage, addWebcastToStudentGUI);
        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // eventhandler for editing a webcast
        editWebcastButton.setOnAction((editWebcastEvent) -> {
            StudentWebcast webcast = table.getSelectionModel().getSelectedItem();

            try {
                EditWebcastProgressGUI editWebcastProgressGUI = new EditWebcastProgressGUI(webcast);
                Stage editWebcastProgressStage = new Stage();
                editWebcastProgressStage.setTitle("Voortgang aanpassen");

                genericGUI.openPopupScreen(studentWebcastProgressStage, editWebcastProgressGUI);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        backButton.setOnAction((backButtonEvent) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Studenten");

            genericGUI.switchScreen(studentWebcastProgressStage, studentStage, studentGUI);
        });

    }

    // method for refreshing the table
    private void refreshTable(ObservableList<StudentWebcast> data, TableView<StudentWebcast> table,
            GenericGUI<StudentWebcast> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(
                "SELECT Webcast.Title, Webcast.Description, Webcast.ContentId, StudentWebcastProgress.ProgressPercentage, StudentWebcastProgress.StudentWebcastProgressId, Student.Email \n"
                        + //
                        "FROM Webcast \n" + //
                        "JOIN StudentWebcastProgress on Webcast.ContentId = StudentWebcastProgress.WebcastContentId\n" + //
                        "JOIN Student on StudentWebcastProgress.StudentMail = Student.Email\n" + //
                        "WHERE Student.Email = '" + student.getEmail() + "';");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, StudentWebcast.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }

}
