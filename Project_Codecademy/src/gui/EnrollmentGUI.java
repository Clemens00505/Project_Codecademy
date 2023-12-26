package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Student;

public class EnrollmentGUI extends Application {
    private Student student;
    
    public EnrollmentGUI(Student student) {
        this.student = student;
    }

    @Override
    public void start(Stage enrollmentGUI) throws Exception {
        enrollmentGUI.setTitle("Inschrijvingen");

        Button backButton = new Button("Terug");
        Label testLabel = new Label(student.toString());

        

        // Scene scene = new Scene();

        // enrollmentGUI.setScene(scene);
        // enrollmentGUI.show();



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
