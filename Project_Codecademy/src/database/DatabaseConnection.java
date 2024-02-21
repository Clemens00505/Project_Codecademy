package database;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection;
    private Statement statement;

    public DatabaseConnection() {
        connection = null;
        statement = null;
    }

    public boolean openConnection() { // method to connect to databse
        boolean result = false;

        if (connection == null) {
            try {
                // Import driver
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                //connecting to the server
                String connectionUrl = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodecademyClemens;user=Clemens;password=wachtwoord1;encrypt=false;";
                connection = DriverManager.getConnection(connectionUrl);

                if (connection != null) {
                    statement = connection.createStatement();
                    result = true;
                    System.out.println("verbonden");
                }

            } catch (SQLException e) {
                System.out.println(e);
                result = false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            result = true;
        }
        // System.out.println("verbinding gemaakt");

        return result;
    }

    public boolean connectionIsOpen() { // method to check connection to database
        boolean open = false;

        if (connection != null && statement != null) {
            try {
                open = !connection.isClosed() && !statement.isClosed();
            } catch (SQLException e) {
                System.out.println(e);
                open = false;
            }
        }

        return open;
    }

    public void closeConnection() { // method to close connection to database
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }

        statement = null;
        connection = null;
    }

    public ResultSet executeSQLSelectStatement(String query) { // method for using a query
        ResultSet resultSet = null;

        if (query != null && connectionIsOpen()) {
            try {
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
                resultSet = null;
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Resultset gemaakt: " + resultSet);
        return resultSet;
    }

    public void executeSQLStatement(String query) {
        if (query != null && connectionIsOpen()) {
            try {
                statement.executeQuery(query);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

}
