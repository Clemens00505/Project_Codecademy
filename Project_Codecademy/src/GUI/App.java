package GUI;

import javafx.application.Application;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.databaseConnection;


public class App {
    public static void main(String[] args) {
        Application.launch(StudentGUI.class);


    //     try {
    //         databaseConnection databaseConnection = new databaseConnection();
    //         System.out.println("gelukt");
    //         databaseConnection.openConnection();
    //         String query = "SELECT * FROM Student";
            
    //         ResultSet resultSet = databaseConnection.executeSQLSelectStatement(query);
    //         System.out.println(resultSet);

    //         while (resultSet.next()) {
    //             System.out.println("email: " + resultSet.getString(1) + ", name: " + resultSet.getString(2) + 
    //             ", gender: " + resultSet.getString(3) + ", date of birth: " + resultSet.getDate(4));

    //         }

    //     } catch (Exception e) {
    //         System.out.println(e);
    //     }
    }
}
