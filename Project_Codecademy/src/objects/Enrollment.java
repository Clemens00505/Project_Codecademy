package objects;

import java.sql.Date;

public class Enrollment {
    private String studentMail;
    private String courseName;
    private int percentage;
    private Date enrollmentDate;
    private int enrollmentId;

    public Enrollment(String studentMail, String courseName, int percentage, Date enrollmentDate, int enrollmentId) {
        this.studentMail = studentMail;
        this.courseName = courseName;
        this.percentage = percentage;
        this.enrollmentDate = enrollmentDate;
        this.enrollmentId = enrollmentId;
    }
}
