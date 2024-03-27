package objects;

import java.sql.Date;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Enrollment {
    private String studentMail;
    private String courseName;
    private int percentage;
    private Date enrollmentDate;
    private int enrollmentId;
    private int courseId;

    public Enrollment() {

    }

    public Enrollment(int enrollmentId, int percentage) {

    }

    public Enrollment(String studentMail, String courseName, int percentage, Date enrollmentDate, int enrollmentId,
            int courseId) {
        this.studentMail = studentMail;
        this.courseName = courseName;
        this.percentage = percentage;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
    }



    public Enrollment(String studentMail, String courseName, int percentage, Date enrollmentDate, int enrollmentId) {
        this.studentMail = studentMail;
        this.courseName = courseName;
        this.percentage = percentage;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentId = enrollmentId;
    }

    public Enrollment(String studentMail, String courseName, Date enrollmentDate) {
        this.studentMail = studentMail;
        this.courseName = courseName;
        this.enrollmentDate = enrollmentDate;
    }

    

    public void addEnrollment(Enrollment enrollment, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();
    
        // Open the database connection
        databaseConnection.openConnection();
        if (databaseConnection.connectionIsOpen()) {
            System.out.println("open");
        } else {
            System.out.println("closed");
        }
    
        // StringBuilder used for constructing the SQL INSERT statement
        insertStmt.append("INSERT INTO Enrollment (StudentMail, CourseId, EnrollmentDate) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(enrollment.getStudentMail());
        insertStmt.append("', ");
        insertStmt.append(enrollment.getCourseId());
        insertStmt.append(", '");
        insertStmt.append(enrollment.getEnrollmentDate().toString());
        insertStmt.append("')");
    
        System.out.println(insertStmt.toString());
    
        databaseConnection.executeSQLStatement(insertStmt.toString());
    
        // Close the database connection
        databaseConnection.closeConnection();
    }

    // method for deleting enrollment from database
    public void deleteEnrollment(Enrollment enrollment, DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();

        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM Enrollment WHERE EnrollmentId = ");
        deleteStmt.append(enrollment.getEnrollmentId());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(deleteStmt.toString());
        databaseConnection.closeConnection();
    }

    // method for updating enrollment information in database
    public void updateEnrollment(Enrollment enrollment, DatabaseConnection databaseConnection)
            throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Enrollment SET ");
        updateStmt.append("Percentage = " + enrollment.getPercentage() + " ");
        updateStmt.append("WHERE EnrollmentId = " + enrollment.getEnrollmentId());

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

    public String getStudentMail() {
        return studentMail;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getPercentage() {
        return percentage;
    }

    public Date getEnrollmentDate() {
        return enrollmentDate;
    }

    public int getEnrollmentId() {
        return enrollmentId;
    }

    

    

    public int getCourseIdByName(String courseName, DatabaseConnection databaseConnection) throws SQLException {
        // Query the database to get the course ID
        return databaseConnection.getCourseIdByName(courseName);
    }

    public void setStudentMail(String studentMail) {
        this.studentMail = studentMail;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public void setEnrollmentDate(Date enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }



    public int getCourseId() {
        return courseId;
    }
    
}
