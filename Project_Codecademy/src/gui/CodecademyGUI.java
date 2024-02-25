package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Student;

public class CodecademyGUI extends Application {
    

    @Override
    public void start(Stage codecademyGUI) throws Exception {
        // Create a GenericGUI for the generic methods
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        codecademyGUI.setTitle("Menu");

        Button studentenButton = new Button("Studenten");
        Button contentButton = new Button("Content");
        
        VBox box = new VBox();
        box.getChildren().addAll(studentenButton, contentButton);

        Scene scene = new Scene(box, 100, 200);

        codecademyGUI.setScene(scene);
        codecademyGUI.show();


        //Buttons that open different screens
        studentenButton.setOnAction((event) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Studenten");

            genericGUI.switchScreen(codecademyGUI, studentStage, studentGUI);
        });

        contentButton.setOnAction((event) -> {
            ChooseContentGUI contentGUI = new ChooseContentGUI();

            Stage contentStage = new Stage();
            contentStage.setTitle("Content");

            genericGUI.switchScreen(codecademyGUI, contentStage, contentGUI);
        });
    

    }
}
