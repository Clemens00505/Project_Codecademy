package Objects;

import java.sql.Date;

public class Student {
    private String Email;
    private String Name;
    private String Gender;
    private Date DateOfBirth;

    public Student(String Email, String Name, String Gender, Date DateOfBirth) {
        this.Email = Email;
        this.Name = Name;
        this.Gender = Gender;
        this.DateOfBirth = DateOfBirth;
    }


    //getters en setters
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

     public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
    
    public Date getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }
}
