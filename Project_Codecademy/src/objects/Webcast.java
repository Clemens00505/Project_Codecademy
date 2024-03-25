package objects;

import java.sql.Date;
import java.sql.SQLException;

import database.DatabaseConnection;

public class Webcast {
    private int contentId;
    private String title;
    private String description;
    private String speakerName;
    private String speakerOrganisation;
    private Date publicationDate;
    private Status status;
    private String URL;
    private int timesViewed;
    
    //constructors
    public Webcast(String title, String description, String speakerName, String speakerOrganisation,
            Date publicationDate, Status status, String URL) {
        this.title = title;
        this.description = description;
        this.speakerName = speakerName;
        this.speakerOrganisation = speakerOrganisation;
        this.publicationDate = publicationDate;
        this.status = status;
        this.URL = URL;
        this.timesViewed = 0;
    }

    //constructor with contentId
    public Webcast(int contentId, String title, String description, String speakerName, String speakerOrganisation,
            Date publicationDate, Status status, String URL, int timesViewed) {
        this.contentId = contentId;
        this.title = title;
        this.description = description;
        this.speakerName = speakerName;
        this.speakerOrganisation = speakerOrganisation;
        this.publicationDate = publicationDate;
        this.status = status;
        this.URL = URL;
        this.timesViewed = timesViewed;
    }

    

    //default constructor
    public Webcast() {

    }

    //getters and setters
    public int getContentId() {
        return contentId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getSpeakerName() {
        return speakerName;
    }

    public String getSpeakerOrganisation() {
        return speakerOrganisation;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public Status getStatus() {
        return status;
    }

    public String getURL() {
        return URL;
    }

    public int getTimesViewed() {
        return timesViewed;
    }

    // method for adding webcast to database
    public void addWebcast(Webcast webcast, DatabaseConnection databaseConnection) throws SQLException {
        StringBuilder insertStmt = new StringBuilder();

        databaseConnection.openConnection();

        // stringbuilder used for adding a webcast
        insertStmt.append(
                "INSERT INTO Webcast (Title, Description, SpeakerName, SpeakerOrganisation, PublicationDate, Status, URL) ");
        insertStmt.append("VALUES ('");
        insertStmt.append(webcast.getTitle());
        insertStmt.append("', '");
        insertStmt.append(webcast.getDescription());
        insertStmt.append("', '");
        insertStmt.append(webcast.getSpeakerName());
        insertStmt.append("', '");
        insertStmt.append(webcast.getSpeakerOrganisation());
        insertStmt.append("', '");
        insertStmt.append(webcast.getPublicationDate().toString());
        insertStmt.append("', '");
        insertStmt.append(webcast.getStatus());
        insertStmt.append("', '");
        insertStmt.append(webcast.getURL());
        insertStmt.append("')");

        System.out.println(insertStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(insertStmt.toString());
        databaseConnection.closeConnection();
    }

    // method to update webcast information in database
    public void updateWebcast(int ContentId, Webcast webcast, DatabaseConnection databaseConnection)
            throws SQLException {
        databaseConnection.openConnection();

        StringBuilder updateStmt = new StringBuilder();
        updateStmt.append("UPDATE Webcast SET ");
        updateStmt.append("Title = '" + webcast.getTitle() + "', ");
        updateStmt.append("Description = '" + webcast.getDescription() + "', ");
        updateStmt.append("SpeakerName = '" + webcast.getSpeakerName() + "', ");
        updateStmt.append("SpeakerOrganisation = '" + webcast.getSpeakerOrganisation() + "', ");
        updateStmt.append("Status = '" + webcast.getStatus() + "', "); // Enclose Status in single quotes
        updateStmt.append("Url = '" + webcast.getURL() + "', ");
        updateStmt.append("TimesViewed = " + webcast.getTimesViewed() + " ");
        updateStmt.append("WHERE ContentId = " + contentId);

        System.out.println(updateStmt.toString());

        databaseConnection.executeSQLInsertUpdateDeleteStatement(updateStmt.toString());
        databaseConnection.closeConnection();
    }

    
}
