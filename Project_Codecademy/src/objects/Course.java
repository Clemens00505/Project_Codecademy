package objects;

public class Course {
    private String courseName;
    private String subject;
    private String introText;
    private Difficulty difficulty;

    public Course() {
        //default constructor
    }

    //constructor with data
    public Course(String courseName, String subject, String introText, Difficulty difficulty) {
        this.courseName = courseName;
        this.subject = subject;
        this.introText = introText;
        this.difficulty = difficulty;
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
}
