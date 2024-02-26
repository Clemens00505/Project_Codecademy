package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class CourseGUI extends Application {

    @Override
    public void start(Stage arg0) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI genericGUI = new GenericGUI<>();
    }

}
