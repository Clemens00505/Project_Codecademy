package objects;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Enrollment {
    private String studentMail;
    private String courseName;
    private int percentage;
    private Date enrollmentDate;
    private int enrollmentId;
    private int courseId;
    private int hasCertificate;

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

    public int addEnrollment(Enrollment enrollment, DatabaseConnection databaseConnection) throws SQLException {
        int enrollmentId = -1; // Default value indicating failure
        PreparedStatement preparedStatement = null;
        ResultSet generatedKeys = null;

        try {
            databaseConnection.openConnection();

            String query = "INSERT INTO Enrollment (StudentMail, CourseId, EnrollmentDate) VALUES (?, ?, ?)";
            preparedStatement = databaseConnection.getPreparedStatement(query, true); // true to retrieve generated keys
            preparedStatement.setString(1, enrollment.getStudentMail());
            preparedStatement.setInt(2, enrollment.getCourseId());
            preparedStatement.setDate(3, enrollment.getEnrollmentDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    enrollmentId = generatedKeys.getInt(1); // Get the first generated key
                }
            }
        } finally {
            if (generatedKeys != null) {
                generatedKeys.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            databaseConnection.closeConnection();
        }

        return enrollmentId;
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

    public int getHasCertificate() {
        return hasCertificate;
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

    public void setCertificateCreated(Enrollment enrollment, DatabaseConnection databaseConnection) {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Enrollment SET ");
        updateStmt.append("HasCertificate = 1 ");
        updateStmt.append("WHERE EnrollmentId = " + enrollment.getEnrollmentId());

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

}
