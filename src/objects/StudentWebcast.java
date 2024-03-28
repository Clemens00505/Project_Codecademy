package objects;

import database.DatabaseConnection;

public class StudentWebcast {
    private String email;
    private String title;
    private String description;
    private int progressPercentage;
    private int studentWebcastProgressId;
    private int contentId;

    public StudentWebcast() {
        
    }
    
    public StudentWebcast(String email, String title, String description, int progressPercentage,
            int studentWebcastProgressId, int contentId) {
        this.email = email;
        this.title = title;
        this.description = description;
        this.progressPercentage = progressPercentage;
        this.studentWebcastProgressId = studentWebcastProgressId;
        this.contentId = contentId;
    }

    public String getEmail() {
        return email;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getProgressPercentage() {
        return progressPercentage;
    }

    public int getStudentWebcastProgressid() {
        return studentWebcastProgressId;
    }

    public int getContentId() {
        return contentId;
    }

    public void updateProgress(int studentWebcastProgressId, int progress, DatabaseConnection databaseConnection) {

        databaseConnection.openConnection();

        String updateStmt = "UPDATE StudentWebcastProgress SET ProgressPercentage = " + progress + "WHERE StudentWebcastProgressId = " + studentWebcastProgressId;

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt);
        databaseConnection.closeConnection();


    }

    

    
}
