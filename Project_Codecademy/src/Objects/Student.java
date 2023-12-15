package Objects;

import java.sql.Date;

public class Student {
    private String email;
    private String name;
    private String gender;
    private Date dateOfBirth;

    public Student(String Email, String Name, String Gender, Date DateOfBirth) {
        this.email = Email;
        this.name = Name;
        this.gender = Gender;
        this.dateOfBirth = DateOfBirth;
    }


    //getters en setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

     public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        gender = gender;
    }
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        dateOfBirth = dateOfBirth;
    }
    
}
