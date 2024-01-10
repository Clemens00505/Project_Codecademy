package objects;

import java.time.LocalDate;

public class Content {
    
    protected String title;
    protected String description;
    protected LocalDate publicationDate;
    protected Status status;


    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getPublicationDate() {
        return publicationDate;
    }
    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public void addContent(Content content, String title, String description, LocalDate publicationDate, Status status) {
        
    }
    
}
