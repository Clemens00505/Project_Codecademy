package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Student;

public class ChooseContentGUI extends Application {
    @Override
    public void start(Stage chooseContentGUI) throws Exception {
        // Create a GenericGUI
        GenericGUI<Student> genericGUI = new GenericGUI<>();

        chooseContentGUI.setTitle("Content");

        Label text = new Label("Welke content wil je bekijken?");

        //buttons for navigating
        Button coursesButton = new Button("Cursussen");
        Button modulesButton = new Button("Modules");
        Button webcastsButton = new Button("Webcasts");
        Button coursesModulesButton = new Button("Cursussen en modules koppelen");
        Button backButton = new Button("Terug");

        //setting equal width for all buttons
        int equalWidth = 200;
        coursesButton.setPrefWidth(equalWidth);
        modulesButton.setPrefWidth(equalWidth);
        webcastsButton.setPrefWidth(equalWidth);
        coursesModulesButton.setPrefWidth(equalWidth);
        backButton.setPrefWidth(equalWidth);

        VBox buttons = new VBox(text, coursesButton, modulesButton, webcastsButton, coursesModulesButton, backButton);

        buttons.setPadding(new Insets(15));

        Scene scene = new Scene(buttons);

        chooseContentGUI.setScene(scene);
        chooseContentGUI.show();

        coursesButton.setOnAction((coursesButtonEvent) -> {
            CourseGUI courseGUI = new CourseGUI();

            Stage courseStage = new Stage();
            courseStage.setTitle("Cursussen");

            genericGUI.switchScreen(chooseContentGUI, courseStage, courseGUI);
        });

        //button to open modules gui
        modulesButton.setOnAction((modulesButtonEvent) -> {
            ModuleGUI modulesGUI = new ModuleGUI();

            Stage moduleStage = new Stage();
            moduleStage.setTitle("Modules");

            genericGUI.switchScreen(chooseContentGUI, moduleStage, modulesGUI);
        });
        
        webcastsButton.setOnAction((webcastButtonEvent) -> {
            ContentWebcastGUI contentWebcastGUI = new ContentWebcastGUI();

            Stage contentWebcastStage = new Stage();
            contentWebcastStage.setTitle("Webcasts");

            genericGUI.switchScreen(chooseContentGUI, contentWebcastStage, contentWebcastGUI);
        });
        
        // eventhandler for button to return to main menu
        backButton.setOnAction((backButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(chooseContentGUI, codecademyStage, codecademyGUI);

        });

        //eventhandler for button to go to screen for adding modules to a course
        coursesModulesButton.setOnAction((coursesModulesButtonEvent) -> {
            CoursesModulesGUI coursesModulesGUI = new CoursesModulesGUI();
            
            Stage coursesModulesStage = new Stage();
            coursesModulesStage.setTitle("Cursussen en modules koppelen");

            genericGUI.switchScreen(chooseContentGUI, coursesModulesStage, coursesModulesGUI);
        });

    }
}
