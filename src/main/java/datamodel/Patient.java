package datamodel;

import javafx.scene.control.DatePicker;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Patient extends Person {
    private String symptoms;
    private Doctor assignedDoctor;
    private int assignedDoctorID;

    public Patient(String id, String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String mobile, String symptoms) {
        super(id, firstName, lastName, dateOfBirth, age, gender, mobile);
        this.symptoms = symptoms;
        this.assignedDoctor = null ;
    }

    public Patient(String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String mobile, String symptoms) {
        super( firstName, lastName, dateOfBirth, age, gender, mobile);


        this.symptoms = symptoms;
        this.assignedDoctor = null;
    }
    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public int getAssignedDoctorID() {
        return assignedDoctorID;
    }

    public void setAssignedDoctorID(int assignedDoctorID) {
        this.assignedDoctorID = assignedDoctorID;
    }
}
