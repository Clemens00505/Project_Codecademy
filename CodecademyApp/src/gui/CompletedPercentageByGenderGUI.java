package gui;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseConnection;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import objects.Enrollment;

public class CompletedPercentageByGenderGUI extends Application {

    @Override
    public void start(Stage completedByGenderStage) throws Exception {
        DatabaseConnection databaseConnection = new DatabaseConnection();
        GenericGUI<Enrollment> genericGUI = new GenericGUI<>();

        Button backButton = new Button("Terug naar menu");

        completedByGenderStage.show();
        completedByGenderStage.setTitle("Percentage behaalde cursussen per geslacht");

        Label titleLabel = new Label("Percentage behaalde cursussen per geslacht");
        titleLabel.setStyle("-fx-font-weight: bold;");

        Label malePercentageLabel = new Label();
        Label femalePercentageLabel = new Label();
        Label otherPercentageLabel = new Label();


        VBox root = new VBox(10);
        root.getChildren().addAll(titleLabel, malePercentageLabel, femalePercentageLabel, otherPercentageLabel, backButton);

        Scene scene = new Scene(root, 300, 140);
        completedByGenderStage.setScene(scene);
        completedByGenderStage.show();

        fetchAndDisplayData(malePercentageLabel, femalePercentageLabel, otherPercentageLabel, databaseConnection);

        // eventhandler for button to return to menu
        backButton.setOnAction((returnButtonEvent) -> {
            CodecademyGUI codecademyGUI = new CodecademyGUI();

            Stage codecademyStage = new Stage();
            codecademyStage.setTitle("Menu");

            genericGUI.switchScreen(completedByGenderStage, codecademyStage, codecademyGUI);
        });
    }

    //method to get the percentages
    private void fetchAndDisplayData(Label maleLabel, Label femaleLabel, Label otherLabel, DatabaseConnection databaseConnection) {
        if (databaseConnection.openConnection()) {
            try {
                ResultSet resultSet = databaseConnection.executeSQLSelectStatement("SELECT " +
                        " (SELECT COUNT(*) FROM Enrollment WHERE Percentage = 100 AND StudentMail IN (SELECT Email FROM Student WHERE Gender = 'man')) * 1.0 / " +
                        " (SELECT COUNT(*) FROM Enrollment), " +
                        " (SELECT COUNT(*) FROM Enrollment WHERE Percentage = 100 AND StudentMail IN (SELECT Email FROM Student WHERE Gender = 'woman')) * 1.0 / " +
                        " (SELECT COUNT(*) FROM Enrollment), " +
                        " (SELECT COUNT(*) FROM Enrollment WHERE Percentage = 100 AND StudentMail IN (SELECT Email FROM Student WHERE Gender NOT IN ('man', 'woman'))) * 1.0 / " +
                        " (SELECT COUNT(*) FROM Enrollment)");

                if (resultSet != null) {
                    List<Double> percentages = new ArrayList<>();
                    while (resultSet.next()) {
                        percentages.add(resultSet.getDouble(1));
                        percentages.add(resultSet.getDouble(2));
                        percentages.add(resultSet.getDouble(3));
                    }

                    maleLabel.setText("Percentage behaald man: " + (percentages.isEmpty() ? "N/A" : percentages.get(0) * 100 + "%"));
                    femaleLabel.setText("Percentage behaald vrouw: " + (percentages.isEmpty() ? "N/A" : percentages.get(1) * 100 + "%"));
                    otherLabel.setText("Percentage behaald anders: " + (percentages.isEmpty() ? "N/A" : percentages.get(2) * 100 + "%"));

                    // tableView.getItems().addAll(percentages);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                databaseConnection.closeConnection();
            }
        }

    }

}
