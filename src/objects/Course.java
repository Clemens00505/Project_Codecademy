package objects;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Course {
    private int courseId;
    private String courseName;
    private String subject;
    private String introText;
    private Difficulty difficulty;

    public Course() {
        // default constructor
    }

    // constructor with data
    public Course(String courseName, String subject, String introText, Difficulty difficulty) {
        this.courseName = courseName;
        this.subject = subject;
        this.introText = introText;
        this.difficulty = difficulty;
    }

    // constructor with courseId
    public Course(int courseId, String courseName, String subject, String introText, Difficulty difficulty) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.subject = subject;
        this.introText = introText;
        this.difficulty = difficulty;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getSubject() {
        return subject;
    }

    public String getIntroText() {
        return introText;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    // method for adding course to database
    public void addCourse(Course course, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        databaseConnection.openConnection();

        // stringbuilder used for adding a course
        insertStmt.append(
                "INSERT INTO Course (CourseName, Subject, IntroText, Difficulty) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(course.getCourseName());
        insertStmt.append("', '");
        insertStmt.append(course.getSubject());
        insertStmt.append("', '");
        insertStmt.append(course.getIntroText());
        insertStmt.append("', '");
        insertStmt.append(course.getDifficulty());
        insertStmt.append("')");

        System.out.println(insertStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    // method to update course information in database
    public void updateCourse(int courseId, Course course, DatabaseConnection databaseConnection)
            throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Course SET ");
        updateStmt.append("CourseName = '" + course.getCourseName() + "', ");
        updateStmt.append("Subject = '" + course.getSubject() + "', ");
        updateStmt.append("IntroText = '" + course.getIntroText() + "', ");
        updateStmt.append("Difficulty = '" + course.getDifficulty() + "' ");
        updateStmt.append("WHERE CourseId = '" + courseId + "'");

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

    // method for deleting course from database
    public void deleteCourse(Course course, DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();

        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM Course ");
        deleteStmt.append("WHERE CourseId = '" + course.getCourseId() + "'");

        databaseConnection.executeSQLInsertUpdateDeleteStatement(deleteStmt.toString());
        databaseConnection.closeConnection();
    }

    public int getAmountCompleted(Course course, DatabaseConnection databaseConnection) {
        databaseConnection.openConnection();
        int amountCompleted = 0;
        String selectStmt = "SELECT COUNT(*) AS amountCompleted FROM Certificate WHERE EnrollmentId in (SELECT EnrollmentId FROM Enrollment WHERE CourseId = " + course.getCourseId() + ")";

        try (ResultSet resultSet = databaseConnection.executeSQLSelectStatement(selectStmt)) {
            if (resultSet.next()) {
                amountCompleted = resultSet.getInt("amountCompleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            databaseConnection.closeConnection();
        }

        return amountCompleted;
    }

}
