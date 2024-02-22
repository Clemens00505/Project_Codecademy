package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import objects.Student;

public class ContentGUI extends Application {
    @Override
    public void start(Stage contentGUI) throws Exception {
        // Create a GenericGUI
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        contentGUI.setTitle("Content");

        Button allContentButton = new Button("Alle content");
        Button webcastButton = new Button("Alle webcasts");
        Button backButton = new Button("Terug");

        Scene scene = new Scene(backButton);

        contentGUI.setScene(scene);
        contentGUI.show();

        // eventhandler for button to return to main menu
        backButton.setOnAction((event) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(contentGUI, codecademyStage, codecademyGUI);

            try {
                codecademyGUI.start(codecademyStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // close current stage
            contentGUI.close();
        });

    }
}
