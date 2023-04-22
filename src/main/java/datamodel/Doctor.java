package datamodel;

import javafx.scene.control.DatePicker;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class Doctor extends Employee{
    private String department;
    private String description ;
    private ArrayList <Patient> patients;

    public Doctor(int id , String firstName, String lastName, LocalDate dateOfBirth, Integer age, String gender, String mobile, LocalDate joiningDate, String post, String department , String description) {
        super(id ,  firstName, lastName, dateOfBirth, age, gender, mobile, joiningDate, post);
        this.department = department;
        this.description = description ;
        this.patients = new ArrayList<>();
    }
//    public Doctor(String firstName, String lastName, Date dateOfBirth, Integer age, String gender, String mobile, Date joiningDate, String post, String department ,String description) {
//        super( firstName, lastName, dateOfBirth, age, gender, mobile, joiningDate, post);
//        this.department = department;
//        this.description = description ;
//        this.patients = new ArrayList<>();
//    }

    public String getDescription(){ return  description ; }
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void setPatients(ArrayList<Patient> patients) {
        this.patients = patients;
    }
}
