package objects;

import java.sql.Date;
import java.sql.SQLException;
import database.DatabaseConnection;

public class Student {
    private String email;
    private String name;
    private Gender gender;
    private Date dateOfBirth;

    public Student(String email, String name, Gender gender, Date dateOfBirth) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Student() {
        // Used to create table with genericGUI
    }

    // getters
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // method for adding student to database
    public void addStudent(Student student, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        databaseConnection.openConnection();

        // stringbuilder used for adding a student
        insertStmt.append("INSERT INTO Student (Email, Name, Gender, DateOfBirth) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(student.getEmail());
        insertStmt.append("', '");
        insertStmt.append(student.getName());
        insertStmt.append("', '");
        insertStmt.append(student.getGender());
        insertStmt.append("', '");
        insertStmt.append(student.getDateOfBirth().toString());
        insertStmt.append("')");

        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    // method for deleting student from database
    public void deleteStudent(String email, DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();

        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM STUDENT WHERE Email LIKE '");
        deleteStmt.append(email);
        deleteStmt.append("'");

        databaseConnection.executeSQLInsertUpdateDeleteStatement(deleteStmt.toString());
        databaseConnection.closeConnection();
    }

    // method to update student information in database
    public void updateStudent(String oldEmail, Student student, DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Student SET ");
        updateStmt.append("Email = '" + student.getEmail() + "', ");
        updateStmt.append("Name = '" + student.getName() + "' ");
        updateStmt.append("WHERE Email = '" + oldEmail + "'");

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }
}
