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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Course;
import objects.ContentModule;

//TODO: 2 tabellen maken. 1 voor courses en 1 voor modules
//TODO: selecteer 1 record in beide tabellen. Als beide geselecteerd zijn een knop indrukken om te koppelen dmv Id's
//TODO: modules die al gekoppeld zijn niet zichtbaar maken in deze tabellen -> Select query op ongekoppelde modules maken
//TODO: Hierna een extra pagina vanaf CoursesGUI maken waarbij je alle gekoppelde modules per Course kan bekijken

public class CoursesModulesGUI extends Application {

    @Override
    public void start(Stage coursesModulesStage) throws Exception {
        // create databaseconnection and genericGUIs for both course and module
        DatabaseConnection databaseConnection = new DatabaseConnection();
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
        int equalWidth = 175;
        addToCourseButton.setPrefWidth(equalWidth);
        backButton.setPrefWidth(equalWidth);

        // columns for course
        TableColumn<Course, Integer> courseIdCol = new TableColumn<>("CourseId");
        TableColumn<Course, String> courseNameCol = new TableColumn<>("Naam cursus");

        courseIdCol.setCellValueFactory(new PropertyValueFactory<>("courseId"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));

        // add columns to a list
        List<TableColumn<Course, ?>> columnsCourse = new ArrayList<>();
        columnsCourse.add(courseIdCol);
        columnsCourse.add(courseNameCol);
        
        //columns for Module
        TableColumn<ContentModule, String> titleCol = new TableColumn<>("Titel");
        TableColumn<ContentModule, String> versionCol = new TableColumn<>("Versie");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));

        //add columns to a list
        List<TableColumn<ContentModule, ?>> columnsModule = new ArrayList<>();
        columnsModule.add(titleCol);
        columnsModule.add(versionCol);

        //create tables
        TableView<Course> tableCourse = genericGUICourse.createTable(columnsCourse);
        TableView<ContentModule> tableModule = genericGUIModule.createTable(columnsModule);

        // get data from database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSetCourse = databaseConnection.executeSQLSelectStatement("SELECT CourseId, CourseName FROM Course");
        ResultSet resultSetModule = databaseConnection.executeSQLSelectStatement("SELECT Title, Version FROM Module");

        // put data in an observablelist to put into the table
        ObservableList<Course> dataCourse = genericGUICourse.getData(resultSetCourse, Course.class);
        tableCourse.setItems(dataCourse);

        ObservableList<ContentModule> dataModule = genericGUIModule.getData(resultSetModule, ContentModule.class);
        tableModule.setItems(dataModule);

        //add buttons to a VBox
        VBox buttons = new VBox(addToCourseButton, backButton);
        buttons.setPrefWidth(200);
        buttons.setPadding(new Insets(10));

        //add selected items to a VBox
        VBox selectedItems = new VBox(selectedCourseShow, selectedCourseShowText, selectedModuleShow, selectedModuleShowText);
        
        //create VBox for center part of the screen
        VBox center = new VBox(buttons, selectedItems);

        //add everything to a HBox
        HBox box = new HBox(tableCourse, center, tableModule);

        Scene scene = new Scene(box);

        coursesModulesStage.setScene(scene);

        // eventhandler for going back to previous page
        backButton.setOnAction((backButtonEvent) -> {
            ChooseContentGUI chooseContentGUI = new ChooseContentGUI();

            Stage chooseContentStage = new Stage();
            chooseContentStage.setTitle("Content");

            genericGUICourse.switchScreen(coursesModulesStage, chooseContentStage, chooseContentGUI);
        });
    }

}
