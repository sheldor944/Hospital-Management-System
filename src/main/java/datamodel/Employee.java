package datamodel;

import javafx.scene.control.DatePicker;

import java.util.Date;

public class Employee extends Person {
    private Date joiningDate;
    private String post;

    public Employee(String id, String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String mobile, Date joiningDate, String post) {
        super(id, firstName, lastName, dateOfBirth, age, gender, mobile);
        this.joiningDate = joiningDate;
        this.post = post;
    }
    public Employee( String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String mobile, Date joiningDate, String post) {
        super( firstName, lastName, dateOfBirth, age, gender, mobile);
        this.joiningDate = joiningDate;
        this.post = post;
    }
    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
