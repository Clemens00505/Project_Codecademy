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
import objects.Course;
import objects.Status;
import objects.ContentModule;

public class CoursesModulesGUI extends Application {

    @Override
    public void start(Stage coursesModulesStage) throws Exception {
        // create databaseconnection and genericGUIs for both course and module
        DatabaseConnection databaseConnectionCourse = new DatabaseConnection();
        DatabaseConnection databaseConnectionModule = new DatabaseConnection();
        GenericGUI<Course> genericGUICourse = new GenericGUI<>();
        GenericGUI<ContentModule> genericGUIModule = new GenericGUI<>();

        coursesModulesStage.show();
        coursesModulesStage.setTitle("Cursussen en modules koppelen");

        Button addToCourseButton = new Button("Module toevoegen aan cursus");
        Button backButton = new Button("Terug naar vorige pagina");

        // create labels and textareas to show selected course and module since table
        // columns
        // are narrow
        Label selectedCourseShow = new Label("Geselecteerde cursus: ");
        Label selectedModuleShow = new Label("Geselecteerde module: ");
        TextArea selectedCourseShowText = new TextArea();
        TextArea selectedModuleShowText = new TextArea();

        selectedCourseShowText.setEditable(false);
        selectedCourseShowText.setWrapText(true);
        selectedModuleShowText.setEditable(false);
        selectedModuleShowText.setWrapText(true);

        // setting equal widt for the buttons
        int equalWidth = 350;
        addToCourseButton.setPrefWidth(equalWidth);
        backButton.setPrefWidth(equalWidth);

        // columns for course
        TableColumn<Course, Integer> courseIdCol = new TableColumn<>("CourseID");
        TableColumn<Course, String> courseNameCol = new TableColumn<>("Naam cursus");
        TableColumn<Course, String> subjectCol = new TableColumn<>("Onderwerp");
        TableColumn<Course, String> introTextCol = new TableColumn<>("Introductietekst");
        TableColumn<Course, String> difficultyCol = new TableColumn<>("Niveau");

        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        subjectCol.setCellValueFactory(new PropertyValueFactory<>("subject"));
        introTextCol.setCellValueFactory(new PropertyValueFactory<>("introText"));
        difficultyCol.setCellValueFactory(new PropertyValueFactory<>("difficulty"));

        // add columns to a list
        List<TableColumn<Course, ?>> columnsCourse = new ArrayList<>();
        columnsCourse.add(courseIdCol);
        columnsCourse.add(courseNameCol);

        // columns for Module
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
        List<TableColumn<ContentModule, ?>> columnsModule = new ArrayList<>();
        columnsModule.add(titleCol);
        columnsModule.add(versionCol);

        versionCol.setPrefWidth(100);

        // create tables
        TableView<Course> tableCourse = genericGUICourse.createTable(columnsCourse);
        TableView<ContentModule> tableModule = genericGUIModule.createTable(columnsModule);

        // get data from database as a resultset
        databaseConnectionCourse.openConnection();
        databaseConnectionModule.openConnection();
        ResultSet resultSetCourse = databaseConnectionCourse.executeSQLSelectStatement("SELECT * FROM Course");
        ResultSet resultSetModule = databaseConnectionModule
                .executeSQLSelectStatement("SELECT * FROM Module WHERE CourseId IS NULL");

        // put data in an observablelist to put into the table
        ObservableList<Course> dataCourse = genericGUICourse.getData(resultSetCourse, Course.class);
        tableCourse.setItems(dataCourse);

        ObservableList<ContentModule> dataModule = genericGUIModule.getData(resultSetModule, ContentModule.class);
        tableModule.setItems(dataModule);

        // Gives the table and columns a good size on the screen
        tableCourse.setPrefWidth(500);
        tableModule.setPrefWidth(500);
        tableCourse.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableModule.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        tableCourse.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE);
        });

        tableModule.getColumns().forEach(column -> {
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE);
        });

        // displays the courseName of the selected course on the right side of the
        // screen
        tableCourse.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedCourseShowText.setText(newSelection.getCourseName());
            } else {
                selectedCourseShowText.setText("");
            }
        });

        // displays the title and version of the selected module on the right side of
        // the screen
        tableModule.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedModuleShowText
                        .setText("Titel module: " + newSelection.getTitle() + "\nVersie: " + newSelection.getVersion());
            } else {
                selectedModuleShowText.setText("");
            }
        });

        // add buttons to a VBox
        VBox buttons = new VBox(addToCourseButton, backButton);
        buttons.setPadding(new Insets(10));

        // add selected items to a VBox
        VBox selectedItems = new VBox(selectedCourseShow, selectedCourseShowText, selectedModuleShow,
                selectedModuleShowText);

        // create VBox for center part of the screen
        VBox center = new VBox(buttons, selectedItems);

        // add everything to a HBox
        HBox box = new HBox(tableCourse, center, tableModule);

        Scene scene = new Scene(box);

        coursesModulesStage.setScene(scene);

        // eventhandler for button for adding a module to a course
        addToCourseButton.setOnAction((addToCourseButtonEvent) -> {

            // get selectedItems
            Course selectedCourse = tableCourse.getSelectionModel().getSelectedItem();
            ContentModule selectedModule = tableModule.getSelectionModel().getSelectedItem();

            if (selectedCourse != null && selectedModule != null) {
                // get data needed to update module to link to correct course
                int courseId = selectedCourse.getCourseId();

                String title = selectedModule.getTitle();
                int version = selectedModule.getVersion();

                StringBuilder updateStmt = new StringBuilder();
                updateStmt.append("UPDATE Module SET ");
                updateStmt.append("CourseId = '" + courseId + "' ");
                updateStmt.append("WHERE Title = '" + title + "' AND Version = '" + version + "'");

                try {
                    databaseConnectionModule.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
                    refreshTable(dataModule, tableModule, genericGUIModule, databaseConnectionModule);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                
            }
        });

        // eventhandler for going back to previous page
        backButton.setOnAction((backButtonEvent) -> {
            ChooseContentGUI chooseContentGUI = new ChooseContentGUI();

            Stage chooseContentStage = new Stage();
            chooseContentStage.setTitle("Content");

            genericGUICourse.switchScreen(coursesModulesStage, chooseContentStage, chooseContentGUI);
        });
    }

    // method for refreshing the table
    private void refreshTable(ObservableList<ContentModule> data, TableView<ContentModule> table,
            GenericGUI<ContentModule> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Module WHERE CourseId IS NULL");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, ContentModule.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }
}
