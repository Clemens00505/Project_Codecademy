package objects;

import java.sql.Date;

public class Webcast {
    private int courseId;
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
            Date publicationDate, Status status, String URL, int timesViewed) {
        this.title = title;
        this.description = description;
        this.speakerName = speakerName;
        this.speakerOrganisation = speakerOrganisation;
        this.publicationDate = publicationDate;
        this.status = status;
        this.URL = URL;
        this.timesViewed = timesViewed;
    }

    //constructor with courseId
    public Webcast(int courseId, String title, String description, String speakerName, String speakerOrganisation,
            Date publicationDate, Status status, String URL, int timesViewed) {
        this.courseId = courseId;
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
}
