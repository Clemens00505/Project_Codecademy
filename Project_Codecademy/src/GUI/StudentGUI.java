package GUI;

import Database.databaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

class StudentGUI extends Application {

    @Override
    public void start(Stage studentGUI) throws Exception {
    
        Stage stage;
        studentGUI.setTitle("Studenten");
        Label label = new Label("hatsikidee");
        Scene scene = new Scene(label);

    }

}
