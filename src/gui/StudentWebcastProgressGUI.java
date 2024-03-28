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
import objects.Status;
import objects.Student;
import objects.Webcast;

public class StudentWebcastProgressGUI extends Application {
    private Student student;
    
    public StudentWebcastProgressGUI(Student student) {
        this.student = student;
    }

    public void start(Stage studentWebcastProgressStage) throws Exception {
        // create a GenericGU
        GenericGUI<Webcast> genericGUI = new GenericGUI<>();

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

        //sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addWebcastToStudentButton.setMinWidth(equalWidth);
        editWebcastButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<Webcast, String> emailCol = new TableColumn<>("Email");
        TableColumn<Webcast, String> titleCol = new TableColumn<>("Titel");
        TableColumn<Webcast, String> descriptionCol = new TableColumn<>("Beschrijving");
        TableColumn<Webcast, Integer> progressCol = new TableColumn<>("Voortgang");
        TableColumn<Webcast, Status> webcastIdCol = new TableColumn<>("WebcastId");
        TableColumn<Webcast, String> studentWebcastIdCol = new TableColumn<>("StudentWebcastId");

        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));;
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        progressCol.setCellValueFactory(new PropertyValueFactory<>("progressPercentage"));
        webcastIdCol.setCellValueFactory(new PropertyValueFactory<>("webcastContentId"));
        studentWebcastIdCol.setCellValueFactory(new PropertyValueFactory<>("studentWebcastProgressId"));

        // add columns to a list
        List<TableColumn<Webcast, ?>> columns = new ArrayList<>();
        columns.add(emailCol);
        columns.add(titleCol);
        columns.add(descriptionCol);
        columns.add(progressCol);
        columns.add(webcastIdCol);
        columns.add(studentWebcastIdCol);

        // create the table
        TableView<Webcast> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT Webcast.Title, Webcast.Description, StudentWebcastProgress.ProgressPercentage, StudentWebcastProgress.StudentWebcastProgressId, Student.Email, StudentWebcastProgress.WebcastContentId\n" + //
                        "FROM Webcast \n" + //
                        "JOIN StudentWebcastProgress on Webcast.ContentId = StudentWebcastProgress.WebcastContentId\n" + //
                        "JOIN Student on StudentWebcastProgress.StudentMail = Student.Email\n" + //
                        "WHERE Student.Email = '" + student.getEmail() + "';");

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
            Webcast selectedWebcast = table.getSelectionModel().getSelectedItem();

            if (selectedWebcast != null) {
                int contentId = selectedWebcast.getContentId();
                String title = selectedWebcast.getTitle();
                String description = selectedWebcast.getDescription();
                String speakerName = selectedWebcast.getSpeakerName();
                String speakerOrganisation = selectedWebcast.getSpeakerOrganisation();
                Date publicationDate = selectedWebcast.getPublicationDate();
                Status status = selectedWebcast.getStatus();
                String URL = selectedWebcast.getURL();
                int timesViewed = selectedWebcast.getTimesViewed();

                Webcast webcast = new Webcast(contentId, title, description, speakerName, speakerOrganisation,
                        publicationDate, status, URL, timesViewed);

                try {
                    EditWebcastGUI editWebcastGUI = new EditWebcastGUI(webcast);
                    Stage editWebcastStage = new Stage();

                    editWebcastStage.setTitle("Webcast bewerken");
                    genericGUI.openPopupScreen(editWebcastStage, editWebcastGUI);

                } catch (Exception e) {
                    e.printStackTrace();
                }

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
    private void refreshTable(ObservableList<Webcast> data, TableView<Webcast> table,
            GenericGUI<Webcast> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Webcast");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, Webcast.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }


}

