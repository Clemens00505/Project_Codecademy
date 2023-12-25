package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CodecademyGUI extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Menu");

        Button studentenButton = new Button("Studenten");
        Button contentButton = new Button("Content");
        
        VBox box = new VBox();
        box.getChildren().addAll(studentenButton, contentButton);

        Scene scene = new Scene(box, 100, 200);

        stage.setScene(scene);
        stage.show();


        //Buttons that open different screens
        studentenButton.setOnAction((event) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Studenten");

            try {
                studentGUI.start(studentStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //close current stage
            stage.close();
        });

        contentButton.setOnAction((event) -> {
            ContentGUI contentGUI = new ContentGUI();

            Stage contentStage = new Stage();
            contentStage.setTitle("Content");

            try {
                contentGUI.start(contentStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            stage.close();
        });
    

    }
}
