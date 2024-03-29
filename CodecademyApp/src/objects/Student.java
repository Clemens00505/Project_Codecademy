package objects;

import java.sql.Date;
import java.sql.SQLException;
import database.DatabaseConnection;

public class Student {
    private String email;
    private String name;
    private Gender gender;
    private Date dateOfBirth;
    private String postalCode;
    private int houseNumber;
    private String city;
    private String country;

    public Student(String email, String name, Gender gender, Date dateOfBirth, String postalCode, int houseNumber,
            String city, String country) {
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.postalCode =  postalCode;
        this.houseNumber = houseNumber;
        this.city = city;
        this.country = country;
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

    public String getPostalCode() {
        return postalCode;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    // method for adding student to database
    public void addStudent(Student student, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        databaseConnection.openConnection();

        // stringbuilder used for adding a student
        insertStmt.append("INSERT INTO Student (Email, Name, Gender, DateOfBirth, PostalCode, HouseNumber, City, Country) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(student.getEmail());
        insertStmt.append("', '");
        insertStmt.append(student.getName());
        insertStmt.append("', '");
        insertStmt.append(student.getGender());
        insertStmt.append("', '");
        insertStmt.append(student.getDateOfBirth().toString());
        insertStmt.append("', '");
        insertStmt.append(student.getPostalCode());
        insertStmt.append("', ");
        insertStmt.append(student.getHouseNumber());
        insertStmt.append(", '");
        insertStmt.append(student.getCity());
        insertStmt.append("', '");
        insertStmt.append(student.getCountry());
        insertStmt.append("')");
        
        System.out.println(insertStmt.toString());

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
    public void updateStudent(String oldEmail, Student student, DatabaseConnection databaseConnection)
            throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Student SET ");
        updateStmt.append("Email = '" + student.getEmail() + "', ");
        updateStmt.append("Name = '" + student.getName() + "', ");
        updateStmt.append("PostalCode = '" + student.getPostalCode() + "', ");
        updateStmt.append("HouseNumber = " + student.getHouseNumber() + ", ");
        updateStmt.append("City = '" + student.getCity() + "', ");
        updateStmt.append("Country = '" + student.getCountry() + "' ");
        updateStmt.append("WHERE Email = '" + oldEmail + "'");

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

    
}
