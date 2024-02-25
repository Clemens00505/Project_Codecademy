package objects;

import java.sql.Date;

public class Module {
    private String title;
    private int version;
    private String description;
    private String contactPersonName;
    private String contactPersonMail;
    private Date publicationDate;
    private Status status;

    //TODO: Constructors maken

    //empty constructor
    public Module() {

    }


    //Constructor for module
    public Module(String title, int version, String description, String contactPersonName, String contactPersonMail, Date publicationDate, Status status) {
        this.title = title;
        this.version = version;
        this.description = description;
        this.contactPersonName = contactPersonName;
        this.contactPersonMail = contactPersonMail;
        this.publicationDate = publicationDate;
        this.status = status;
    }

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
}
