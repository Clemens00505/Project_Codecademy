package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class EnrollmentGUI extends Application {
 @Override
    public void start(Stage enrollmentGUI) throws Exception {
        enrollmentGUI.setTitle("Inschrijvingen");

        Button backButton = new Button("Terug");

        Scene scene = new Scene(backButton);

        enrollmentGUI.setScene(scene);
        enrollmentGUI.show();


        //eventhandler for button to return to main menu
        backButton.setOnAction((event) -> {
            StudentGUI studentGUI = new StudentGUI();

            Stage studentStage = new Stage();
            studentStage.setTitle("Menu");

            try {
                studentGUI.start(studentStage);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //close current stage
            enrollmentGUI.close();
        });
    
    }
}
