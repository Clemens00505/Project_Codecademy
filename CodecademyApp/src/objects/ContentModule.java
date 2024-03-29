package objects;

import java.sql.Date;
import java.sql.SQLException;

import database.DatabaseConnection;

public class ContentModule {
    private String title;
    private int version;
    private String description;
    private String contactPersonName;
    private String contactPersonMail;
    private Date publicationDate;
    private Status status;
    private int indexNumber;
    private Integer courseId;

    // constructor for a ContentModule
    public ContentModule(String title, int version, String description, String contactPersonName,
            String contactPersonMail,
            Date publicationDate, Status status, int indexNumber) {
        this.title = title;
        this.version = version;
        this.description = description;
        this.contactPersonName = contactPersonName;
        this.contactPersonMail = contactPersonMail;
        this.publicationDate = publicationDate;
        this.status = status;
        this.indexNumber = indexNumber;
        this.courseId = -1;
    }

    public ContentModule() {

    }

    // Getters
    public String getTitle() {
        return title;
    }

    public int getVersion() {
        return version;
    }

    public String getDescription() {
        return description;
    }

    public String getContactPersonName() {
        return contactPersonName;
    }

    public String getContactPersonMail() {
        return contactPersonMail;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public int getIndexNumber() {
        return indexNumber;
    }

    public int getCourseId() {
        return courseId;
    }

    // method for adding module to database
    public void addModule(ContentModule contentModule, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        databaseConnection.openConnection();

        // stringbuilder used for adding a module
        insertStmt.append(
                "INSERT INTO Module (Title, Version, Description, ContactPersonName, ContactPersonMail, PublicationDate, Status, IndexNumber) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(contentModule.getTitle());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getVersion());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getDescription());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getContactPersonName());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getContactPersonMail());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getPublicationDate().toString());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getStatus());
        insertStmt.append("', '");
        insertStmt.append(contentModule.getIndexNumber());
        insertStmt.append("')");

        System.out.println(insertStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    // method to update module information in database
    public void updateModule(String oldTitle, ContentModule contentModule, DatabaseConnection databaseConnection)
            throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Module SET ");
        updateStmt.append("Title = '" + contentModule.getTitle() + "', ");
        updateStmt.append("Description = '" + contentModule.getDescription() + "', ");
        updateStmt.append("ContactPersonName = '" + contentModule.getContactPersonName() + "', ");
        updateStmt.append("ContactPersonMail = '" + contentModule.getContactPersonMail() + "', ");
        updateStmt.append("Status = '" + contentModule.getStatus() + "', ");
        updateStmt.append("IndexNumber = '" + contentModule.getIndexNumber() + "' ");
        updateStmt.append("WHERE Title = '" + oldTitle + "' ");
        updateStmt.append("AND Version = " + contentModule.getVersion());

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

    // method for deleting module from database
    public void deleteModule(ContentModule contentModule, DatabaseConnection databaseConnection) throws SQLException {
        databaseConnection.openConnection();

        StringBuilder deleteStmt = new StringBuilder();
        deleteStmt.append("DELETE FROM Module ");
        deleteStmt.append("WHERE Title = '" + contentModule.getTitle() + "' ");
        deleteStmt.append("AND Version = " + contentModule.getVersion());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(deleteStmt.toString());
        databaseConnection.closeConnection();
    }
}
