package database;

import java.util.ArrayList;
import java.util.List;
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
                // connecting to the server
                String connectionUrl = "jdbc:sqlserver://aei-sql2.avans.nl:1443;databaseName=CodecademyClemens;user=Clemens;password=wachtwoord1;encrypt=false;";
                connection = DriverManager.getConnection(connectionUrl);

                if (connection != null) {
                    statement = connection.createStatement();
                    result = true;
                    System.out.println("verbonden");
                }

            } catch (SQLException e) {
                e.printStackTrace();
                result = false;
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            result = true;
        }

        return result;
    }

    public boolean connectionIsOpen() { // method to check connection to database
        boolean open = false;

        if (connection != null && statement != null) {
            try {
                open = !connection.isClosed() && !statement.isClosed();
            } catch (SQLException e) {
                e.printStackTrace();
                open = false;
            }
        }

        return open;
    }

    public void closeConnection() { // method to close connection to database
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }

        if (resultSet != null) {
            System.out.println("Resultset gemaakt: " + resultSet.toString());
        } else {
            System.out.println("Resultset gemaakt: null");
        }

        return resultSet;
    }

    public void executeSQLStatement(String query) {
        if (query != null && connectionIsOpen()) {
            try {
                statement.execute(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void executeSQLInsertUpdateDeleteStatement(String query) {
        if (query != null && connectionIsOpen()) {
            try {
                statement.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to get all student emails from the database
    public List<String> getAllStudentEmails() throws SQLException {
        List<String> emails = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            String query = "SELECT Email FROM Student";
            resultSet = executeSQLSelectStatement(query);
            while (resultSet.next()) {
                emails.add(resultSet.getString("Email"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return emails;
    }

    // Method to fetch all existing course names from the database
    public List<String> getAllCourseNames() throws SQLException {
        List<String> courseNames = new ArrayList<>();
        ResultSet resultSet = null;
        try {
            String query = "SELECT CourseName FROM Course";
            resultSet = executeSQLSelectStatement(query);
            while (resultSet.next()) {
                courseNames.add(resultSet.getString("CourseName"));
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return courseNames;
    }

    // Method to get a prepared statement
    public PreparedStatement getPreparedStatement(String sqlQuery) throws SQLException {
        return connection.prepareStatement(sqlQuery);
    }

    public PreparedStatement getPreparedStatement(String sqlQuery, boolean returnGeneratedKeys) throws SQLException {
        openConnection();
        PreparedStatement preparedStatement;
    
        if (returnGeneratedKeys) {
            preparedStatement = connection.prepareStatement(sqlQuery, PreparedStatement.RETURN_GENERATED_KEYS);
        } else {
            preparedStatement = connection.prepareStatement(sqlQuery);
        }
    
        return preparedStatement;
    }

    // Method to get the course ID by name
    public int getCourseIdByName(String courseName) throws SQLException {
        int courseId = -1; // Default value if course is not found or an error occurs
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;

        try {
            openConnection(); // Ensure the connection is open before executing the query

            String query = "SELECT CourseId FROM Course WHERE CourseName = ?";
            preparedStatement = getPreparedStatement(query);
            preparedStatement.setString(1, courseName);

            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                courseId = resultSet.getInt("CourseId");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            closeConnection(); // Close the connection after executing the query
        }

        return courseId;
    }

}
