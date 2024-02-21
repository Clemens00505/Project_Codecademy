package objects;

import java.sql.Date;
import java.sql.SQLException;
import database.DatabaseConnection;

public class Student {
    private String email;
    private String name;
    private String gender;
    private Date dateOfBirth;

    public Student(String email, String name, String gender, Date dateOfBirth) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Student() {
        
    }

    // public Student(String email, String name, String gender, Date dateOfBirth) {
    //     this.email = email;
    //     this.name = name;
    //     this.gender = gender
    //     this.dateOfBirth = dateOfBirth;
    // }

    //getters 
    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

     public String getGender() {
        return gender.toString();
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    // Zet een String om in Gender Enum zodat het in de gui class werkt
    public static Gender stringToGender(String string) {
        Gender toGender = Gender.valueOf(string);
        return toGender;
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
