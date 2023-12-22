package database;

import java.util.logging.Level;
import java.util.logging.Logger;
import objects.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DatabaseConnection {

    private Connection connection;
    private Statement statement;

    public DatabaseConnection() {
        connection = null;
        statement = null;
    }

        public boolean openConnection() { //methode die wordt geroepen vanuit de GUI om te verbinden met de database
            boolean result = false;

            if (connection == null) {
                try {
                    // 'Importeer' de driver die je gedownload hebt.
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                // Try to create a connection with the library database
                String connectionUrl = "jdbc:sqlserver://localhost;databaseName=codecademy;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";
                //connection = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=codecademy;integratedSecurity=true;");
                connection = DriverManager.getConnection(connectionUrl);

                if (connection != null) {
                    statement = connection.createStatement();
                }

                result = true;
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

    public boolean connectionIsOpen() { //methode om te controleren of de de verbinding nog aanwezig is
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

    public void closeConnection() { //methode om de verbinding met de database te verbreken
        try {
            statement.close();
            connection.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public ResultSet executeSQLSelectStatement(String query) { //methode om een query te gebruiken
        ResultSet resultSet = null;

        if (query != null && connectionIsOpen()) {
            try {
                resultSet = statement.executeQuery(query);
            } catch (SQLException e) {
            resultSet = null;
            }
        }

        // System.out.println("Resultset gemaakt");
        return resultSet;
    }

    public ObservableList<Student> getAllStudents() throws SQLException, ClassNotFoundException { //methode om alle informatie over studenten uit de database te halen
        ObservableList<Student> students = FXCollections.observableArrayList();

        String selectStatement = "SELECT * FROM Student"; //Statement voor alle informatie over studenten

        try { //select statement uitvoeren
            ResultSet resultSet = executeSQLSelectStatement(selectStatement);

            while (resultSet.next()) {
                Student student = new Student(resultSet.getString("Email"), resultSet.getString("Name"), resultSet.getString("Gender"), resultSet.getDate("DateOfBirth"));
                students.add(student);
            }
        } catch (SQLException e) {
            System.out.println("SQL select query was niet succesvol: " + e);
            //laat de exception zien
            throw e;
        }

        return students;
    }

    public void addStudent(Student student) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        insertStmt.append("INSERT INTO Student (Email, Name, Gender, DateOfBirth) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(student.getEmail());
        insertStmt.append("', '");
        insertStmt.append(student.getName());
        insertStmt.append("', '");
        insertStmt.append(student.getGender());
        insertStmt.append("', '");
        insertStmt.append(student.getDateOfBirth());
        insertStmt.append("')");

        System.out.println(insertStmt.toString());

        executeSQLSelectStatement(insertStmt.toString());
    }

    public void deleteStudent(String email) throws SQLException {
        
        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM STUDENT WHERE Email LIKE '");
        deleteStmt.append(email);
        deleteStmt.append("'");


        executeSQLSelectStatement(deleteStmt.toString());
        System.out.println(deleteStmt);
    }

    public void updateStudent(Student student) throws SQLException {
        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Student SET ");
        updateStmt.append("Email = '" + student.getEmail());
        updateStmt.append("', Name = '" + student.getName());
        updateStmt.append("', Gender = '" + student.getGender());
        updateStmt.append("', DateOfBirth = '" + student.getDateOfBirth());
        updateStmt.append("' WHERE Email = '" + student.getEmail());
        updateStmt.append("'");

        System.out.println(updateStmt);

        executeSQLSelectStatement(updateStmt.toString());
    }
}




