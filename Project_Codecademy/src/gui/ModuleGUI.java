package gui;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.stage.Stage;

public class ModuleGUI extends Application {

    @Override
    public void start(Stage modulesStage) throws Exception {
        modulesStage.show();
        modulesStage.setTitle("Modules");

        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Module> genericGUI = new GenericGUI<>();
    }

}
