package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CodecademyGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("menu");

        Button studentenButton = new Button("Studenten");
        Button contentButton = new Button("Content");
        
        HBox box = new HBox();
        box.getChildren().addAll(studentenButton, contentButton);

        Scene scene = new Scene(box, 100, 200);

        stage.setScene(scene);
        stage.show();

    }
}
