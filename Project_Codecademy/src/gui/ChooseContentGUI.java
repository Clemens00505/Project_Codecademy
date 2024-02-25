package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import objects.Student;

//TODO: Andere knoppen en labels toevoegen
//TODO: buttons op scherm tonen

public class ChooseContentGUI extends Application {
    @Override
    public void start(Stage chooseContentGUI) throws Exception {
        // Create a GenericGUI
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        chooseContentGUI.setTitle("Content");

        Button allContentButton = new Button("Alle content");
        Button webcastButton = new Button("Alle webcasts");
        Button backButton = new Button("Terug");

        Scene scene = new Scene(backButton);

        chooseContentGUI.setScene(scene);
        chooseContentGUI.show();

        allContentButton.setOnAction((allContentButtonEvent) -> {
            AllContentGUI allContentGUI = new AllContentGUI();

            Stage allContentStage = new Stage();
            allContentStage.setTitle("Alle content");

            genericGUI.switchScreen(chooseContentGUI, allContentStage, allContentGUI);
        });
        
        webcastButton.setOnAction((webcastButtonEvent) -> {
            WebcastGUI webcastGUI = new WebcastGUI();

            Stage webcastStage = new Stage();
            webcastStage.setTitle("Webcasts");

            genericGUI.switchScreen(chooseContentGUI, webcastStage, webcastGUI);
        });
        
        // eventhandler for button to return to main menu
        backButton.setOnAction((backButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(chooseContentGUI, codecademyStage, codecademyGUI);

        });

    }
}
