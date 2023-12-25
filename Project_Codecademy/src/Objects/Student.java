package objects;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import database.DatabaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Student {
    private String email;
    private String name;
    private String gender;
    private Date dateOfBirth;

    public Student(String Email, String Name, String Gender, Date DateOfBirth) {
        this.email = Email;
        this.name = Name;
        this.gender = Gender;
        this.dateOfBirth = DateOfBirth;
    }

    //getters en setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

     public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        dateOfBirth = dateOfBirth;
    }

    public void addStudent(Student student, DatabaseConnection databaseConnection) throws SQLException { //method for adding student to database
        StringBuilder insertStmt = new StringBuilder();
        
        databaseConnection.openConnection();

        //stringbuilder used for adding a student
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

        databaseConnection.executeSQLStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    //method for deleting student from database
    public void deleteStudent(String email, DatabaseConnection databaseConnection) throws SQLException { 
        databaseConnection.openConnection();

        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM STUDENT WHERE Email LIKE '");
        deleteStmt.append(email);
        deleteStmt.append("'");


        databaseConnection.executeSQLStatement(deleteStmt.toString());
        System.out.println(deleteStmt);
        databaseConnection.closeConnection();
    }

    //method to update student information in database
    public void updateStudent(Student student, DatabaseConnection databaseConnection) throws SQLException { 
        databaseConnection.openConnection();
        
        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Student SET ");
        updateStmt.append("Email = '" + student.getEmail());
        updateStmt.append("', Name = '" + student.getName());
        updateStmt.append("', Gender = '" + student.getGender());
        updateStmt.append("', DateOfBirth = '" + student.getDateOfBirth());
        updateStmt.append("' WHERE Email = '" + student.getEmail());
        updateStmt.append("'");

        System.out.println(updateStmt);

        databaseConnection.executeSQLStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }
}
