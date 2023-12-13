package GUI;

import Database.databaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class StudentsGUI extends Application {

    @Override
    public void start(Stage studentGUI) throws Exception {
        databaseConnection databaseConnection = new databaseConnection();
        databaseConnection.openConnection();

    }

}
