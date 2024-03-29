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
import objects.AverageCompletionModules;
import objects.Course;

public class AverageCompletionModulesGUI extends Application {
    private Course course;

    public AverageCompletionModulesGUI(Course course) {
        this.course = course;
    }

    @Override
    public void start(Stage averageCompletionModulesStage) throws Exception {
        // create a GenericGU
        GenericGUI<AverageCompletionModules> genericGUI = new GenericGUI<>();

        averageCompletionModulesStage.show();
        averageCompletionModulesStage.setTitle("Gemiddelde voortgang modules");

        DatabaseConnection databaseConnection = new DatabaseConnection();

        // create buttons
        Button backButton = new Button("Terug naar cursussen");

        // sets equal width for buttons
        int equalWidth = 175;
        backButton.setMinWidth(equalWidth);

        // create columns for the table
        TableColumn<AverageCompletionModules, String> titleCol = new TableColumn<>("Titel");
        TableColumn<AverageCompletionModules, Integer> versionCol = new TableColumn<>("Versie");
        TableColumn<AverageCompletionModules, Double> avgProgressCol = new TableColumn<>("Gemiddelde voortgang");

        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        versionCol.setCellValueFactory(new PropertyValueFactory<>("version"));
        avgProgressCol.setCellValueFactory(new PropertyValueFactory<>("averageProgress"));

        // add columns to a list
        List<TableColumn<AverageCompletionModules, ?>> columns = new ArrayList<>();
        columns.add(titleCol);
        columns.add(versionCol);
        columns.add(avgProgressCol);

        // create the table
        TableView<AverageCompletionModules> table = genericGUI.createTable(columns);

        // gets the data from the database as a resultset
        databaseConnection.openConnection();
        ResultSet resultSet = databaseConnection.executeSQLSelectStatement(
                "SELECT Module.Title, Module.Version, EnrollmentModules.AverageProgress " +
                        "FROM Module " +
                        "JOIN ( " +
                        "    SELECT ContentId, AVG(CAST(Progress AS FLOAT)) AS AverageProgress " +
                        "    FROM EnrollmentModules " +
                        "    WHERE CourseId = " + course.getCourseId() +
                        "    GROUP BY ContentId " +
                        ") AS EnrollmentModules ON Module.ContentId = EnrollmentModules.ContentId " +
                        "WHERE Module.CourseId = " + course.getCourseId() + ";");

        // puts the data from the resultset in an observablelist
        ObservableList<AverageCompletionModules> data = genericGUI.getData(resultSet, AverageCompletionModules.class);

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

        averageCompletionModulesStage.setScene(scene);

        backButton.setOnAction((backButtonEvent) -> {
            CourseGUI courseGUI = new CourseGUI();

            Stage courseStage = new Stage();
            courseStage.setTitle("Cursussen");
            ;

            genericGUI.switchScreen(averageCompletionModulesStage, courseStage, courseGUI);
        });

    }

}
