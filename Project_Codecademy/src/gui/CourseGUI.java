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
import objects.Course;
import objects.Difficulty;

public class CourseGUI extends Application {

    @Override
    public void start(Stage courseStage) throws Exception {
        // create databaseconnection and genericGUI
        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Course> genericGUI = new GenericGUI<>();

        courseStage.show();
        courseStage.setTitle("Cursussen");

        // create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addCourseButton = new Button("Cursus toevoegen");
        Button editCourseButton = new Button("Cursus bewerken");
        Button deleteCourseButton = new Button("Cursus verwijderen");
        Button backButton = new Button("Terug naar hoofdmenu");

        // create labels and textareas to show courseName and IntroText since table
        // columns
        // are narrow
        Label courseNameShow = new Label("Cursusnaa: ");
        Label introTextShow = new Label("Introductietekst: ");
        TextArea courseNameShowText = new TextArea();
        TextArea introTextShowText = new TextArea();

        courseNameShowText.setEditable(false);
        courseNameShowText.setWrapText(true);
        introTextShowText.setEditable(false);
        introTextShowText.setWrapText(true);

        // sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addCourseButton.setMinWidth(equalWidth);
        editCourseButton.setMinWidth(equalWidth);
        deleteCourseButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        // create columns for the table
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

        // add the columns to a list
        List<TableColumn<Course, ?>> columns = new ArrayList<>();
        columns.add(courseNameCol);
        columns.add(subjectCol);
        columns.add(introTextCol);
        columns.add(difficultyCol);

        // create table
        TableView<Course> table = genericGUI.createTable(columns);

        // get data from database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Course");

        // put data in an observablelist to put into the table
        ObservableList<Course> data = genericGUI.getData(resultSet, Course.class);
        table.setItems(data);

        // displays the courseName and introText on the right side of the screen
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                courseNameShowText.setText(newSelection.getCourseName());
                introTextShowText.setText(newSelection.getIntroText());
            } else {
                courseNameShowText.setText("");
                introTextShowText.setText("");
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
        VBox buttons = new VBox(refreshButton, addCourseButton, editCourseButton, deleteCourseButton,
                backButton);

        VBox showData = new VBox(courseNameShow, courseNameShowText, introTextShow, introTextShowText);

        buttons.setPrefWidth(200);

        buttons.setPadding(new Insets(10));

        VBox rightSide = new VBox(buttons, showData);
        rightSide.setPrefWidth(200);
        HBox box = new HBox(table, rightSide);

        Scene scene = new Scene(box);

        courseStage.setScene(scene);

        // eventhandler for going back to previous page
        backButton.setOnAction((backButtonEvent) -> {
            ChooseContentGUI chooseContentGUI = new ChooseContentGUI();

            Stage chooseContentStage = new Stage();
            chooseContentStage.setTitle("Content");

            genericGUI.switchScreen(courseStage, chooseContentStage, chooseContentGUI);
        });

        // eventhandler for adding module
        addCourseButton.setOnAction((addCourseEvent) -> {
            try {
                AddCourseGUI addCourseGUI = new AddCourseGUI();
                Stage addCourseStage = new Stage();

                addCourseStage.setTitle("Cursus toevoegen");
                genericGUI.openPopupScreen(addCourseStage, addCourseGUI);

            } catch (Exception e) {
                e.printStackTrace();

            }
        });

        // eventhandler for refreshing table
        refreshButton.setOnAction((refreshButtonEvent) -> {
            try {
                refreshTable(data, table, genericGUI, databaseConnection);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // eventhandler for editing a course
        editCourseButton.setOnAction((editCourseEvent) -> {
            Course selectedCourse = table.getSelectionModel().getSelectedItem();

            if (selectedCourse != null) {
                int courseId = selectedCourse.getCourseId();
                String courseName = selectedCourse.getCourseName();
                String subject = selectedCourse.getSubject();
                String introText = selectedCourse.getIntroText();
                Difficulty difficulty = selectedCourse.getDifficulty();

                Course course = new Course(courseId, courseName, subject, introText, difficulty);

                try {
                    EditCourseGUI editCourseGUI = new EditCourseGUI(course);
                    Stage editCourseStage = new Stage();

                    editCourseStage.setTitle("Module bewerken");
                    genericGUI.openPopupScreen(editCourseStage, editCourseGUI);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        // eventhandler for deleting course
        deleteCourseButton.setOnAction((deleteCourseEvent) -> {
            Course selectedCourse = table.getSelectionModel().getSelectedItem();

            if (selectedCourse != null) {
                try {
                    int courseId = selectedCourse.getCourseId();
                    String courseName = selectedCourse.getCourseName();
                    String subject = selectedCourse.getSubject();
                    String introText = selectedCourse.getIntroText();
                    Difficulty difficulty = selectedCourse.getDifficulty();

                    Course course = new Course(courseId, courseName, subject, introText, difficulty);

                    course.deleteCourse(course, databaseConnection);
                    refreshTable(data, table, genericGUI, databaseConnection);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    // method for refreshing the table
    private void refreshTable(ObservableList<Course> data, TableView<Course> table,
            GenericGUI<Course> genericGUI,
            DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT * FROM Course");
        databaseConnection.connectionIsOpen();

        data = genericGUI.getData(resultSet, Course.class);

        // displays new data in the table
        table.setItems(data);
        table.refresh();
    }

}
