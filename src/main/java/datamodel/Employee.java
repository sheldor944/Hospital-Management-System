package datamodel;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.Date;

public class Employee extends Person {
    private LocalDate joiningDate;
    private String post;

    public Employee(int id, String firstName, String lastName, LocalDate dateOfBirth, Integer age, String gender, String mobile, LocalDate joiningDate, String post) {
        super(id, firstName, lastName, dateOfBirth, age, gender, mobile);
        this.joiningDate = joiningDate;
        this.post = post;
    }
    public Employee( String firstName, String lastName, LocalDate dateOfBirth, Integer age, String gender, String mobile, LocalDate joiningDate, String post) {
        super( firstName, lastName, dateOfBirth, age, gender, mobile);
        this.joiningDate = joiningDate;
        this.post = post;
    }
    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
