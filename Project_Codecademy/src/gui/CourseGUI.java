package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import objects.Course;

public class CourseGUI extends Application {

    @Override
    public void start(Stage courseStage) throws Exception {
        //create databaseconnection and genericGUI
        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Course> genericGUI = new GenericGUI<>();

        courseStage.show();
        courseStage.setTitle("Cursussen");

        //create buttons
        Button refreshButton = new Button("Tabel verversen");
        Button addCourseButton = new Button("Cursus toevoegen");
        Button editCourseButton = new Button("Cursus bewerken");
        Button deleteCourseButton = new Button("Cursus verwijderen");
        Button backButton = new Button("Terug naar hoofdmenu");

        // create labels and textareas to show courseName and IntroText since table columns
        // are narrow
        Label courseNameShow = new Label("Titel: ");
        Label introTextShow = new Label("Beschrijving: ");
        TextArea courseNameShowText = new TextArea();
        TextArea introTextShowText = new TextArea();

        courseNameShowText.setEditable(false);
        courseNameShowText.setWrapText(true);
        introTextShowText.setEditable(false);
        introTextShowText.setWrapText(true);

        //sets equal width for buttons
        int equalWidth = 175;
        refreshButton.setMinWidth(equalWidth);
        addCourseButton.setMinWidth(equalWidth);
        editCourseButton.setMinWidth(equalWidth);
        deleteCourseButton.setMinWidth(equalWidth);
        backButton.setMinWidth(equalWidth);

        TableColumn<Course, String> courseNameCol = new TableColumn<>("Naam cursus");
    }

}
