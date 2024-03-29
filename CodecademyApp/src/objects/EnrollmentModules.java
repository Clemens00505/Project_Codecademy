package objects;

import database.DatabaseConnection;

public class EnrollmentModules {
    private int enrollmentId;
    private int courseId;
    private int contentId;
    private String title;
    private int version;
    private int progress;

    public EnrollmentModules() {

    }

    

    public EnrollmentModules(int enrollmentId, int courseId) {
        this.enrollmentId = enrollmentId;
        this.courseId = courseId;
    }

    



    public void insertIntoDatabase(EnrollmentModules enrollmentModules, DatabaseConnection databaseConnection) {
        databaseConnection.openConnection();

        StringBuilder insertStmt = new StringBuilder();
        insertStmt.append("INSERT INTO EnrollmentModules (EnrollmentId, CourseId, ContentId, Title, Version) ");
        insertStmt.append("SELECT Enrollment.EnrollmentId, Enrollment.CourseId, Module.ContentId, Module.Title, Module.Version ");
        insertStmt.append("FROM Enrollment, Module ");
        insertStmt.append("WHERE Enrollment.EnrollmentId = " + enrollmentModules.getEnrollmentId() + " AND Module.CourseId = " + enrollmentModules.getCourseId());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }



    public int getEnrollmentId() {
        return enrollmentId;
    }



    public int getCourseId() {
        return courseId;
    }



    public int getContentId() {
        return contentId;
    }



    public String getTitle() {
        return title;
    }



    public int getVersion() {
        return version;
    }



    public int getProgress() {
        return progress;
    }



    public void updateProgress(EnrollmentModules enrollmentModules, DatabaseConnection databaseConnection) {
        databaseConnection.openConnection();

        String query = "UPDATE EnrollmentModules SET Progress = " + enrollmentModules.getProgress() + " WHERE EnrollmentId = " + enrollmentModules.getEnrollmentId() + " AND ContentId = " + enrollmentModules.getContentId();

        databaseConnection.executeSQLInsertUpdateDeleteStatement(query);
        databaseConnection.closeConnection();
    }



    public void setEnrollmentId(int enrollmentId) {
        this.enrollmentId = enrollmentId;
    }



    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }



    public void setContentId(int contentId) {
        this.contentId = contentId;
    }



    public void setTitle(String title) {
        this.title = title;
    }



    public void setVersion(int version) {
        this.version = version;
    }



    public void setProgress(int progress) {
        this.progress = progress;
    }

    
}
