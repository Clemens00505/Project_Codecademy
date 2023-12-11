package GUI;

import javafx.application.Application;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class CodecademyGUI extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Menu menuItem = new Menu("opties");

        MenuItem Students = new MenuItem("Studenten");
        MenuItem Content = new MenuItem("Content");

        menuItem.getItems().add(Students);
        menuItem.getItems().add(Content);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().add(menuItem);

        stage.show();
        stage.setTitle("menu");
    }
}
